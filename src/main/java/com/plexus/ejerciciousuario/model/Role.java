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
  @ApiModelProperty(notes = "The database generated role ID")
  private Long role_id;

  @Column(name = "name_role", length = 50)
  @ApiModelProperty(notes = "Nombre del rol")
  private String name;

  @ManyToMany(mappedBy="roles")
  @JsonIgnore
  private Set<User> users;
  
  @ManyToMany(fetch = FetchType.EAGER)
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
   * Constructor para inicializar atributo nombre
   * @param name
   */
  public Role(String name) {
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
   * Método para obtener el id
   * @return Long retorna el id
   */
  public Long getRole_id() {
    return this.role_id;
  }

  /**
   * Método para guardar la id
   * @param role_id
   */
  public void setRole_id(Long role_id) {
    this.role_id = role_id;
  }
  
  /**
   * Método obtener nombre
   * @return String retorna el nombre
   */
  public String getName() {
    return this.name;
  }

  /**
   * Método establecer nombre
   * @param name <String> nombre
   */
  public void setName(String name) {
    this.name = name;
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