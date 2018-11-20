package com.plexus.ejerciciousuario.security;

import static com.plexus.ejerciciousuario.constant.Constants.REGISTER_URL;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.plexus.ejerciciousuario.model.User;
import com.plexus.ejerciciousuario.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtFilter extends GenericFilterBean {

  private UserRepository userRepository;

  public JwtFilter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void doFilter(ServletRequest request,
    ServletResponse response,
    FilterChain filterChain)
    throws IOException, ServletException {

      HttpServletRequest request_;
      request_ = (HttpServletRequest)request;

      String[] roles = null;

      if(!request_.getRequestURI().equals(REGISTER_URL)) {

      String user = JwtUtil.getUser((HttpServletRequest) request);
      User aux = userRepository.findByMail(user);
      roles = aux.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())
      .toArray(new String[aux.getRoles().size()]);
      }

      Authentication authentication = JwtUtil.getAuthentication((HttpServletRequest)request, roles);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);

  }

}
