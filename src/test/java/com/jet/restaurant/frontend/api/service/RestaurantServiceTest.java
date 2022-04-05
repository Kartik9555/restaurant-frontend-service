package com.jet.restaurant.frontend.api.service;

import com.jet.restaurant.frontend.api.dao.RestaurantRepository;
import com.jet.restaurant.frontend.api.domain.Status;
import com.jet.restaurant.frontend.api.dto.RestaurantDTO;
import com.jet.restaurant.frontend.api.dto.RestaurantUpdateDTO;
import com.jet.restaurant.frontend.api.entity.Restaurant;
import com.jet.restaurant.frontend.api.exception.ApplicationException;
import com.jet.restaurant.frontend.api.mapper.RestaurantMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Test
    void testFetchRestaurantsStatusAll(){
        Status status = Status.ALL;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        List<RestaurantDTO> results = service.fetchRestaurants(status);
        assertEquals(1L, results.get(0).getId());
    }

    @Test
    void testFetchRestaurantsStatusOpen(){
        Status status = Status.OPEN;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setStatus(Status.OPEN);
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        when(restaurantRepository.findAllByStatus(status)).thenReturn(restaurants);
        List<RestaurantDTO> results = service.fetchRestaurants(status);
        assertEquals(1L, results.get(0).getId());
    }

    @Test
    void testFetchRestaurantsStatusClosed(){
        Status status = Status.CLOSED;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1l);
        restaurant.setStatus(Status.CLOSED);
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        when(restaurantRepository.findAllByStatus(status)).thenReturn(restaurants);
        List<RestaurantDTO> results = service.fetchRestaurants(status);
        assertEquals(1L, results.get(0).getId());
    }

    @Test
    void testSave(){
        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setName("Test");
        restaurant.setStatus(Status.CLOSED);
        List<RestaurantDTO> restaurants = Collections.singletonList(restaurant);
        when(restaurantRepository.saveAll(anyCollection())).thenReturn(new ArrayList<>());
        service.save(restaurants);
    }

    @Test
    void testUpdateNotFoundError() {
        Long id = 1L;
        RestaurantUpdateDTO restaurant = new RestaurantUpdateDTO();
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () -> service.update(id, restaurant));
    }

    @Test
    void testUpdateOk() throws ApplicationException {
        Long id = 1L;
        RestaurantUpdateDTO restaurant = new RestaurantUpdateDTO();
        restaurant.setStatus(Status.OPEN);
        restaurant.setName("MOCK");
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(new Restaurant()));
        service.update(id, restaurant);
    }

    @Test
    void testUpdateStatusNotFoundError() {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ApplicationException.class, () -> service.updateStatus(restaurant));
    }

    @Test
    void testUpdateStatusOk() throws ApplicationException {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setStatus(Status.OPEN);
        restaurant.setName("MOCK");
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(new Restaurant()));
        service.updateStatus(restaurant);
    }
}
