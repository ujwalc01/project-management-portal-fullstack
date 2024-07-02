package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.entities.Project;
import com.project.entities.Users;

public interface ProjectRepository extends JpaRepository<Project, Long>{
	
//	List<Project> findByOwner(Users user);
	
	List<Project> findByNameContainingAndTeamContains(String partialName, Users user);
	
//	@Query("SELECT p From Project p join p.team t where t=:user")
//	List<Project> findProjectByTeam(@Param("user") Users user);

	List<Project> findByTeamContainingOrOwner(Users user, Users owner);
}
