package com.plexus.ejerciciousuario.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Las peticiones que no sean /login pasarán por este filtro y se encarga
 * de validar el token a traves de la request
 */
public class JwtFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request,
    ServletResponse response,
    FilterChain filterChain)
    throws IOException, ServletException {

      Authentication authentication = JwtUtil.getAuthentication((HttpServletRequest)request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);

  }
}
