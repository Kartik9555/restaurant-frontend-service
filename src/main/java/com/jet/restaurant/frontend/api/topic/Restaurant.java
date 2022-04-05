package com.jet.restaurant.frontend.api.topic;

import com.jet.restaurant.frontend.api.domain.Status;
import lombok.Data;

@Data
public class Restaurant {
    private Long id;
    private Status status;
}
