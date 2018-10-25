package com.plexus.ejerciciousuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -7295910574475009536L;

  public UserNotFoundException() {
    super("No existe ningún usuario");
  }
  public UserNotFoundException(Long id) {
    super(String.format("No existe ningún usuario con el ID = %d", id));
  }

}