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

@RestController
@RequestMapping("/role")
@Api(value="rolemanagement", description="Operations to roles")
public class RoleController {

  @Autowired
  @Qualifier("roleRepository")
  RoleRepository roleRepository;

  @ApiOperation(value = "Obtener todos los roles")
  @GetMapping("/roles")
  public ResponseEntity<?> getRoles() {
    ResponseEntity<?> response;
    List<Role> result = roleRepository.findAll();
    if (!result.equals(null)) 
      response = new ResponseEntity<List<Role>>(result, HttpStatus.OK);
    else {
      response = new ResponseEntity<List<Role>>(result, HttpStatus.NOT_FOUND);
      throw new RoleNotFoundException();
    }
    return response;
  }

  @ApiOperation(value = "Obtener un rol por su id")
  @GetMapping("/role/{id}")
  public ResponseEntity<?> getRole(@PathVariable Long id) {
    ResponseEntity<?> response;
    Role result = roleRepository.findOne(id);
    if (!result.equals(null))
      response = new ResponseEntity<Role>(result, HttpStatus.OK);
    else {
      response = new ResponseEntity<Role>(result, HttpStatus.NOT_FOUND);
      throw new RoleNotFoundException(id);
    }
    return response;
  }

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
      response_ = new ResponseEntity<Role>(roleRepository.save(newRole), HttpStatus.BAD_REQUEST);
    }
    return response_;
  }

  @ApiOperation(value = "Actualizar un rol encontrado por su id")
  @PutMapping("/role/{id}")
  public ResponseEntity<?> updateRole(@PathVariable Long id, RequestEntity<Role> reqRole) {
    if (reqRole.getBody() == null) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del rol a modificar"), HttpStatus.BAD_REQUEST);
    }
    if (!roleRepository.findOne(id).equals(null)) {
      Role role = reqRole.getBody();
      Role roleUpdate = new Role(id, role.getName());
      return new ResponseEntity<Role>(roleRepository.save(roleUpdate), HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El rol a modificar no existe"),
        HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "Eliminar un rol encontrado por su id")
  @DeleteMapping("/role/{id}")
  public ResponseEntity<?> deleteRole(@PathVariable Long id) {
    Role roleDelete = roleRepository.findOne(id);
    if (roleDelete != null) {
      roleRepository.delete(roleDelete);
      return new ResponseEntity<Role>(roleDelete, HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El rol a borrar no existe"), 
        HttpStatus.NOT_FOUND);
    }
  }

}