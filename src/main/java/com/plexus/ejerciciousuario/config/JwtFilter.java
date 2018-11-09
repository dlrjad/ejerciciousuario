package com.plexus.ejerciciousuario.config;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.plexus.ejerciciousuario.model.User;
import com.plexus.ejerciciousuario.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

import static com.plexus.ejerciciousuario.config.Constants.SUPER_SECRET_KEY;

/**
 * Las peticiones que no sean /login pasarán por este filtro y se encarga
 * de validar el token a traves de la request
 */
public class JwtFilter extends GenericFilterBean {

  private UserRepository userRepository;

  public JwtFilter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  private static Logger logger = LoggerFactory.getLogger(JwtFilter.class);

  @Override
  public void doFilter(ServletRequest request,
    ServletResponse response,
    FilterChain filterChain)
    throws IOException, ServletException {

      String token = ((HttpServletRequest) request).getHeader("Authorization");

      if (token != null) {
        String username = Jwts.parser()
                .setSigningKey(SUPER_SECRET_KEY) //la clave secreta esta declara en JwtUtil
                .parseClaimsJws(token) //este metodo es el que valida
                .getBody()
                .getSubject();

        
        logger.debug("usuariooooooooooo " + username);
        System.out.println("repositori" + userRepository);
      }

        String user = JwtUtil.getUser((HttpServletRequest) request);
        User aux = userRepository.findByName(user);
        System.out.println("qwrerewrqewr usuwriosssssss"+user);
        String[] roles = aux.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList())
.toArray(new String[aux.getRoles().size()]);
System.out.println("qwrerewrqewr rolesssssssss"+roles);


        //com.plexus.ejerciciousuario.model.User user = userRepository.findByMail("juan31@mail.com");
        //System.out.println("roleesssssssssssssssssssssssssssssssssssssssss" +user.getRoles());

      

      Authentication authentication = JwtUtil.getAuthentication((HttpServletRequest)request, roles);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);

  }
}
