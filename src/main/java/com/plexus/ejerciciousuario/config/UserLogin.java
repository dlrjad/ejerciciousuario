package com.plexus.ejerciciousuario.config;

import java.util.Collection;
import java.util.stream.Collectors;

import com.plexus.ejerciciousuario.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserLogin extends User implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return super.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toList());
  }

  public UserLogin(User user) {
    super.setName(user.getName());
    super.setMail(user.getMail());
    super.setPassword(user.getPassword());
    super.setRoles(user.getRoles());
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