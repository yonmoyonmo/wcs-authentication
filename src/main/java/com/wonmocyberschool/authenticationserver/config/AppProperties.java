package com.wonmocyberschool.authenticationserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

//JWT, token redirect properties
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    public static class Auth {
        private String tokenSecret;
        private Long tokenExpirationMsec;

        public String getTokenSecret(){return tokenSecret;}
        public Long getTokenExpirationMsec(){return tokenExpirationMsec;}

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public void setTokenExpirationMsec(Long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }
    public static class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();
        public List<String> getAuthorizedRedirectUris(){
            return authorizedRedirectUris;
        }

        public void setAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
        }
    }
    public Auth getAuth(){
        return auth;
    }
    public OAuth2 getOauth2(){
        return oauth2;
    }
}
