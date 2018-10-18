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

@Entity
@Table(name="role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column
  private String name;

  @ManyToMany(mappedBy="roles")
  private List<User> users = new ArrayList<>();

  @ManyToMany
  @JoinTable(name="ROLE_PRIVILEGE",
    joinColumns = @JoinColumn(name="ROLE_ID"),
    inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID")
  )
  private List<Role> roles = new ArrayList<>();
  
  public Role() {}

  public Role(String name) {
    this.name = name;
  }

}