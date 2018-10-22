package com.plexus.ejerciciousuario.exception;

public class ErrorRest extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  private String mensaje;

  public ErrorRest() {}

  public ErrorRest(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getMensaje() {
    return this.mensaje;
  }

}