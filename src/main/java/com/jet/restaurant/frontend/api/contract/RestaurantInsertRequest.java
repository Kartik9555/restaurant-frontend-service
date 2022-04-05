package com.jet.restaurant.frontend.api.contract;

import com.jet.restaurant.frontend.api.dto.RestaurantDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInsertRequest {
    private List<RestaurantDTO> restaurants;
}
