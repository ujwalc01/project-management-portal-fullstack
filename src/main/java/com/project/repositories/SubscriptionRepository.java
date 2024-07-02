package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

	Subscription findByUserId(Long userId);
}
