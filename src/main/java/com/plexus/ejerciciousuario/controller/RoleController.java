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
      List<Role> result = roleRepository.findAll();
      return new ResponseEntity<List<Role>>(result, HttpStatus.OK);
    } catch(Exception e) {
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
      Role result = roleRepository.findById(id).get();
      return new ResponseEntity<Role>(result, HttpStatus.OK);
    } catch(Exception e) {
      throw new RoleNotFoundException(id);
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
    Role newRole = new Role(
      role.getName()
    );
    if(!newRole.equals(null)) {
      //response.setStatus(201);
      return new ResponseEntity<Role>(roleRepository.save(newRole), HttpStatus.OK);
    }else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Datos incorrectos para crear rol"), HttpStatus.BAD_REQUEST);
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
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del rol a modificar"), HttpStatus.BAD_REQUEST);
    } 
    try {
      if (!roleRepository.findById(id).equals(null) && roleRepository.existsById(id)) {
        Role role = reqRole.getBody();
        Role roleUpdate = new Role(id, role.getName(), role.getUsers(), role.getPrivileges());
        return new ResponseEntity<Role>(roleRepository.save(roleUpdate), HttpStatus.OK);
      } else {
        throw new RoleNotFoundException(id);
      } 
    } catch(Exception e) {
      throw e;
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
      Role roleDelete = roleRepository.findById(id).get();
      roleRepository.delete(roleDelete);
      return new ResponseEntity<Role>(roleDelete, HttpStatus.OK);
    } catch(Exception e) {
      throw new RoleNotFoundException(id);
    }
  }

}