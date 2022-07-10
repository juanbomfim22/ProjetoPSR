package br.ufs.projetopsr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.ufs.projetopsr.domain.Usuario;
import br.ufs.projetopsr.domain.enums.AuthProvider;
import br.ufs.projetopsr.repositories.UsuarioRepository;
import br.ufs.projetopsr.security.UserPrincipal;
import br.ufs.projetopsr.security.oauth2.OAuth2UserInfo;
import br.ufs.projetopsr.security.oauth2.OAuth2UserInfoFactory;

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

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws Exception {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.hasLength(oAuth2UserInfo.getEmail())) {
            throw new Exception("Email not found from OAuth2 provider");
        }

        Usuario userOptional = repo.findByEmail(oAuth2UserInfo.getEmail());
        Usuario user;
        if(userOptional != null) {
            user = userOptional;
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
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

    private Usuario registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    	Usuario user = new Usuario();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setNome(oAuth2UserInfo.getNome());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImagemPerfil(oAuth2UserInfo.getImagemPerfil());
        
        return repo.save(user);
    }

    private Usuario updateExistingUser(Usuario existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setNome(oAuth2UserInfo.getNome());
        existingUser.setImagemPerfil(oAuth2UserInfo.getImagemPerfil());
        return repo.save(existingUser);
    }

}