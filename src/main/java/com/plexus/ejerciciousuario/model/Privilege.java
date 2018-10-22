package com.plexus.ejerciciousuario.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase entidad privilege
 * 
 * @author dlrjad
 */

@Entity
@Table(name = "privilege")
public class Privilege implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "privilege_id", unique=true)
  @ApiModelProperty(notes = "The database generated user ID")
  private Long id;

  @Column(name = "name_privilege", length = 50)
  @ApiModelProperty(notes = "Nombre del privilegio")
  private String name;

  @ManyToMany(mappedBy="privileges")
  @JsonIgnore
  private Set<Role> roles;

  /**
   * Constructor por omisión
   */
  public Privilege() {}

  /**
   * Constructor para inicializar atributo id
   * @param id
   */
  public Privilege(Long id){
    this.id = id;
  }

  /**
   * Constructor para inicializar los atributos id y name
   * @param id
   * @param name
   */
  public Privilege(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Constructor para inicializar los atributos id, name y roles
   * @param id
   * @param name
   * @param roles
   */
  public Privilege(Long id, String name, Set<Role> roles) {
    this.id = id;
    this.name = name;
    this.roles = roles;
  }

  /**
   * Constructor para inicializar atributo nombre
   * @param name
   */
  public Privilege(String name) {
    this.name = name;
  }

  /**
   * Método obtener nombre
   * @return
   */
  public String getName() {
    return this.name;
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
  public void setPrivileges(Set<Role> roles) {
    this.roles = roles;
  }

}