package com.plexus.ejerciciousuario.repository;

import com.plexus.ejerciciousuario.model.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{

	Privilege findOne(Long id);
	
}