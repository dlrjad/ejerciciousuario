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

@Entity
@Table(name = "privilege")
public class Privilege {

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

  public Privilege() {}

  public Privilege(Long id){
    this.id = id;
  }

  public Privilege(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Privilege(Long id, String name, Set<Role> roles) {
    this.id = id;
    this.name = name;
    this.roles = roles;
  }

  public Privilege(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setPrivileges(Set<Role> roles) {
    this.roles = roles;
  }

}