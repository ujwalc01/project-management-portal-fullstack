package com.project.services;

import com.project.constants.PlanType;
import com.project.entities.Subscription;
import com.project.entities.Users;

public interface SubscriptionService {

	Subscription createSubscription(Users user);
	
	Subscription getUsersSubscription(Long userId) throws Exception;
	
	Subscription upgradeSubscription(Long userId, PlanType planType);
	
	boolean isValid(Subscription subscription);
	
}
