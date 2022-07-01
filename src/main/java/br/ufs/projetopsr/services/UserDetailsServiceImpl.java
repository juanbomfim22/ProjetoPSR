package br.ufs.projetopsr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufs.projetopsr.domain.Usuario;
import br.ufs.projetopsr.repositories.UsuarioRepository;
import br.ufs.projetopsr.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usr = repo.findByEmail(email);
		if(usr == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSpringSecurity(usr.getId(), usr.getEmail(), usr.getSenha(), usr.getPerfis());
	}
}
