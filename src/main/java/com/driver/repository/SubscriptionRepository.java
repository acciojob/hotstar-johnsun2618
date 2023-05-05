package com.driver.repository;

import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionType, Integer> {
    SubscriptionType findByName(String name);
}

