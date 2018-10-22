package com.plexus.ejerciciousuario.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.plexus.ejerciciousuario.exception.ErrorRest;
import com.plexus.ejerciciousuario.exception.RoleNotFoundException;
import com.plexus.ejerciciousuario.model.Role;
import com.plexus.ejerciciousuario.repository.RoleRepository;

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
 * Clase controlador rol
 * 
 * @author dlrjad
 */

@RestController
@RequestMapping("/api")
@Api(value="rolemanagement", description="Operations to roles")
public class RoleController {

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
  @ApiOperation(value = "Obtener todos los roles")
  @GetMapping("/roles")
  public ResponseEntity<?> getRoles() {
    ResponseEntity<?> response;
    try {
      List<Role> result = roleRepository.findAll();
      response = new ResponseEntity<List<Role>>(result, HttpStatus.OK);
    } catch(Exception e) {
      throw new RoleNotFoundException();
    }
    return response;
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
  @ApiOperation(value = "Obtener un rol por su id")
  @GetMapping("/role/{id}")
  public ResponseEntity<?> getRole(@PathVariable Long id) {
    ResponseEntity<?> response;
    try {
      Role result = roleRepository.findById(id).get();
      response = new ResponseEntity<Role>(result, HttpStatus.OK);
    } catch(Exception e) {
      throw new RoleNotFoundException(id);
    }
    return response;
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
  @ApiOperation(value = "Guardar un rol")
  @PostMapping("/role")
  public ResponseEntity<?> createRole(@RequestBody Role role, HttpServletResponse response) {
    ResponseEntity<?> response_;
    Role newRole = new Role(
      role.getName()
    );
    if(!newRole.equals(null)) {
      response.setStatus(201);
      response_ = new ResponseEntity<Role>(roleRepository.save(newRole), HttpStatus.OK);
    }else {
      response_ = new ResponseEntity<ErrorRest>(new ErrorRest("Datos incorrectos para crear rol"), HttpStatus.BAD_REQUEST);
    }
    return response_;
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
  @ApiOperation(value = "Actualizar un rol encontrado por su id")
  @PutMapping("/role/{id}")
  public ResponseEntity<?> updateRole(@PathVariable Long id, RequestEntity<Role> reqRole) {
    if (reqRole.getBody() == null) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del rol a modificar"), HttpStatus.BAD_REQUEST);
    }
    if (!roleRepository.findById(id).equals(null)) {
      Role role = reqRole.getBody();
      Role roleUpdate = new Role(id, role.getName(), role.getUsers(), role.getPrivileges());
      return new ResponseEntity<Role>(roleRepository.save(roleUpdate), HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El rol a modificar no existe"),
        HttpStatus.NOT_FOUND);
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
  @ApiOperation(value = "Eliminar un rol encontrado por su id")
  @DeleteMapping("/role/{id}")
  public ResponseEntity<?> deleteRole(@PathVariable Long id) {
    Role roleDelete = roleRepository.findById(id).get();
    if (roleDelete != null) {
      roleRepository.delete(roleDelete);
      return new ResponseEntity<Role>(roleDelete, HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El rol a borrar no existe"), 
        HttpStatus.NOT_FOUND);
    }
  }

}