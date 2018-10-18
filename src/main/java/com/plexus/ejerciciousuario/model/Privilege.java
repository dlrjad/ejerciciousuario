package com.plexus.ejerciciousuario.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Privilege {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;

  @ManyToMany(mappedBy="roles")
  private List<Role> users = new ArrayList<>();

  public Privilege() {}

  public Privilege(String name) {
    this.name = name;
  }

}