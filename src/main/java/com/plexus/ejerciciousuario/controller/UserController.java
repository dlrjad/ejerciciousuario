package com.plexus.ejerciciousuario.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.plexus.ejerciciousuario.exception.ErrorRest;
import com.plexus.ejerciciousuario.exception.UserNotFoundException;
import com.plexus.ejerciciousuario.model.User;
import com.plexus.ejerciciousuario.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  @Qualifier("userRepository")
  UserRepository userRepository;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

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
  @ApiOperation(value = "Obtener todos los usuarios", response = User.class, responseContainer = "List")
  @GetMapping("/users")
  public ResponseEntity<?> getUsers() {
    try {
      logger.debug("Ejecutando peticion HTTP GET");
      logger.debug("Obteniendo los usuarios con HTTP GET");
      List<User> result = userRepository.findAll();
      return new ResponseEntity<List<User>>(result, HttpStatus.OK);
    } catch(Exception e) {
      logger.debug("Exception NOT_FOUND");
      throw new UserNotFoundException();
    }
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
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "long", paramType = "query"),
    @ApiImplicitParam(name = "response", value = "Http Response", required = true)
  })
  @ApiOperation(value = "Obtener un usuario por su id", response = User.class)
  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id, HttpServletResponse response) {
    try {
      logger.debug("Ejecutando peticion HTTP GET indicando una id");
      User result = userRepository.findById(id).get();
      //response.setStatus(200);
      logger.debug("Obteniendo usuario con HTTP GET indicando una id");
      return new ResponseEntity<User>(result, HttpStatus.OK);
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        //response.setStatus(404);
        logger.debug("Exception NOT_FOUND");
        throw new UserNotFoundException(id);
      } else {
        //response.setStatus(400);
        logger.debug("Exception BAD_REQUEST");
        return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
      }
    }
  }

  /*@GetMapping("/user/{name}/{password}")
  public ResponseEntity<?> getUserByNamePassword(@PathVariable String name, @PathVariable String password) {
    try {
      logger.debug("Ejecutando peticion HTTP GET indicando user y password");
      User result = userRepository.findByNameAndPassword(name, password);
      //logger.debug(result.getMail());
      return new ResponseEntity<User>(result, HttpStatus.OK);
    } catch(Exception e) {
      if(e.getMessage() == null) {
        //response.setStatus(404);
        logger.debug("Exception NULL");
        throw new UserNotFoundException();
      } else {
        //response.setStatus(400);
        logger.debug("Exception BAD_REQUEST");
        return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
      }
    }
  }*/

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
  @ApiImplicitParams({
    @ApiImplicitParam(name = "user", value = "User object", required = true, dataType = "object", paramType = "query"),
    @ApiImplicitParam(name = "response", value = "Http Response", required = true)
  })
  @ApiOperation(value = "Guardar un usuario", response = User.class)
  @PostMapping("/user")
  public ResponseEntity<?> createUser(@RequestBody User user, HttpServletResponse response) {
    if (user.equals(null)) {
      logger.debug("Exception BAD_REQUEST");
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del usuario a crear"), HttpStatus.BAD_REQUEST);
    }
    try {
      logger.debug("Ejecutando peticion HTTP POST");
      User newUser = new User(
        user.getName(),
        bCryptPasswordEncoder.encode(user.getPassword()),
        user.getMail()
      );
      //response.setStatus(201);
      logger.debug("Creando usuario con HTTP POST");
      return new ResponseEntity<User>(userRepository.save(newUser), HttpStatus.OK);
    } catch(Exception e) {
      //response.setStatus(400);
      logger.debug("Exception METHOD_NOT_ALLOWED");
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }
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
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "long", paramType = "query"),
    @ApiImplicitParam(name = "reqUser", value = "RequestEntity", required = true)
  })
  @ApiOperation(value = "Actualizar un usuario encontrado por su id", response = User.class)
  @PutMapping("/user/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, RequestEntity<User> reqUser) {
    if (reqUser.getBody().equals(null)) {
      logger.debug("Exception METHOD_NOT_ALLOWED");
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del usuario a modificar"), HttpStatus.METHOD_NOT_ALLOWED);
    } 
    try {
      Optional<User> aux = userRepository.findById(id);
      if (aux.isPresent()) {
        logger.debug("Ejecutando peticion HTTP PUT");
        User userUpdate = aux.get();
        userUpdate.setMail(reqUser.getBody().getMail());
        userUpdate.setName(reqUser.getBody().getName());
        userUpdate.setPassword(reqUser.getBody().getPassword());
        userUpdate.setRoles(reqUser.getBody().getRoles());
        logger.debug("Actualizando usuario con HTTP PUT");
        return new ResponseEntity<User>(userRepository.save(userUpdate), HttpStatus.OK);
      } else {
        throw new UserNotFoundException(id);
      }
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        logger.debug("Exception NOT_FOUND");
        throw new UserNotFoundException(id);
      }
      logger.debug("Exception BAD_REQUEST");
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
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
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "long", paramType = "query")
  })
  @ApiOperation(value = "Eliminar un usuario encontrado por su id", response = User.class)
  @DeleteMapping("/user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    try {
      logger.debug("Ejecutando peticion HTTP DELETE");
      User userDelete = userRepository.findById(id).get();
      userDelete.getRoles().clear();
      userRepository.delete(userDelete);
      logger.debug("Eliminando usuario con HTTP DELETE");
      return new ResponseEntity<User>(userDelete, HttpStatus.OK);
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        logger.debug("Exception NOT_FOUND");
        throw new UserNotFoundException(id);
      }
      logger.debug("Exception BAD_REQUEST"); 
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

}