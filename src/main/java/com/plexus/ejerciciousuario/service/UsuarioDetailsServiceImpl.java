package com.plexus.ejerciciousuario.service;

import static java.util.Collections.emptyList;

import com.plexus.ejerciciousuario.repository.UserRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	public UsuarioDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		com.plexus.ejerciciousuario.model.User user = userRepository.findByName(name);
		if (user == null) {
			throw new UsernameNotFoundException(name);
    }
		return new User(user.getName(), user.getPassword(), emptyList());
	}
}