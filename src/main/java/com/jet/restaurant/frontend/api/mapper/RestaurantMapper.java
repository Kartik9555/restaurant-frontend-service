package com.jet.restaurant.frontend.api.mapper;

import com.jet.restaurant.frontend.api.dto.RestaurantDTO;
import com.jet.restaurant.frontend.api.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    List<Restaurant> mapToEntity(List<RestaurantDTO> restaurants);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    @Mapping(target = "createdBy", constant = "back-office")
    @Mapping(target = "updatedBy", constant = "back-office")
    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    Restaurant mapEntity(RestaurantDTO restaurant);

    List<RestaurantDTO> mapToList(List<Restaurant> restaurants);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "id", source = "id")
    RestaurantDTO entityToDto(Restaurant restaurant);
}
