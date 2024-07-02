package com.project.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private int projectSize;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
	private List<Issue> assignedIssue = new ArrayList<>();
}
