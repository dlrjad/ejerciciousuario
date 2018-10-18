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
@Table(name="users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

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

  public User(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return this.name;
  }

  public String getMail() {
	  return this.email;
  }

}