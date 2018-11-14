package com.plexus.ejerciciousuario.config;

import static com.plexus.ejerciciousuario.constant.Constants.LOGIN_URL;

import com.plexus.ejerciciousuario.repository.UserRepository;
import com.plexus.ejerciciousuario.security.JwtFilter;
import com.plexus.ejerciciousuario.security.LoginFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
  @Qualifier("userDetailsServiceImpl")
  private UserDetailsService userDetailsService;

  @Autowired
  @Qualifier("userRepository")
  private UserRepository userRepository;

  @Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
  }
  
  /*
  * 1. The CSRF filter is deactivated
  * 2. It's indicate the login not require authentication
  * 3. It's indicate that the rest of URLs are secured
  */
  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception { 
    httpSecurity
    .cors().and()
    .csrf().disable().authorizeRequests()
    .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
    .antMatchers(HttpMethod.GET, "/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    .antMatchers(HttpMethod.POST, "/**").access("hasRole('ROLE_ADMIN')")
    .antMatchers(HttpMethod.PUT, "/**").access("hasRole('ROLE_ADMIN')")
    .antMatchers(HttpMethod.DELETE, "/**").access("hasRole('ROLE_ADMIN')")
    .anyRequest().authenticated()
    //.and().httpBasic()
    .and()
    // The request /login will go through this filter
    .addFilterBefore(new LoginFilter(LOGIN_URL, authenticationManager()),
      UsernamePasswordAuthenticationFilter.class)
    // The other requests will go through this filter to validate the token
    .addFilterBefore(new JwtFilter(userRepository),
      UsernamePasswordAuthenticationFilter.class);
  }

  // It's defined the class that recovery the users and the algorithm to process the passwords
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    auth.userDetailsService(userDetailsService);
  }
  
}
