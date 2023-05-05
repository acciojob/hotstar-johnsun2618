package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay

        // Create a new Subscription object from the DTO
        Subscription subscription = new Subscription();
        subscription.setStartSubscriptionDate(new Date());
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setUser(userRepository.findById(subscriptionEntryDto.getUserId()).orElse(null));

        // Save the subscription object into the database

//        subscriptionRepository.save(subscription);

        // Return the total amount that the user has to pay
        return (Integer) subscription.getSubscriptionType().getPrice();

//        return null;

    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        // Find the user by ID and get their current subscription
        User user = userRepository.findById(userId).orElse(null);
        Subscription currentSubscription = user.getSubscription();

        // Check if the user is already at the best subscription level
        if (currentSubscription.getSubscriptionType() == SubscriptionType.ELITE) {
            throw new Exception("Already the best subscription");
        }

        // Get the next highest subscription level
        SubscriptionType newSubscriptionType = currentSubscription.getSubscriptionType();

        // Calculate the price difference between the current and new subscription
        int priceDifference = newSubscriptionType.getPrice() - currentSubscription.;

        // Create a new subscription object with the new subscription type
        Subscription newSubscription = new Subscription();
        newSubscription.setStartSubscriptionDate(new Date());
        newSubscription.setSubscriptionType(newSubscriptionType);
        newSubscription.setUser(user);

        // Update the user's subscription in the database
        user.setSubscription(newSubscription);
        userRepository.save(user);

        // Return the price difference
        return priceDifference;

//        return null;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        // Find all subscriptions in the database
        List<SubscriptionType> subscriptions = subscriptionRepository.findAll();

        // Calculate the total revenue from all subscriptions
        int totalRevenue = subscriptions.stream()
                .mapToInt(subscription -> (int) subscription.getSubscriptionType().getPrice())
                .sum();

        // Return the total revenue
        return totalRevenue;

//        return null;

    }

}
