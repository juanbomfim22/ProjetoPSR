package br.ufs.dcomp.projetopsr.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.domain.enums.Perfil;
import lombok.ToString;
  
@ToString
public class UserPrincipal implements OAuth2User, UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;
	
	public Integer getId() {
		return id;
	}
	
	public UserPrincipal() {
		
	}
	
	public UserPrincipal(Integer id, String email, String senha,  Collection<? extends GrantedAuthority> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis;
	}
	
	public static List<GrantedAuthority> toList(Set<Perfil> perfis){
		return perfis.stream().map(x -> new SimpleGrantedAuthority(x.getValor())).collect(Collectors.toList());		
	}
	
	public static UserPrincipal create(Usuario user) {
	        List<GrantedAuthority> authorities = Collections.
	                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

	        return new UserPrincipal(
	                user.getId(),
	                user.getEmail(),
	                user.getSenha(),
	                authorities
	        );
	    }
	
	// Implementado pelo proprio programador
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getValor()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}
	
	public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}
}
