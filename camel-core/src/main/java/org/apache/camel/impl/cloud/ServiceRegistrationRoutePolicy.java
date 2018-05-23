/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.impl.cloud;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Endpoint;
import org.apache.camel.Route;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.cloud.DiscoverableService;
import org.apache.camel.cloud.ServiceDefinition;
import org.apache.camel.cloud.ServiceRegistry;
import org.apache.camel.support.RoutePolicySupport;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedResource(description = "Clustered Route policy using")
public class ServiceRegistrationRoutePolicy extends RoutePolicySupport implements CamelContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistrationRoutePolicy.class);

    private final ServiceRegistry.Selector serviceRegistrySelector;

    private ServiceRegistry serviceRegistry;
    private CamelContext camelContext;

    public ServiceRegistrationRoutePolicy() {
        this(null, ServiceRegistrySelectors.DEFAULT_SELECTOR);
    }

    public ServiceRegistrationRoutePolicy(ServiceRegistry.Selector serviceRegistrySelector) {
        this(null, serviceRegistrySelector);
    }

    public ServiceRegistrationRoutePolicy(ServiceRegistry serviceRegistry, ServiceRegistry.Selector serviceRegistrySelector) {
        this.serviceRegistry = serviceRegistry;
        this.serviceRegistrySelector = serviceRegistrySelector;
    }

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    // ***********************
    // policy life-cycle
    // ***********************

    @Override
    public void doStart() throws Exception {
        if (serviceRegistry == null) {
            serviceRegistry = ServiceRegistryHelper.lookupService(camelContext, serviceRegistrySelector).orElseThrow(
                () -> new IllegalStateException("ServiceRegistry service not found")
            );
        }

        LOGGER.debug("ServiceRegistrationRoutePolicy {} is using ServiceRegistry instance {} (id={}, type={})",
            this,
            serviceRegistry,
            serviceRegistry.getId(),
            serviceRegistry.getClass().getName()
        );
    }

    // ***********************
    // route life-cycle
    // ***********************

    @Override
    public void onStart(Route route) {
        register(route);
    }

    @Override
    public void onStop(Route route) {
        deregister(route);
    }

    @Override
    public void onSuspend(Route route) {
        deregister(route);
    }

    @Override
    public void onResume(Route route) {
        register(route);
    }

    // ***********************
    // registration helpers
    // ***********************

    private void register(Route route) {
        computeServiceDefinition(route).ifPresent(serviceRegistry::register);
    }

    private void deregister(Route route) {
        computeServiceDefinition(route).ifPresent(serviceRegistry::deregister);
    }

    private Optional<ServiceDefinition> computeServiceDefinition(Route route) {
        final Endpoint endpoint = route.getConsumer().getEndpoint();
        final Map<String, Object> properties = new HashMap<>();

        if (endpoint instanceof DiscoverableService) {
            final DiscoverableService service = (DiscoverableService) endpoint;

            // first load all the properties from the endpoint
            properties.putAll(service.getServiceProperties());
        }

        // try to get the service id from route properties
        String serviceId = (String)route.getProperties().get(ServiceDefinition.SERVICE_META_ID);
        if (serviceId == null) {
            // if not check if the route id is custom and use it
            if (route.getRouteContext().getRoute().hasCustomIdAssigned()) {
                serviceId = route.getId();
            }
        }
        if (serviceId == null) {
            // finally get the id from the DiscoverableService
            serviceId = (String)properties.get(ServiceDefinition.SERVICE_META_ID);
        }

        // try to get the service name from route properties
        String serviceName = (String)route.getProperties().get(ServiceDefinition.SERVICE_META_NAME);
        if (serviceName == null) {
            // if not check if the route group is defined use the route group
            serviceName = route.getGroup();
        }
        if (serviceName == null) {
            // finally get the name from the DiscoverableService
            serviceName = (String)properties.get(ServiceDefinition.SERVICE_META_NAME);
        }

        if (ObjectHelper.isEmpty(serviceId) || ObjectHelper.isEmpty(serviceName)) {
            LOGGER.debug("Route {} has not enough information for service registration");
            return Optional.empty();
        }

        // Build the final resource definition from bits collected from the
        // endpoint and the route.
        DefaultServiceDefinition.Builder builder = DefaultServiceDefinition.builder()
            .from(properties)
            .withId(serviceId)
            .withName(serviceName)
            .addMeta(ServiceDefinition.SERVICE_META_NAME, serviceName)
            .addMeta(ServiceDefinition.SERVICE_META_ID, serviceId);

        // Add additional metadata from route properties whose name starts
        // with ServiceDefinition.SERVICE_META_PREFIX.
        //
        // NOTE: At the moment it is not possible to add properties to a route
        // with fluent DSL
        for (Map.Entry<String, Object> entry: route.getProperties().entrySet()) {
            if (!entry.getKey().startsWith(ServiceDefinition.SERVICE_META_PREFIX)) {
                continue;
            }

            final String key = entry.getKey().substring(ServiceDefinition.SERVICE_META_PREFIX.length());
            final String val = camelContext.getTypeConverter().convertTo(String.class, entry.getValue());

            builder.addMeta(key, val);
        }

        return Optional.of(builder.build());
    }
}
