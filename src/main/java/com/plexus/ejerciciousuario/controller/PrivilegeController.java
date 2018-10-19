package com.plexus.ejerciciousuario.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.plexus.ejerciciousuario.exception.ErrorRest;
import com.plexus.ejerciciousuario.exception.PrivilegeNotFoundException;
import com.plexus.ejerciciousuario.model.Privilege;
import com.plexus.ejerciciousuario.repository.PrivilegeRepository;

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
@RequestMapping("/privilege")
@Api(value="privilegemanagement", description="Operations to privileges")
public class PrivilegeController {

  //private static final String PRIVILEGE_REPOSITORY = "privilegeRepository";
  @Autowired
  @Qualifier("privilegeRepository")
  PrivilegeRepository privilegeRepository;

  @ApiOperation(value = "Obtener todos los privilegios")
  @GetMapping("/privileges")
  public ResponseEntity<?> getPrivileges() {
    ResponseEntity<?> response;
    List<Privilege> result = privilegeRepository.findAll();
    if (!result.equals(null)) 
      response = new ResponseEntity<List<Privilege>>(result, HttpStatus.OK);
    else {
      response = new ResponseEntity<List<Privilege>>(result, HttpStatus.NOT_FOUND);
      throw new PrivilegeNotFoundException();
    }
    return response;
  }

  @ApiOperation(value = "Obtener un rol por su id")
  @GetMapping("/privilege/{id}")
  public ResponseEntity<?> getPrivilege(@PathVariable Long id) {
    ResponseEntity<?> response;
    Privilege result = privilegeRepository.findOne(id);
    if (!result.equals(null))
      response = new ResponseEntity<Privilege>(result, HttpStatus.OK);
    else {
      response = new ResponseEntity<Privilege>(result, HttpStatus.NOT_FOUND);
      throw new PrivilegeNotFoundException(id);
    }
    return response;
  }

  @ApiOperation(value = "Guardar un privilegio")
  @PostMapping("/privilege")
  public ResponseEntity<?> createPrivilege(@RequestBody Privilege privilege, HttpServletResponse response) {
    ResponseEntity<?> response_;
    Privilege newPrivilege = new Privilege(
      privilege.getName()
    );
    if(!newPrivilege.equals(null)) {
      response.setStatus(201);
      response_ = new ResponseEntity<Privilege>(privilegeRepository.save(newPrivilege), HttpStatus.OK);
    }else {
      response_ = new ResponseEntity<Privilege>(privilegeRepository.save(newPrivilege), HttpStatus.BAD_REQUEST);
    }
    return response_;
  }

  @ApiOperation(value = "Actualizar un privilegio encontrado por su id")
  @PutMapping("/privilege/{id}")
  public ResponseEntity<?> updatePrivilege(@PathVariable Long id, RequestEntity<Privilege> reqPrivilege) {
    if (reqPrivilege.getBody() == null) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del privilegio a modificar"), HttpStatus.BAD_REQUEST);
    }
    if (!privilegeRepository.findOne(id).equals(null)) {
      Privilege privilege = reqPrivilege.getBody();
      Privilege privilegeUpdate = new Privilege(id, privilege.getName());
      return new ResponseEntity<Privilege>(privilegeRepository.save(privilegeUpdate), HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El privilegio a modificar no existe"),
        HttpStatus.NOT_FOUND);
    }
  }

  @ApiOperation(value = "Eliminar un privilegio encontrado por su id")
  @DeleteMapping("/privilege/{id}")
  public ResponseEntity<?> deletePrivilege(@PathVariable Long id) {
    Privilege privilegeDelete = privilegeRepository.findOne(id);
    if (privilegeDelete != null) {
      privilegeRepository.delete(privilegeDelete);
      return new ResponseEntity<Privilege>(privilegeDelete, HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El privilegio a borrar no existe"), 
        HttpStatus.NOT_FOUND);
    }
  }

}