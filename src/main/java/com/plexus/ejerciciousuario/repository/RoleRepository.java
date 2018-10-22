package com.plexus.ejerciciousuario.repository;

import com.plexus.ejerciciousuario.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}