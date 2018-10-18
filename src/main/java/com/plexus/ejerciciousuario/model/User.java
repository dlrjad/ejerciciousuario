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

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;

  @Column
  private String email;

  @ManyToMany
  @JoinTable(name="USER_ROLE",
    joinColumns = @JoinColumn(name="USER_ID"),
    inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
  )
  private List<Role> roles = new ArrayList<>();

  public User() {}

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

}