package com.jet.restaurant.frontend.api.dto;

import com.jet.restaurant.frontend.api.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUpdateDTO {
    @NotNull
    private String name;

    @NotNull
    private Status status;
}
