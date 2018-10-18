package com.plexus.ejerciciousuario.controller;

import java.util.List;

import com.plexus.ejerciciousuario.model.Privilege;
import com.plexus.ejerciciousuario.repository.PrivilegeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PrivilegeController {

  @Autowired
  PrivilegeRepository privilegeRepository;

  @GetMapping("/users")
  public List<Privilege> list() {
    List<Privilege> result = privilegeRepository.findAll();
    if (result != null)
      return result;
    else
      throw new PrivilegeNotFoundException();
  }

  @GetMapping("/empleado/{id}")
  public Privilege getUser(@PathVariable Long id) {
    Privilege result = privilegeRepository.findOne(id);
    if (result != null)
      return result;
    else
      throw new PrivilegeNotFoundException(id);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  private class PrivilegeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7295910574475009536L;

    public PrivilegeNotFoundException() {
      super("No existe ningún privilegio");
    }
    public PrivilegeNotFoundException(Long id) {
      super(String.format("No existe ningún privilegio con el ID = %d", id));
    }
    
  }

}