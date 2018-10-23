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
  private Set<Role> roles;

  /**
   * Costructor por omisión
   */
  public User() {}

  /**
   * Constructor para inicializar atributo id
   * @param id
   */
  public User(Long id){
    this.user_id = id;
  }

  /**
   * Constructor para inicializar los atributos name, mail y roles
   * @param name
   * @param email
   * @param roles
   */
  public User(String name, String email, Set<Role> roles) {
    this.name = name;
    this.mail = email;
    this.roles = roles;
  }

  /**
   * Constructor para inicializar los atributos id, name, email y roles
   * @param id
   * @param name
   * @param email
   * @param roles
   */
  public User(Long id, String name, String email, Set<Role> roles) {
    this.user_id = id;
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