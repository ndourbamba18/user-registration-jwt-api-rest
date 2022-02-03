package com.ndourcodeur.microservices.registrationuserservice.repository;

import java.util.Optional;

import com.ndourcodeur.microservices.registrationuserservice.models.ERole;
import com.ndourcodeur.microservices.registrationuserservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
