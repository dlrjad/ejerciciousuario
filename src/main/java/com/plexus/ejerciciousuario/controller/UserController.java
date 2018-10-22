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

/**
 * Clase controlador usuario
 * 
 * @author dlrjad
 */

@RestController
@RequestMapping("/api")
@Api(value="usermanagement", description="Operations to users")
public class UserController {

  @Autowired
  @Qualifier("userRepository")
  UserRepository userRepository;

  /**
   * Método GET obtener todos los usuarios
   * @return retorna todos los usuarios en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al obtener lista de usuarios"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Obtener todos los usuarios")
  @GetMapping("/users")
  public ResponseEntity<?> getUsers() {
    ResponseEntity<?> response;
    try {
      List<User> result = userRepository.findAll();
      response = new ResponseEntity<List<User>>(result, HttpStatus.OK);
    } catch(Exception e) {
      throw new UserNotFoundException();
    }
    return response;
  }

  /**
   * Método GET obtener un usuario
   * @param id
   * @param response
   * @return retorna un usuario en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al obtener un usuario"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Obtener un usuario por su id")
  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id, HttpServletResponse response) {
    ResponseEntity<?> response_=null;
    try {
      User result = userRepository.findById(id).get();
      //response.setStatus(201);
      response_ = new ResponseEntity<User>(result, HttpStatus.OK);
    } catch(Exception e) {
      //response.setStatus(404);
      throw new UserNotFoundException(id);
    }
    return response_;
  }

  /**
   * Método POST para crear usuario
   * @param user
   * @param response
   * @return retorna usuario creado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al crear usuario"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Guardar un usuario")
  @PostMapping("/user")
  public ResponseEntity<?> createUser(@RequestBody User user, HttpServletResponse response) {
    ResponseEntity<?> response_;
    User newUser = new User(
      user.getName(),
      user.getMail(),
      user.getRoles()
    );
    if(!newUser.equals(null) && user.getName()!="") {
      //response.setStatus(201);
      response_ = new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
    } else {
      //response.setStatus(400);
      response_ = new ResponseEntity<ErrorRest>(new ErrorRest("Datos incorrectos para crear usuario"), HttpStatus.BAD_REQUEST);
    }
    return response_;
  }

  /**
   * Método PUT para actualizar usuario
   * @param id
   * @param reqUser
   * @return retorna usuario actualizado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al actualizar usuario"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Actualizar un usuario encontrado por su id")
  @PutMapping("/user/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, RequestEntity<User> reqUser) {
    if (reqUser.getBody() == null) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del usuario a modificar"), HttpStatus.BAD_REQUEST);
    }
    if (userRepository.findById(id) != null) {
      User user = reqUser.getBody();
      User userUpdate = new User(id, user.getName(), user.getMail(), user.getRoles());
      return new ResponseEntity<User>(userRepository.save(userUpdate), HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El usuario a modificar no existe"),
        HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Método DELETE para eliminar usuario
   * @param id
   * @return usuario eliminado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al borrar usuario"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Eliminar un usuario encontrado por su id")
  @DeleteMapping("/user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    User userDelete = userRepository.findById(id).get();
    if (userDelete != null) {
      userRepository.delete(userDelete);
      return new ResponseEntity<User>(userDelete, HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El usuario a borrar no existe"), 
        HttpStatus.NOT_FOUND);
    }
  }

}