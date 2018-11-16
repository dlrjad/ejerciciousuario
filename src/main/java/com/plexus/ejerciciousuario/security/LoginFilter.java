package com.plexus.ejerciciousuario.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plexus.ejerciciousuario.model.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  public LoginFilter(String url, AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(url));
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
  throws AuthenticationException, IOException, ServletException {

    InputStream body = req.getInputStream();
    User user = new ObjectMapper().readValue(body, User.class);

    // Finally we authentication. It's compared the user received with the defined en the class SecurityConfig
    return getAuthenticationManager().authenticate(
      new UsernamePasswordAuthenticationToken(
        user.getMail(),
        user.getPassword(),
        Collections.emptyList()
      )
    );
  }

  @Override
  protected void successfulAuthentication(
  HttpServletRequest req,
  HttpServletResponse res, FilterChain chain,
  Authentication auth) throws IOException, ServletException {
        
    // If the authentication was successful, we added the token to the answer
    //JwtUtil.addAuthentication(res, auth.getName());
    JwtUtil.addAuthentication(res, ((User)auth.getPrincipal()).getMail(), auth.getName());

  }
}
