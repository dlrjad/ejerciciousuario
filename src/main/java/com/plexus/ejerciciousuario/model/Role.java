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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Clase entidad role
 * 
 * @author dlrjad
 */

@Entity
@Table(name = "role")
@ApiModel(value="Identificador", description="Clase entidad role")
public class Role implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id", unique=true)
  @ApiModelProperty(notes = "The database generated user ID")
  private Long role_id;

  @Column(name = "name_role", length = 50)
  @ApiModelProperty(notes = "Nombre del rol")
  private String name;

  @ManyToMany(mappedBy="roles")
  @JsonIgnore
  private Set<User> users;

  @ManyToMany(cascade = {
    CascadeType.ALL
  })
  @JoinTable(name="role_privilege",
    joinColumns = {@JoinColumn(name="role_id")},
    inverseJoinColumns = {@JoinColumn(name = "privilege_id")}
  )
  private Set<Privilege> privileges;
  
  /**
   * Constructor por omisión
   */
  public Role() {}

  /**
   * Constructor para inicializar atributo id
   * @param id
   */
  public Role(Long id){
    this.role_id = id;
  }

  /**
   * Constructor para inicializar los atributos id y name 
   * @param id
   * @param name
   */
  public Role(Long id, String name) {
    this.role_id = id;
    this.name = name;
  }

  /**
   * Constructor para inicializar los atributos id, name, users y privileges
   * @param id
   * @param name
   * @param users
   * @param privileges
   */
  public Role(Long id, String name, Set<User> users, Set<Privilege> privileges) {
    this.role_id = id;
    this.name = name;
    this.users = users;
    this.privileges = privileges;
  }

  /**
   * Constructor para inicializar atributo nombre
   * @param name
   */
  public Role(String name) {
    this.name = name;
  }

  /**
   * Método obtener nombre
   * @return String retorna el nombre
   */
  public String getName() {
    return this.name;
  }

  /**
   * Método obtener usuarios
   * @return Set<User> retorna los usuarios
   */
  public Set<User> getUsers() {
    return users;
  }

  /**
   * Método pra guardar los usuarios
   * @param users
   */
  public void setUser(Set<User> users) {
    this.users = users;
  }

  /**
   * Método obtener privilegios
   * @return Set<Privilege> retorna los privilegios
   */
  public Set<Privilege> getPrivileges() {
    return privileges;
  }

  /**
   * Método para guardar los privilegios
   * @param privileges
   */
  public void setPrivileges(Set<Privilege> privileges) {
    this.privileges = privileges;
  }

}