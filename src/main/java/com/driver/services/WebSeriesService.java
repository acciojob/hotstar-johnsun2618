package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.*;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository


        try {
            SimpleJpaRepository<User, Integer> userRepository = null;
            User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
            int userAge = user.getAge();
            SubscriptionType subscriptionType = user.getSubscription().getSubscriptionType();
            return (int) webSeriesRepository.findAll()
                    .stream()
                    .filter(webSeries -> webSeries.getAgeLimit() <= userAge && webSeries.getProductionHouse().getWebSeriesList().contains(subscriptionType))
                    .count();
        } catch(NoSuchElementException e) {
            return null;
        }

//        return null;
    }

}
