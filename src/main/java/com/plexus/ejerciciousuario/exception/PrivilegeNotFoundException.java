package com.plexus.ejerciciousuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PrivilegeNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 7295910574475009536L;

  public PrivilegeNotFoundException() {
    super("No existe ningún privilegio");
  }
  public PrivilegeNotFoundException(Long id) {
    super(String.format("No existe ningún privilegio con el ID = %d", id));
  }
  
}