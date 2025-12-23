package com.example.patientrecordsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
/**
 * Configuration properties for JWT handling.
 *
 * <p>Properties are bound from configuration using the prefix {@code jwt} and include
 * the secret key, the token issuer and access token lifetime in minutes.
 */
public class JwtProperties {

    private String secret;
    private String issuer;
    private long accessTokenMinutes;

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return issuer;
    }
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public long getAccessTokenMinutes() {
        return accessTokenMinutes;
    }
    public void setAccessTokenMinutes(long accessTokenMinutes) {
        this.accessTokenMinutes = accessTokenMinutes;
    }
}
