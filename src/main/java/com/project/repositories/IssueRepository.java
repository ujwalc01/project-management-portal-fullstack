package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {

	public List<Issue> findByProjectId(Long id);
	
}
