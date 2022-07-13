package br.ufs.dcomp.projetopsr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.domain.Usuario;
import br.ufs.dcomp.projetopsr.repositories.UsuarioRepository;
import br.ufs.dcomp.projetopsr.security.UserPrincipal;

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
		return new UserPrincipal(usr.getId(), usr.getEmail(), usr.getSenha(), UserPrincipal.toList(usr.getPerfis()));
	}
}
