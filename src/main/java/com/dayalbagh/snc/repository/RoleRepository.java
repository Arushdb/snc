package com.dayalbagh.snc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayalbagh.snc.model.ERole;
import com.dayalbagh.snc.model.Role;



public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(ERole name);

}
