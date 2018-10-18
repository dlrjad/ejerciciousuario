package com.plexus.ejerciciousuario.controller;

import java.util.List;

import com.plexus.ejerciciousuario.model.User;
import com.plexus.ejerciciousuario.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @GetMapping("/users")
  public List<User> list() {
    List<User> result = userRepository.findAll();
    if (result != null)
      return result;
    else
      throw new UserNotFoundException();
  }

  @GetMapping("/empleado/{id}")
  public User getUser(@PathVariable Long id) {
    User result = userRepository.findOne(id);
    if (result != null)
      return result;
    else
      throw new UserNotFoundException(id);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  private class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7295910574475009536L;

    public UserNotFoundException() {
      super("No existe ningún usuario");
    }
    public UserNotFoundException(Long id) {
      super(String.format("No existe ningún empleado con el ID = %d", id));
    }

  }

}