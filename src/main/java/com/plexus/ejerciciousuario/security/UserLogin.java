package com.plexus.ejerciciousuario.security;

import java.util.Collection;
import java.util.stream.Collectors;

import com.plexus.ejerciciousuario.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase UserLogin que extiende de User e implementa UserDetails
 * 
 * @author dlrjad
 */

public class UserLogin extends User implements UserDetails {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor para inicializar los valores
   * @param user
   */
  public UserLogin(User user) {
    super();
    super.setUser_id(user.getUser_id());
    super.setName(user.getName());
    super.setMail(user.getMail());
    super.setPassword(user.getPassword());
    super.setRoles(user.getRoles());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return super.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return super.getName();
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

}