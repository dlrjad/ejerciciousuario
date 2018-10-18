package com.plexus.ejerciciousuario.controller;

import java.util.List;

import com.plexus.ejerciciousuario.model.Role;
import com.plexus.ejerciciousuario.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleController {

  @Autowired
  RoleRepository roleRepository;

  @GetMapping("/users")
  public List<Role> list() {
    List<Role> result = roleRepository.findAll();
    if (result != null)
      return result;
    else
      throw new RoleNotFoundException();
  }

  @GetMapping("/empleado/{id}")
  public Role getUser(@PathVariable Long id) {
    Role result = roleRepository.findOne(id);
    if (result != null)
      return result;
    else
      throw new RoleNotFoundException(id);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  private class RoleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7295910574475009536L;

    public RoleNotFoundException() {
      super("No existe ningún rol");
    }
    public RoleNotFoundException(Long id) {
      super(String.format("No existe ningún rol con el ID = %d", id));
    }
    
  }

}