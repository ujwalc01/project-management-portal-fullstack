package com.project.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.project.entities.Project;
import com.project.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {
	
	private Long id;
	
	private String title;
	
	private String description;
	
	private String status;
	
	private Long projectID;
	
	private String priority;
	
	private LocalDate dueDate;
	
	private List<String> tags = new ArrayList<>();
	
	private Project project;
	
	private Users assignee;
	

}
