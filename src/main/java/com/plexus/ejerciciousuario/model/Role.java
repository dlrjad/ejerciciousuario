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
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id", unique=true)
  @ApiModelProperty(notes = "The database generated user ID")
  private Long id;

  @Column(name = "name_role", length = 50)
  @ApiModelProperty(notes = "Nombre del rol")
  private String name;

  @ManyToMany(mappedBy="roles")
  //@JsonIgnore
  private Set<User> users;

  @ManyToMany(cascade = {
    CascadeType.ALL
  })
  @JoinTable(name="role_privilege",
    joinColumns = {@JoinColumn(name="role_id")},
    inverseJoinColumns = {@JoinColumn(name = "privilege_id")}
  )
  @JsonBackReference
  private Set<Privilege> privileges;
  
  public Role() {}

  public Role(Long id){
    this.id = id;
  }

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Role(Long id, String name, Set<User> users, Set<Privilege> privileges) {
    this.id = id;
    this.name = name;
    this.users = users;
    this.privileges = privileges;
  }

  public Role(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUser(Set<User> users) {
    this.users = users;
  }

  public Set<Privilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Set<Privilege> privileges) {
    this.privileges = privileges;
  }

}