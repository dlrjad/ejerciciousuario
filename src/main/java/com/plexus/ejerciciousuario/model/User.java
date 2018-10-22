package com.plexus.ejerciciousuario.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase entidad usuario
 * 
 * @author dlrjad
 */

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", unique=true)
  @ApiModelProperty(notes = "The database generated user ID")
  private Long id;

  @Column(name = "name_user", length = 50)
  @ApiModelProperty(notes = "Nombre del usuario")
  private String name;

  @Column(name = "email", length = 50)
  @ApiModelProperty(notes = "Email del usuario")
  private String mail;

  @ManyToMany(cascade = { 
    CascadeType.ALL
  })
  @JoinTable(name="user_role",
    joinColumns = {@JoinColumn(name="user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")}
  )
  @JsonBackReference
  private Set<Role> roles;

  public User() {}

  public User(Long id){
    this.id = id;
  }

  public User(String name, String email, Set<Role> roles) {
    this.name = name;
    this.mail = email;
    this.roles = roles;
  }

  public User(Long id, String name, String email, Set<Role> roles) {
    this.id = id;
    this.name = name;
    this.mail = email;
    this.roles = roles;
  }

  /**
   * Constructor para inicializar los atributos name y email
   * @param name
   * @param email
   */
  public User(String name, String email) {
    this.name = name;
    this.mail = email;
  }

  /**
   * Método obtener nombre
   * @return String retorna el nombre
   */
  public String getName() {
    return this.name;
  }

  /**
   * Método obtener mail
   * @return String retorna el mail
   */
  public String getMail() {
	  return this.mail;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}