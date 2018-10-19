package com.plexus.ejerciciousuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 7295910574475009536L;

  public RoleNotFoundException() {
    super("No existe ningún rol");
  }
  public RoleNotFoundException(Long id) {
    super(String.format("No existe ningún rol con el ID = %d", id));
  }
  
}