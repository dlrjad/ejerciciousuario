package com.plexus.ejerciciousuario.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.plexus.ejerciciousuario.exception.ErrorRest;
import com.plexus.ejerciciousuario.exception.RoleNotFoundException;
import com.plexus.ejerciciousuario.model.Role;
import com.plexus.ejerciciousuario.repository.RoleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controlador rol
 * 
 * @author dlrjad
 */

@RestController
@RequestMapping("/api")
@Api(value="rolemanagement", description="Operations to roles")
public class RoleController {

  private final Logger logger = LoggerFactory.getLogger(RoleController.class);

  @Autowired
  @Qualifier("roleRepository")
  RoleRepository roleRepository;

  /**
   * Método GET obtener todos los roles
   * @return retorna todos los roles en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al obtener lista de roles"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Obtener todos los roles", response = Role.class, responseContainer = "List")
  @GetMapping("/roles")
  public ResponseEntity<?> getRoles() {
    try {
      logger.debug("Ejecutando peticion HTTP GET");
      logger.debug("Obteniendo los roles con HTTP GET");
      List<Role> result = roleRepository.findAll();
      return new ResponseEntity<List<Role>>(result, HttpStatus.OK);
    } catch(Exception e) {
      logger.debug("Exception NOT_FOUND");
      throw new RoleNotFoundException();
    }
  }

  /**
   * Método GET obtener un rol
   * @param id
   * @return retorna un rol en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al obtener un rol"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "Role ID", required = true, dataType = "long", paramType = "query"),
    @ApiImplicitParam(name = "response", value = "Http Response", required = true)
  })
  @ApiOperation(value = "Obtener un rol por su id", response = Role.class)
  @GetMapping("/role/{id}")
  public ResponseEntity<?> getRole(@PathVariable Long id) {
    try {
      logger.debug("Ejecutando peticion HTTP GET indicando una id");
      Role result = roleRepository.findById(id).get();
      logger.debug("Obteniendo rol con HTTP GET indicando una id");
      return new ResponseEntity<Role>(result, HttpStatus.OK);
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        logger.debug("Exception NOT_FOUND");
        throw new RoleNotFoundException(id);
      } else {
        logger.debug("Exception BAD_REQUEST");
        return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
      }
    }
  }

  /**
   * Método POST para crear rol
   * @param role
   * @param response
   * @return retorna rol creado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al crear un rol"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiImplicitParams({
    @ApiImplicitParam(name = "role", value = "Role object", required = true, dataType = "object", paramType = "query"),
    @ApiImplicitParam(name = "response", value = "Http Response", required = true)
  })
  @ApiOperation(value = "Guardar un rol", response = Role.class)
  @PostMapping("/role")
  public ResponseEntity<?> createRole(@RequestBody Role role, HttpServletResponse response) {
    if (role.equals(null)) {
      logger.debug("Exception BAD_REQUEST");
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del rol a crear"), HttpStatus.BAD_REQUEST);
    }
    try {
      logger.debug("Ejecutando petición HTTP POST");
      Role newRole = new Role(
        role.getName()
      );
      //response.setStatus(201);
      logger.debug("Creando usuario con HTTP POST");
      return new ResponseEntity<Role>(roleRepository.save(newRole), HttpStatus.OK);
    } catch(Exception e) {
      logger.debug("Exception METHOD_NOT_ALLOWED");
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }
  }

  /**
   * Método PUT para actualizar rol
   * @param id
   * @param reqRole
   * @return retorna rol actualizado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al actualizar rol"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "Role ID", required = true, dataType = "long", paramType = "query"),
    @ApiImplicitParam(name = "reqRole", value = "RequestEntity", required = true)
  })
  @ApiOperation(value = "Actualizar un rol encontrado por su id", response = Role.class)
  @PutMapping("/role/{id}")
  public ResponseEntity<?> updateRole(@PathVariable Long id, RequestEntity<Role> reqRole) {
    if (reqRole.getBody() == null) {
      logger.debug("Exception METHOD_NOT_ALLOWED");
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del rol a modificar"), HttpStatus.METHOD_NOT_ALLOWED);
    } 
    try {
      Optional<Role> aux = roleRepository.findById(id);
      if (aux.isPresent()) {
        logger.debug("Ejecutando peticion HTTP PUT");
        Role roleUpdate = aux.get();
        roleUpdate.setPrivileges(reqRole.getBody().getPrivileges());
        roleUpdate.setUser(reqRole.getBody().getUsers());
        roleUpdate.setName(reqRole.getBody().getName());
        logger.debug("Actualizando rol con HTTP PUT");
        return new ResponseEntity<Role>(roleRepository.save(roleUpdate), HttpStatus.OK);
      } else {
        throw new RoleNotFoundException(id);
      } 
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        logger.debug("Exception NOT_FOUND");
        throw new RoleNotFoundException(id);
      }
      logger.debug("Exception BAD_REQUEST");
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Método DELETE para eliminar rol
   * @param id
   * @return rol eliminado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al borrar rol"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "Role ID", required = true, dataType = "long", paramType = "query")
  })
  @ApiOperation(value = "Eliminar un rol encontrado por su id", response = Role.class)
  @DeleteMapping("/role/{id}")
  public ResponseEntity<?> deleteRole(@PathVariable Long id) {
    try {
      logger.debug("Ejecutando petición con HTTP DELETE");
      Role roleDelete = roleRepository.findById(id).get();
      roleRepository.delete(roleDelete);
      logger.debug("Eliminando rol con HTTP DELETE");
      return new ResponseEntity<Role>(roleDelete, HttpStatus.OK);
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        logger.debug("Exception NOT_FOUND");
        throw new RoleNotFoundException(id);
      }
      logger.debug("Exception BAD_REQUEST"); 
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

}