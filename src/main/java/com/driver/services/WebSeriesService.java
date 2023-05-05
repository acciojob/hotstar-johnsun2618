package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto) throws Exception {

        // Check if the web series already exists in the database
        if (webSeriesRepository.existsBySeriesName(webSeriesEntryDto.getSeriesName())) {
            throw new Exception("Web series already exists");
        }

        // Find the production house by the given production house ID
        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId())
                .orElseThrow(() -> new NoSuchElementException("Production house not found"));

        // Create a new WebSeries object and set its properties
        WebSeries webSeries = new WebSeries();
        webSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
        webSeries.setAgeLimit(webSeriesEntryDto.getAgeLimit());
        webSeries.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());
        webSeries.setProductionHouse(productionHouse);

        // Add the new web series to the database
        webSeries = webSeriesRepository.save(webSeries);

        // Update the production house's rating
        List<WebSeries> webSeriesList = productionHouse.getWebSeriesList();
        webSeriesList.add(webSeries);
        productionHouse.setWebSeriesList(webSeriesList);
        double totalRating = webSeriesList.stream().mapToDouble(WebSeries::getRating).sum();
        double newRating = totalRating / webSeriesList.size();
        productionHouse.setRating(newRating);
        productionHouseRepository.save(productionHouse);

        return webSeries.getId();

//        return null;

    }
}
