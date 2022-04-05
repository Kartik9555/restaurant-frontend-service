package com.jet.restaurant.frontend.api.listener;

import com.google.gson.Gson;
import com.jet.restaurant.frontend.api.entity.Restaurant;
import com.jet.restaurant.frontend.api.exception.ApplicationException;
import com.jet.restaurant.frontend.api.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestaurantStatusInboundEvent {

    private final RestaurantService service;

    @KafkaListener(id = "${kafka.consumers.consumerGroup}", topics = {"${kafka.topic.status-update:UPDATE_STATUS}"})
    public void receive(@Payload String detail) throws ApplicationException {
        log.info("Handling index events. Received {} event(s)", detail);
        Restaurant restaurant = new Gson().fromJson(detail, Restaurant.class);
        service.updateStatus(restaurant);
    }

}
