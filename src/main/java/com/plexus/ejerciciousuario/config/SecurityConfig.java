package com.plexus.ejerciciousuario.config;

import static com.plexus.ejerciciousuario.config.Constants.LOGIN_URL;

import com.plexus.ejerciciousuario.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig<UserService> extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("userService")
  private UserService userDetailsService;

  @Autowired
  private UserRepository userRepository;

  @Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
  }
  
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception { 
    /*
     * 1. Se desactiva el filtro CSRF
     * 2. Se indica que el login no requiere autenticación
     * 3. Se indica que el resto de URLs esten securizadas
     */
    httpSecurity
    .csrf().disable().authorizeRequests()
    .antMatchers(LOGIN_URL).permitAll() //permitimos el acceso a /login a cualquiera
    .antMatchers("/api/users").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    .anyRequest().authenticated()
    //.antMatchers("/api/users").access("hasRole('ROLE_ADMIN')")
    .and().httpBasic()
    .and()
    // Las peticiones /login pasaran previamente por este filtro
    .addFilterBefore(new LoginFilter(LOGIN_URL, authenticationManager()),
      UsernamePasswordAuthenticationFilter.class)

    // Las demás peticiones pasarán por este filtro para validar el token
    .addFilterBefore(new JwtFilter(userRepository),
      UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    // Se define la clase que recupera los usuarios y el algoritmo para procesar las passwords
    auth.userDetailsService((UserDetailsService) userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    //auth.inMemoryAuthentication().withUser("juan31@mail.com").password("password").roles("USER");
  }
  
}
