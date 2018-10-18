package com.plexus.ejerciciousuario.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.plexus.ejerciciousuario.model.ErrorRest;
import com.plexus.ejerciciousuario.model.User;
import com.plexus.ejerciciousuario.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @GetMapping("/usuarios")
  public List<User> list() {
    List<User> result = userRepository.findAll();
    if (result != null)
      return result;
    else
      throw new UserNotFoundException();
  }

  @GetMapping("/usuario/{id}")
  public User getUser(@PathVariable Long id) {
    User result = userRepository.findOne(id);
    if (result != null)
      return result;
    else
      throw new UserNotFoundException(id);
  }

  @PostMapping("/usuario")
  public User createEmpleado(@RequestBody User user, HttpServletResponse response) {
    User nuevo = new User(
      user.getName(),
      user.getMail()
    );
    response.setStatus(201);
    return userRepository.save(nuevo);
  }

  @PutMapping("/usuario/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, RequestEntity<User> reqEmpleado) {
    if (reqEmpleado.getBody() == null) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del usuario a modificar"), HttpStatus.BAD_REQUEST);
    }
    if (userRepository.findOne(id) != null) {
      User user = reqEmpleado.getBody();
      User aActualizar = new User(id, user.getName(), user.getMail());
    return new ResponseEntity<User>(userRepository.save(aActualizar), HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El usuario a modificar no existe"),
        HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/usuario/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    User aBorrar = userRepository.findOne(id);
      if (aBorrar != null) {
        userRepository.delete(aBorrar);
      return new ResponseEntity<User>(aBorrar, HttpStatus.OK);
      } else {
         return new ResponseEntity<ErrorRest>(new ErrorRest("El usuario a borrar no existe"), 
          HttpStatus.NOT_FOUND);
    }
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