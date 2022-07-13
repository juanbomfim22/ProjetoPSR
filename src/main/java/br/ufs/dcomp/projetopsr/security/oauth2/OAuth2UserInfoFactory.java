package br.ufs.dcomp.projetopsr.security.oauth2;

import java.util.Map;

import br.ufs.dcomp.projetopsr.domain.enums.AuthProvider;

public class OAuth2UserInfoFactory {

	public static class FacebookOAuth2UserInfo extends OAuth2UserInfo {
        public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
            super(attributes);
        }

        @Override
        public String getId() {
            return (String) attributes.get("id");
        }

        @Override
        public String getNome() {
            return (String) attributes.get("name");
        }

        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }

        @Override
        public String getImagemPerfil() {
            if(attributes.containsKey("picture")) {
                Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
                if(pictureObj.containsKey("data")) {
                    Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
                    if(dataObj.containsKey("url")) {
                        return (String) dataObj.get("url");
                    }
                }
            }
            return null;
        }
    }
    
    public static class GithubOAuth2UserInfo extends OAuth2UserInfo {

        public GithubOAuth2UserInfo(Map<String, Object> attributes) {
            super(attributes);
        }

        @Override
        public String getId() {
            return ((Integer) attributes.get("id")).toString();
        }

        @Override
        public String getNome() {
            return (String) attributes.get("name");
        }

        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }

        @Override
        public String getImagemPerfil() {
            return (String) attributes.get("avatar_url");
        }
    }
    
    public static class GoogleOAuth2UserInfo extends OAuth2UserInfo {

        public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
            super(attributes);
        }

        @Override
        public String getId() {
            return (String) attributes.get("sub");
        }

        @Override
        public String getNome() {
            return (String) attributes.get("name");
        }

        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }

        @Override
        public String getImagemPerfil() {
            return (String) attributes.get("picture");
        }
    }
    
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws Exception {
        if(registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new Exception("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}