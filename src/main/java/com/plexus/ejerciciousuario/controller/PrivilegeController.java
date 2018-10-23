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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
  @ApiOperation(value = "Obtener todos los privilegios", response = Privilege.class, responseContainer = "List")
  @GetMapping("/privileges")
  public ResponseEntity<?> getPrivileges() {
    try {
      List<Privilege> result = privilegeRepository.findAll();
      return new ResponseEntity<List<Privilege>>(result, HttpStatus.OK);
    } catch(Exception e) {
      throw new PrivilegeNotFoundException();
    }
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
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "Privilege ID", required = true, dataType = "long", paramType = "query"),
    @ApiImplicitParam(name = "response", value = "Http Response", required = true)
  })
  @ApiOperation(value = "Obtener un rol por su id", response = Privilege.class)
  @GetMapping("/privilege/{id}")
  public ResponseEntity<?> getPrivilege(@PathVariable Long id) {
    try {
      Privilege result = privilegeRepository.findById(id).get();
      return new ResponseEntity<Privilege>(result, HttpStatus.OK);
    }catch(Exception e) {
      throw new PrivilegeNotFoundException(id);
    }
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
  @ApiImplicitParams({
    @ApiImplicitParam(name = "privilege", value = "Privilege object", required = true, dataType = "object", paramType = "query"),
    @ApiImplicitParam(name = "response", value = "Http Response", required = true)
  })
  @ApiOperation(value = "Guardar un privilegio", response = Privilege.class)
  @PostMapping("/privilege")
  public ResponseEntity<?> createPrivilege(@RequestBody Privilege privilege, HttpServletResponse response) {
    Privilege newPrivilege = new Privilege(
      privilege.getName()
    );
    if(!newPrivilege.equals(null)) {
      //response.setStatus(201);
      return new ResponseEntity<Privilege>(privilegeRepository.save(newPrivilege), HttpStatus.OK);
    }else {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Datos incorrectos para crear privilegio"), HttpStatus.BAD_REQUEST);
    }
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
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "Privilege ID", required = true, dataType = "long", paramType = "query"),
    @ApiImplicitParam(name = "reqPrivilege", value = "RequestEntity", required = true)
  })
  @ApiOperation(value = "Actualizar un privilegio encontrado por su id", response = Privilege.class)
  @PutMapping("/privilege/{id}")
  public ResponseEntity<?> updatePrivilege(@PathVariable Long id, RequestEntity<Privilege> reqPrivilege) {
    if (reqPrivilege.getBody() == null) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del privilegio a modificar"), HttpStatus.BAD_REQUEST);
    }
    try {
      if (!privilegeRepository.findById(id).equals(null)) {
        Privilege privilege = reqPrivilege.getBody();
        Privilege privilegeUpdate = new Privilege(id, privilege.getName(), privilege.getRoles());
        return new ResponseEntity<Privilege>(privilegeRepository.save(privilegeUpdate), HttpStatus.OK);
      } else {
        throw new PrivilegeNotFoundException(id);
      }
    } catch(Exception e) {
      throw e;
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
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "Privilege ID", required = true, dataType = "long", paramType = "query")
  })
  @ApiOperation(value = "Eliminar un privilegio encontrado por su id", response = Privilege.class)
  @DeleteMapping("/privilege/{id}")
  public ResponseEntity<?> deletePrivilege(@PathVariable Long id) {
    try {
      Privilege privilegeDelete = privilegeRepository.findById(id).get();
      privilegeRepository.delete(privilegeDelete);
      return new ResponseEntity<Privilege>(privilegeDelete, HttpStatus.OK);
    } catch(Exception e) {
      throw new PrivilegeNotFoundException(id);
    }
  }

}