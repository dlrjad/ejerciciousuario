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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controlador privilegio
 * 
 * @author dlrjad
 */

@RestController
@RequestMapping("/api")
@Api(value="privilegemanagement", description="Operations to privileges")
public class PrivilegeController {

  @Autowired
  @Qualifier("privilegeRepository")
  PrivilegeRepository privilegeRepository;

  /**
   * Método GET obtener todos los privilegios
   * @return retorna todos los privilegios en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al obtener lista de privilegios"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Obtener todos los privilegios")
  @GetMapping("/privileges")
  public ResponseEntity<?> getPrivileges() {
    ResponseEntity<?> response;
    try {
      List<Privilege> result = privilegeRepository.findAll();
      response = new ResponseEntity<List<Privilege>>(result, HttpStatus.OK);
    } catch(Exception e) {
      throw new PrivilegeNotFoundException();
    }
    return response;
  }

  /**
   * Método GET obtener un privilegio
   * @param id
   * @return retorna un privilegio en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al obtener un privilegio"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Obtener un rol por su id")
  @GetMapping("/privilege/{id}")
  public ResponseEntity<?> getPrivilege(@PathVariable Long id) {
    ResponseEntity<?> response;
    try {
      Privilege result = privilegeRepository.findById(id).get();
      response = new ResponseEntity<Privilege>(result, HttpStatus.OK);
    }catch(Exception e) {
      throw new PrivilegeNotFoundException(id);
    }
    return response;
  }

  /**
   * Método POST para crear privilegio
   * @param privilege
   * @param response
   * @return retorna privilegio creado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al crear privilegio"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Guardar un privilegio")
  @PostMapping("/privilege")
  public ResponseEntity<?> createPrivilege(@RequestBody Privilege privilege, HttpServletResponse response) {
    ResponseEntity<?> response_;
    Privilege newPrivilege = new Privilege(
      privilege.getName()
    );
    if(!newPrivilege.equals(null)) {
      //response.setStatus(201);
      response_ = new ResponseEntity<Privilege>(privilegeRepository.save(newPrivilege), HttpStatus.OK);
    }else {
      response_ = new ResponseEntity<ErrorRest>(new ErrorRest("Datos incorrectos para crear privilegio"), HttpStatus.BAD_REQUEST);
    }
    return response_;
  }

  /**
   * Método PUT para actualizar privilegio
   * @param id
   * @param reqPrivilege
   * @return retorna privilegio actualizado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al actualizar privilegio"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Actualizar un privilegio encontrado por su id")
  @PutMapping("/privilege/{id}")
  public ResponseEntity<?> updatePrivilege(@PathVariable Long id, RequestEntity<Privilege> reqPrivilege) {
    if (reqPrivilege.getBody() == null) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del privilegio a modificar"), HttpStatus.BAD_REQUEST);
    }
    if (!privilegeRepository.findById(id).equals(null)) {
      Privilege privilege = reqPrivilege.getBody();
      Privilege privilegeUpdate = new Privilege(id, privilege.getName(), privilege.getRoles());
      return new ResponseEntity<Privilege>(privilegeRepository.save(privilegeUpdate), HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El privilegio a modificar no existe"),
        HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Método DELETE para eliminar privilegio
   * @param id
   * @return privilegio eliminado en caso de éxito
   */
  @ApiResponses(value = 
    {
      @ApiResponse(code = 200, message = "Éxito al borrar privilegio"),
      @ApiResponse(code = 401, message = "Sin autorización para ver el recurso"),
      @ApiResponse(code = 403, message = "Acceso prohibido al recurso"),
      @ApiResponse(code = 404, message = "Resultado no encontrado")
    }
  )
  @ApiOperation(value = "Eliminar un privilegio encontrado por su id")
  @DeleteMapping("/privilege/{id}")
  public ResponseEntity<?> deletePrivilege(@PathVariable Long id) {
    Privilege privilegeDelete = privilegeRepository.findById(id).get();
    if (privilegeDelete != null) {
      privilegeRepository.delete(privilegeDelete);
      return new ResponseEntity<Privilege>(privilegeDelete, HttpStatus.OK);
    } else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("El privilegio a borrar no existe"), 
        HttpStatus.NOT_FOUND);
    }
  }

}