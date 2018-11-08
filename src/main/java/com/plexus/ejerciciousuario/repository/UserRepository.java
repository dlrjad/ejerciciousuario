package com.plexus.ejerciciousuario.repository;

import com.plexus.ejerciciousuario.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByNameAndPassword(String name, String password);
  User findByName(String name);

}