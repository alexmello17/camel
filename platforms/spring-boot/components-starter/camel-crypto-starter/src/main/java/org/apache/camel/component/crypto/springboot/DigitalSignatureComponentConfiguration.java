/*
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
package org.apache.camel.component.crypto.springboot;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import javax.annotation.Generated;
import org.apache.camel.component.crypto.CryptoOperation;
import org.apache.camel.spring.boot.ComponentConfigurationPropertiesCommon;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The crypto component is used for signing and verifying exchanges using the
 * Signature Service of the Java Cryptographic Extension (JCE).
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.maven.packaging.SpringBootAutoConfigurationMojo")
@ConfigurationProperties(prefix = "camel.component.crypto")
public class DigitalSignatureComponentConfiguration
        extends
            ComponentConfigurationPropertiesCommon {

    /**
     * Whether to enable auto configuration of the crypto component. This is
     * enabled by default.
     */
    private Boolean enabled;
    /**
     * To use the shared DigitalSignatureConfiguration as configuration
     */
    private DigitalSignatureConfigurationNestedConfiguration configuration;
    /**
     * Whether the component should resolve property placeholders on itself when
     * starting. Only properties which are of String type can use property
     * placeholders.
     */
    private Boolean resolvePropertyPlaceholders = true;

    public DigitalSignatureConfigurationNestedConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(
            DigitalSignatureConfigurationNestedConfiguration configuration) {
        this.configuration = configuration;
    }

    public Boolean getResolvePropertyPlaceholders() {
        return resolvePropertyPlaceholders;
    }

    public void setResolvePropertyPlaceholders(
            Boolean resolvePropertyPlaceholders) {
        this.resolvePropertyPlaceholders = resolvePropertyPlaceholders;
    }

    public static class DigitalSignatureConfigurationNestedConfiguration {
        public static final Class CAMEL_NESTED_CLASS = org.apache.camel.component.crypto.DigitalSignatureConfiguration.class;
        /**
         * The logical name of this operation.
         */
        private String name;
        /**
         * Sets the JCE name of the Algorithm that should be used for the
         * signer.
         */
        private String algorithm = "SHA1WithDSA";
        /**
         * Sets the alias used to query the KeyStore for keys and {@link
         * java.security.cert.Certificate Certificates} to be used in signing
         * and verifying exchanges. This value can be provided at runtime via
         * the message header {@link
         * org.apache.camel.component.crypto.DigitalSignatureConstants#KEYSTORE_ALIAS}
         */
        private String alias;
        /**
         * Set the PrivateKey that should be used to sign the exchange
         */
        private PrivateKey privateKey;
        /**
         * Sets the reference name for a PrivateKey that can be found in the
         * registry.
         */
        private String privateKeyName;
        /**
         * Set the PublicKey that should be used to verify the signature in the
         * exchange.
         */
        private PublicKey publicKey;
        /**
         * Sets the reference name for a publicKey that can be found in the
         * registry.
         */
        private String publicKeyName;
        /**
         * Set the Certificate that should be used to verify the signature in
         * the exchange based on its payload.
         */
        private Certificate certificate;
        /**
         * Sets the reference name for a PrivateKey that can be found in the
         * registry.
         */
        private String certificateName;
        /**
         * Sets the KeyStore that can contain keys and Certficates for use in
         * signing and verifying exchanges. A {@link KeyStore} is typically used
         * with an alias, either one supplied in the Route definition or
         * dynamically via the message header "CamelSignatureKeyStoreAlias". If
         * no alias is supplied and there is only a single entry in the
         * Keystore, then this single entry will be used.
         */
        private KeyStore keystore;
        /**
         * Sets the reference name for a Keystore that can be found in the
         * registry.
         */
        private String keystoreName;
        /**
         * Sets the password used to access an aliased {@link PrivateKey} in the
         * KeyStore.
         */
        private char[] password;
        /**
         * Sets the KeyStore that can contain keys and Certficates for use in
         * signing and verifying exchanges based on the given
         * KeyStoreParameters. A {@link KeyStore} is typically used with an
         * alias, either one supplied in the Route definition or dynamically via
         * the message header "CamelSignatureKeyStoreAlias". If no alias is
         * supplied and there is only a single entry in the Keystore, then this
         * single entry will be used.
         */
        private KeyStoreParameters keyStoreParameters;
        /**
         * Sets the reference name for a SecureRandom that can be found in the
         * registry.
         */
        private String secureRandomName;
        /**
         * Set the SecureRandom used to initialize the Signature service
         */
        private SecureRandom secureRandom;
        /**
         * Set the size of the buffer used to read in the Exchange payload data.
         */
        private Integer bufferSize = 2048;
        /**
         * Set the id of the security provider that provides the configured
         * {@link Signature} algorithm.
         */
        private String provider;
        /**
         * Set the name of the message header that should be used to store the
         * base64 encoded signature. This defaults to 'CamelDigitalSignature'
         */
        private String signatureHeaderName;
        /**
         * Determines if the Signature specific headers be cleared after signing
         * and verification. Defaults to true, and should only be made otherwise
         * at your extreme peril as vital private information such as Keys and
         * passwords may escape if unset.
         */
        private Boolean clearHeaders = true;
        /**
         * Set the Crypto operation from that supplied after the crypto scheme
         * in the endpoint uri e.g. crypto:sign sets sign as the operation.
         */
        private CryptoOperation cryptoOperation;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public PrivateKey getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
        }

        public String getPrivateKeyName() {
            return privateKeyName;
        }

        public void setPrivateKeyName(String privateKeyName) {
            this.privateKeyName = privateKeyName;
        }

        public PublicKey getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
        }

        public String getPublicKeyName() {
            return publicKeyName;
        }

        public void setPublicKeyName(String publicKeyName) {
            this.publicKeyName = publicKeyName;
        }

        public Certificate getCertificate() {
            return certificate;
        }

        public void setCertificate(Certificate certificate) {
            this.certificate = certificate;
        }

        public String getCertificateName() {
            return certificateName;
        }

        public void setCertificateName(String certificateName) {
            this.certificateName = certificateName;
        }

        public KeyStore getKeystore() {
            return keystore;
        }

        public void setKeystore(KeyStore keystore) {
            this.keystore = keystore;
        }

        public String getKeystoreName() {
            return keystoreName;
        }

        public void setKeystoreName(String keystoreName) {
            this.keystoreName = keystoreName;
        }

        public char[] getPassword() {
            return password;
        }

        public void setPassword(char[] password) {
            this.password = password;
        }

        public KeyStoreParameters getKeyStoreParameters() {
            return keyStoreParameters;
        }

        public void setKeyStoreParameters(KeyStoreParameters keyStoreParameters) {
            this.keyStoreParameters = keyStoreParameters;
        }

        public String getSecureRandomName() {
            return secureRandomName;
        }

        public void setSecureRandomName(String secureRandomName) {
            this.secureRandomName = secureRandomName;
        }

        public SecureRandom getSecureRandom() {
            return secureRandom;
        }

        public void setSecureRandom(SecureRandom secureRandom) {
            this.secureRandom = secureRandom;
        }

        public Integer getBufferSize() {
            return bufferSize;
        }

        public void setBufferSize(Integer bufferSize) {
            this.bufferSize = bufferSize;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getSignatureHeaderName() {
            return signatureHeaderName;
        }

        public void setSignatureHeaderName(String signatureHeaderName) {
            this.signatureHeaderName = signatureHeaderName;
        }

        public Boolean getClearHeaders() {
            return clearHeaders;
        }

        public void setClearHeaders(Boolean clearHeaders) {
            this.clearHeaders = clearHeaders;
        }

        public CryptoOperation getCryptoOperation() {
            return cryptoOperation;
        }

        public void setCryptoOperation(CryptoOperation cryptoOperation) {
            this.cryptoOperation = cryptoOperation;
        }
    }
}