package com.plexus.ejerciciousuario.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "privilege")
public class Privilege {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "privilege_id")
  @ApiModelProperty(notes = "The database generated user ID")
  private Long id;

  @Column(name = "name_privilege")
  @ApiModelProperty(notes = "Nombre del privilegio")
  private String name;

  @ManyToMany(mappedBy="privileges")
  private List<Role> roles = new ArrayList<>();

  public Privilege() {}

  public Privilege(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Privilege(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

}