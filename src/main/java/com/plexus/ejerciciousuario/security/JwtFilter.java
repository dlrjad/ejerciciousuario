package com.plexus.ejerciciousuario.security;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

      HttpServletRequest req = (HttpServletRequest) request;

      HttpServletResponse res = (HttpServletResponse) response;

      String user = JwtUtil.getUser((HttpServletRequest) request);
      User aux = userRepository.findByName(user);
      String[] roles = aux.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())
      .toArray(new String[aux.getRoles().size()]);

      Authentication authentication = JwtUtil.getAuthentication((HttpServletRequest)request, roles);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);

      /*if (allowRequestWithoutToken(req)) {

        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);

        filterChain.doFilter(request, response);

      }*/

  }

  public boolean allowRequestWithoutToken(HttpServletRequest request)  {
    if (request.getRequestURI().contains("/login")
        || request.getRequestURI().contains("/users")) {
        return true;
    }
    return false;
  }

}
