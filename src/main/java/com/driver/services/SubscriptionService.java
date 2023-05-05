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

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setId(subscriptionEntryDto.getUserId());
        subscription = subscriptionRepository.save(subscription);
        return subscription.getTotalAmountPaid();

//        return null;

    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        Subscription currentSubscription = subscriptionRepository.findByUserId(userId);
        SubscriptionType currentType = currentSubscription.getSubscriptionType();
        if (currentType == SubscriptionType.ELITE) {
            throw new Exception("Already the best subscription");
        }
        SubscriptionType nextType = currentType.next();
        currentSubscription.setSubscriptionType(nextType);
        subscriptionRepository.save(currentSubscription);
        return nextType.getPrice() - currentType.getPrice();

//        return null;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        List<Subscription> subscriptions = subscriptionRepository.findAll();
        int totalRevenue = 0;
        for (Subscription subscription : subscriptions) {
            totalRevenue += subscription.getTotalAmountPaid();
        }
        return totalRevenue;

//        return null;

    }

}
