package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
        User savedUser = userRepository.save(user);
        return savedUser.getId();

    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository


        // Find the user based on the userId
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        // Find the subscription for the user
        Subscription subscription = subscriptionRepository.
                findById(user.getSubscription()).orElseThrow(() ->
                        new NoSuchElementException("Subscription not found"));

        // Get the age limit of the user
        Integer ageLimit = user.getAge();

        // Find all the web series that are viewable for the given age limit and subscription type
        List<WebSeries> viewableWebSeries = webSeriesRepository.
                findByAgeLimitLessThanEqualAndSubscriptionTypeLessThanEqual
                        (ageLimit, subscription.getSubscriptionType());

        // Return the count of viewable web series
        return viewableWebSeries.size();

//        return null;

    }


}
