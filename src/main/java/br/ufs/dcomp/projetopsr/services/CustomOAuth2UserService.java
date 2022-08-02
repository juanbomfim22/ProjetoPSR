package br.ufs.dcomp.projetopsr.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.domain.enums.AuthProvider;
import br.ufs.dcomp.projetopsr.repositories.UsuarioRepository;
import br.ufs.dcomp.projetopsr.security.UserPrincipal;
import br.ufs.dcomp.projetopsr.security.oauth2.OAuth2UserInfo;
import br.ufs.dcomp.projetopsr.security.oauth2.OAuth2UserInfoFactory;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private AuthProvider getAuthProviderFrom(OAuth2UserRequest oAuth2UserRequest) {
    	return AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
    }
    
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws Exception {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        // If email is null.....
//        if(oAuth2UserInfo.getEmail() == null || oAuth2UserInfo.getEmail().isEmpty()) {
//            throw new Exception("Email not found from OAuth2 provider");
//        }

        Usuario userOptional = repo.findByEmail(oAuth2UserInfo.getEmail());
        Usuario user;
        if(userOptional != null) {
            user = userOptional;
            if(!user.getProvider().equals(getAuthProviderFrom(oAuth2UserRequest))) {
                
            	
            	throw new Exception("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        UserPrincipal prin = UserPrincipal.create(user);
        prin.setAttributes(oAuth2User.getAttributes());
       
        return prin;
    }

    private Usuario registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo info) {
    	String email = info.getEmail();
    	String nome = info.getNome();
    	
    	if(info.getEmail() == null || info.getEmail().isEmpty()) {
    		String login = (String) info.getAttributes().get("login");
    		if(login == null) login = UUID.randomUUID().toString().replaceAll("-","");
    		email = login + "_auto_generated@example.com";
    	}
    	
    	Usuario user = new Usuario(
    			null, nome, email, info.getImagemPerfil(), info.getId(), null, null
    	);    
    	user.setProvider(getAuthProviderFrom(oAuth2UserRequest));
        return repo.save(user);
    }

    private Usuario updateExistingUser(Usuario existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setNome(oAuth2UserInfo.getNome());
        existingUser.setImagemPerfil(oAuth2UserInfo.getImagemPerfil());
        return repo.save(existingUser);
    }

}