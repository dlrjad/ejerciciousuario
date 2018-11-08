package com.plexus.ejerciciousuario.config;

import static com.plexus.ejerciciousuario.config.Constants.SUPER_SECRET_KEY;
import static com.plexus.ejerciciousuario.config.Constants.TOKEN_EXPIRATION_TIME;

import static java.util.Collections.emptyList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
  
  private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  // Método para crear el JWT y enviarlo al cliente en el header de la respuesta
  static void addAuthentication(HttpServletResponse res, String username) {

    String token = Jwts.builder()
    .setSubject(username)
    
    // TOKEN_EXPIRATION_TIME = 10 days
    .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))

    .signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY)
    .compact();

    //agregamos al encabezado el token
    res.addHeader("Authorization", token);
    
    logger.debug("TOKEN: "+ token);
  }

  // Método para validar el token enviado por el cliente
  static Authentication getAuthentication(HttpServletRequest request) {

    // Obtenemos el token que viene en el encabezado de la peticion
    String token = request.getHeader("Authorization");

    // si hay un token presente, entonces lo validamos
    if (token != null) {
      String user = Jwts.parser()
        .setSigningKey(SUPER_SECRET_KEY)
        .parseClaimsJws(token) //este metodo es el que valida
        .getBody()
        .getSubject();

      // Las peticiones que no sean /login no requieren una autenticacion por username/password 
      // por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin password
      return user != null ?
        new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
        null;
    }
    return null;
  }
}
