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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Clase entidad privilege
 * 
 * @author dlrjad
 */

@Entity
@Table(name = "privilege")
@ApiModel(value="Identificador", description="Clase entidad privilege")
public class Privilege implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "privilege_id", unique=true)
  @ApiModelProperty(notes = "The database generated privilege ID")
  private Long privilege_id;

  
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
   * Constructor para inicializar atributo nombre
   * @param name
   */
  public Privilege(String name) {
    this.name = name;
  }

  /**
   * Constructor para inicializar los atributos id, name y roles
   * @param id
   * @param name
   * @param roles
   */
  public Privilege(Long id, String name, Set<Role> roles) {
    this.privilege_id = id;
    this.name = name;
    this.roles = roles;
  }

  /**
   * Método para obtener el id
   * @return Long retorna el id
   */
  public Long getPrivilege_id() {
    return this.privilege_id;
  }

  /**
   * Método para guardar id
   * @param privilege_id
   */
  public void setPrivilege_id(Long privilege_id) {
    this.privilege_id = privilege_id;
  }

  /**
   * Método obtener nombre
   * @return
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