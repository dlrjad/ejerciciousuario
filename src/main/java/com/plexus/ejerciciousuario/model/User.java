package com.plexus.ejerciciousuario.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Clase entidad user
 * 
 * @author dlrjad
 */

@Entity
@Table(name = "users")
@ApiModel(value="Identificador", description="Clase entidad user")
public class User implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", unique=true)
  @ApiModelProperty(notes = "The database generated user ID")
  private Long user_id;

  
  @Column(name = "name_user", length = 50)
  @ApiModelProperty(notes = "Nombre del usuario")
  private String name;

  @Column(name = "password", length = 255)
  @ApiModelProperty(notes = "Password del usuario")
  private String password;

  @Column(name = "email", length = 50)
  @ApiModelProperty(notes = "Email del usuario")
  private String mail;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="user_role",
  joinColumns = {@JoinColumn(name="user_id")},
  inverseJoinColumns = {@JoinColumn(name = "role_id")}
  )
  private Set<Role> roles;
  
  /**
   * Costructor por omisión
   */
  public User() {}

  /**
   * Constructor para inicializar los atributos name y email
   * @param name
   * @param password
   * @param email
   */
  public User(String name, String password, String email) {
    this.name = name;
    this.password = password;
    this.mail = email;
  }

  /**
   * Constructor para inicializar los atributos name, email y roles
   * @param name
   * @param password
   * @param email
   * @param roles
   */
  public User(String name, String password, String email, Set<Role> roles) {
    this.name = name;
    this.password = password;
    this.mail = email;
    this.roles = roles;
  }
  
  /**
   * Constructor para inicializar los atributos id, name, password, email y roles
   * @param id
   * @param name
   * @param password
   * @param email
   * @param roles
   */
  public User(Long id, String name, String password, String email, Set<Role> roles) {
    this.user_id = id;
    this.name = name;
    this.password = password;
    this.mail = email;
    this.roles = roles;
  }
  
  /**
   * Método para obtener el id
   * @return Long retorna el id
   */
  public Long getUser_id() {
    return this.user_id;
  }

  /**
   * Método para guardar id
   * @param user_id
   */
  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  /**
   * Método obtener nombre
   * @return String retorna el nombre
   */
  public String getName() {
    return this.name;
  }

  /**
   * Método para guardar el nombre
   * @param name
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * Método obtener password
   * @return String retorna el password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Método para guardar el password
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Método obtener mail
   * @return String retorna el mail
   */
  public String getMail() {
	  return this.mail;
  }

  /**
   * Método para guardar el mail
   * @param mail
   */
  public void setMail(String mail){
    this.mail = mail;
  }

  /**
   * Método obtener roles
   * @return Set<Role> retorna los roles
   */
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * Método para guardar los roles
   * @param roles
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}