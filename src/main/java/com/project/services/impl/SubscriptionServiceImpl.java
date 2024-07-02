package com.project.services.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.constants.PlanType;
import com.project.entities.Subscription;
import com.project.entities.Users;
import com.project.repositories.SubscriptionRepository;
import com.project.services.SubscriptionService;
import com.project.services.UserService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepo;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Subscription createSubscription(Users user){
		Subscription subscription = new Subscription();
		
		subscription.setUser(user);
		subscription.setSubscriptionStartDate(LocalDate.now());
		subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
		subscription.setValid(true);
		subscription.setPlanType(PlanType.FREE);
		
		return subscriptionRepo.save(subscription);
	}

	@Override
	public Subscription getUsersSubscription(Long userId) throws Exception {
		
		Subscription subscription = subscriptionRepo.findByUserId(userId);
		
		if(!isValid(subscription)) {
			subscription.setPlanType(PlanType.FREE);
			subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
			subscription.setSubscriptionStartDate(LocalDate.now());
		}
		
		return subscriptionRepo.save(subscription);
	}

	@Override
	public Subscription upgradeSubscription(Long userId, PlanType planType) {
		Subscription subscription = subscriptionRepo.findByUserId(userId);
		
		subscription.setPlanType(planType);
		subscription.setSubscriptionStartDate(LocalDate.now());
		
		if(planType.equals(planType.ANNUALLY)) {
			subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
		}
		else {
			subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
		}
		
		return subscriptionRepo.save(subscription);
	}

	@Override
	public boolean isValid(Subscription subscription) {
		if(subscription.getPlanType().equals(PlanType.FREE)) {
			return true;
		}
		LocalDate endDate = subscription.getSubscriptionEndDate();
		LocalDate currentDate = LocalDate.now();
	
		return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
	}

}
