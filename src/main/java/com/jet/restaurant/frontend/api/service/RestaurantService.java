package com.jet.restaurant.frontend.api.service;

import com.jet.restaurant.frontend.api.dao.RestaurantRepository;
import com.jet.restaurant.frontend.api.domain.Status;
import com.jet.restaurant.frontend.api.dto.RestaurantDTO;
import com.jet.restaurant.frontend.api.dto.RestaurantUpdateDTO;
import com.jet.restaurant.frontend.api.entity.Restaurant;
import com.jet.restaurant.frontend.api.exception.ApplicationException;
import com.jet.restaurant.frontend.api.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public List<RestaurantDTO> fetchRestaurants(Status status) {
        List<Restaurant> restaurants = Status.ALL.equals(status) ? restaurantRepository.findAll() : restaurantRepository.findAllByStatus(status);
        return restaurantMapper.mapToList(restaurants);
    }

    public void save(List<RestaurantDTO> restaurants) {
        restaurantRepository.saveAll(restaurantMapper.mapToEntity(restaurants));
    }

    public void update(Long id, RestaurantUpdateDTO restaurant) throws ApplicationException{
        Restaurant entity = findRestaurant(id);
        entity.setStatus(restaurant.getStatus());
        restaurant.setName(StringUtils.hasLength(restaurant.getName()) ? restaurant.getName() : entity.getName());
        save(entity);
    }
    
    public void updateStatus(Restaurant restaurant) throws ApplicationException {
        Restaurant entity = findRestaurant(restaurant.getId());
        entity.setStatus(restaurant.getStatus());
        save(entity);
    }

    public void save(Restaurant restaurant){
        restaurant.setUpdatedAt(new Date());
        restaurantRepository.save(restaurant);
    }
    
    private Restaurant findRestaurant(Long id) throws ApplicationException{
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Could not find restaurant with id: " + id));
    }
}
