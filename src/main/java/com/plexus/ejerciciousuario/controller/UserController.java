package com.plexus.ejerciciousuario.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.plexus.ejerciciousuario.exception.ErrorRest;
import com.plexus.ejerciciousuario.exception.UserNotFoundException;
import com.plexus.ejerciciousuario.model.User;
import com.plexus.ejerciciousuario.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user") //api
@Api(value="usermanagement", description="Operations to users")
public class UserController {

  @Autowired
  @Qualifier("userRepository")
  UserRepository userRepository;

  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Successfully retrieved list"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
  )

  @ApiOperation(value = "Obtener todos los usuarios")
  @GetMapping("/users")
  public ResponseEntity<?> getUsers() {
    ResponseEntity<?> response;
    List<User> result = userRepository.findAll();
    if (result != null) 
      response = new ResponseEntity<List<User>>(result, HttpStatus.OK);
    else {
      response = new ResponseEntity<List<User>>(result, HttpStatus.NOT_FOUND);
      throw new UserNotFoundException();
    }
    return response;  
  }

  @ApiOperation(value = "Obtener un usuario por su id")
  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    ResponseEntity<?> response;
    User result = userRepository.findOne(id);
    if (!result.equals(null))
      response = new ResponseEntity<User>(result, HttpStatus.OK);
    else {
      response = new ResponseEntity<User>(result, HttpStatus.NOT_FOUND);
      throw new UserNotFoundException(id);
    }    
    return response;
  }

  @ApiOperation(value = "Guardar un usuario")
  @PostMapping("/user")
  public ResponseEntity<?> createUser(@RequestBody User user, HttpServletResponse response) {
    ResponseEntity<?> response_;
    User newUser = new User(
      user.getName(),
      user.getMail()
    );
    if(!newUser.equals(null)) {
      response.setStatus(201);
      response_ = new ResponseEntity<User>(userRepository.save(newUser), HttpStatus.OK);
    }else {
      response_ = new ResponseEntity<User>(userRepository.save(newUser), HttpStatus.BAD_REQUEST);
    }
    return response_;
  }

  @ApiOperation(value = "Actualizar un usuario encontrado por su id")
  @PutMapping("/usuario/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, RequestEntity<User> reqUser) {
    if (reqUser.getBody() == null) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del usuario a modificar"), HttpStatus.BAD_REQUEST);
    }
    if (!userRepository.findOne(id).equals(null)) {
      User user = reqUser.getBody();
      User userUpdate = new User(id, user.getName(), user.getMail());
      return new ResponseEntity<User>(userRepository.save(userUpdate), HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El usuario a modificar no existe"),
        HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "Eliminar un usuario encontrado por su id")
  @DeleteMapping("/usuario/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    User userDelete = userRepository.findOne(id);
    if (userDelete != null) {
      userRepository.delete(userDelete);
      return new ResponseEntity<User>(userDelete, HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El usuario a borrar no existe"), 
        HttpStatus.NOT_FOUND);
    }
  }

}