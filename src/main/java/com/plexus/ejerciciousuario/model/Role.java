package com.plexus.ejerciciousuario.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id")
  @ApiModelProperty(notes = "The database generated user ID")
  private Long id;

  @Column(name = "name_role", length = 50)
  @ApiModelProperty(notes = "Nombre del rol")
  private String name;

  @ManyToMany(mappedBy="roles")
  private List<User> users = new ArrayList<>();

  @ManyToMany
  @JoinTable(name="role_privilege",
    joinColumns = @JoinColumn(name="role_id"),
    inverseJoinColumns = @JoinColumn(name = "privilege_id")
  )
  private List<Privilege> privileges = new ArrayList<>();
  
  public Role() {}

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Role(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}