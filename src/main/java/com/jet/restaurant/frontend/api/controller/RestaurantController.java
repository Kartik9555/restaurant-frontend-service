package com.jet.restaurant.frontend.api.controller;

import com.jet.restaurant.frontend.api.contract.RestaurantInsertRequest;
import com.jet.restaurant.frontend.api.domain.Status;
import com.jet.restaurant.frontend.api.dto.RestaurantDTO;
import com.jet.restaurant.frontend.api.dto.RestaurantUpdateDTO;
import com.jet.restaurant.frontend.api.exception.ApplicationException;
import com.jet.restaurant.frontend.api.service.RestaurantService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@Slf4j
@RequiredArgsConstructor
@Api(value = "Restaurant", tags = {"Restaurant"})
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantDTO> findAll(@RequestParam(name = "status", defaultValue = "ALL") Status status) {
        return restaurantService.fetchRestaurants(status);
    }

    @PostMapping
    public ResponseEntity<String> insert(@RequestBody RestaurantInsertRequest request) {
        restaurantService.save(request.getRestaurants());
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,  @RequestBody RestaurantUpdateDTO restaurant) throws ApplicationException {
        restaurantService.update(id, restaurant);
        return ResponseEntity.ok("Updated for: " + id);
    }
}
