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

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Subscription subscription = user.getSubscription();

        if(subscription == null) {
            return 0;
        }

        List<WebSeries> webSeriesList = webSeriesRepository.findAllByAgeLimitLessThanEqualAndSubscriptionTypeLessThanEqual(
                user.getAge(), subscription.getSubscriptionType().getSubscriptionType());

        return webSeriesList.size();
    }

    public User getUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<WebSeries> getAllWebSeries() {
        return webSeriesRepository.findAll();
    }

    public List<SubscriptionType> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }


}
