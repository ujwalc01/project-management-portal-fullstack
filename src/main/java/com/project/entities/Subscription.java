package com.project.entities;

import java.time.LocalDate;

import com.project.constants.PlanType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDate subscriptionStartDate;
	
	private LocalDate subscriptionEndDate;
	
	private PlanType planType;
	
	private boolean isValid;
	
	@OneToOne
	private Users user;
	
}
