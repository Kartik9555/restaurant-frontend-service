package com.jet.restaurant.frontend.api.dao;

import com.jet.restaurant.frontend.api.domain.Status;
import com.jet.restaurant.frontend.api.entity.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    List<Restaurant> findAllByStatus(Status status);

    @Query("select u from Restaurant u ")
    List<Restaurant> findAll();
}

