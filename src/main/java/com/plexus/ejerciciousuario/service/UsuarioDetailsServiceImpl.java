package com.plexus.ejerciciousuario.service;

import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.plexus.ejerciciousuario.model.Role;
import com.plexus.ejerciciousuario.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.plexus.ejerciciousuario.config.UserLogin;

@Service("userService")
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	public UsuarioDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		/*com.plexus.ejerciciousuario.model.User user = userRepository.findByName(name);
		if (user == null) {
			throw new UsernameNotFoundException(name);
    }
		return new User(user.getName(), user.getPassword(), emptyList());*/

		com.plexus.ejerciciousuario.model.User user = userRepository.findByMail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		return new User(user.getMail(), user.getPassword(), emptyList());
		//return new UserLogin(user);
		//return new User(user.getMail(), user.getPassword(), (Collection<? extends GrantedAuthority>) user.getRoles());
	}

}