package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	
	Users findByEmail(String email);
}
 