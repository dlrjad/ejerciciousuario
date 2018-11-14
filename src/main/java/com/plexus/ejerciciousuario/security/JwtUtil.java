package com.plexus.ejerciciousuario.security;

import static com.plexus.ejerciciousuario.constant.Constants.SUPER_SECRET_KEY;
import static com.plexus.ejerciciousuario.constant.Constants.TOKEN_EXPIRATION_TIME;
import static com.plexus.ejerciciousuario.constant.Constants.HEADER_AUTHORIZACION_KEY;
import static com.plexus.ejerciciousuario.constant.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
  
  private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  // Method to create the JWT and send the client the response's header
  static void addAuthentication(HttpServletResponse res, String name) throws IOException {

    String token = Jwts.builder()
    .setSubject(name)
    .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
    .signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY)
    .compact();

    // Add header to the token
    res.addHeader(HEADER_AUTHORIZACION_KEY, token);
    res.getWriter().write(String.format("%s %s",TOKEN_BEARER_PREFIX, token));
    res.flushBuffer();
    logger.debug("TOKEN: "+ String.format("%s %s",TOKEN_BEARER_PREFIX, token));
  
  }

  // Method to valid the token send by the client
  static Authentication getAuthentication(HttpServletRequest request, String[] roles) {

    // We get the  token that it's in the request's header
    String token = request.getHeader(HEADER_AUTHORIZACION_KEY);

    // If there's a token then we valid it
    if (token != null) {
      String user = Jwts.parser()
        .setSigningKey(SUPER_SECRET_KEY)
        .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, "")) //este metodo es el que valida
        .getBody()
        .getSubject();

      // The requests that aren't /login not require a authentication by username/password 
      // And by this motive we can return a UsernamePasswordAuthenticationToken without password
      return user != null ?
        new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList(roles)) :
        null;
    }
    return null;
  }

  static String getUser(HttpServletRequest request) {
    String user = null;
    String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
    if (token != null) {
        user = Jwts.parser().setSigningKey(SUPER_SECRET_KEY).parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, "")).getBody().getSubject();
    }
    return user;
  }

}
