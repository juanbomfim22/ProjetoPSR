package br.ufs.dcomp.projetopsr.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.ufs.dcomp.projetopsr.security.UserPrincipal;

@Service
public class UserService {
	public static UserPrincipal authenticated() {
		try {
			return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	} 
}
