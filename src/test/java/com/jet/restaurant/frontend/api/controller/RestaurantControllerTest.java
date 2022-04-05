package com.jet.restaurant.frontend.api.controller;

import com.jet.restaurant.frontend.api.contract.RestaurantInsertRequest;
import com.jet.restaurant.frontend.api.domain.Status;
import com.jet.restaurant.frontend.api.dto.RestaurantUpdateDTO;
import com.jet.restaurant.frontend.api.exception.ApplicationException;
import com.jet.restaurant.frontend.api.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestaurantControllerTest {

    @Autowired
    private RestaurantController controller;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void testFindAll() {
        Status status = Status.OPEN;
        when(restaurantService.fetchRestaurants(status)).thenReturn(new ArrayList<>());
        assertNotNull(controller.findAll(status));
    }

    @Test
    void testInsert() {
        RestaurantInsertRequest request = new RestaurantInsertRequest();
        doNothing().when(restaurantService).save(request.getRestaurants());
        assertNotNull(controller.insert(request));
    }

    @Test
    void testUpdate() throws ApplicationException {
        Long id = 1L;
        RestaurantUpdateDTO request = new RestaurantUpdateDTO();
        doNothing().when(restaurantService).update(id, request);
        assertNotNull(controller.update(id, request));
    }

    @Test
    void testUpdateError() throws ApplicationException {
        Long id = 2L;
        RestaurantUpdateDTO request = new RestaurantUpdateDTO();
        doThrow(new ApplicationException("MOCK")).when(restaurantService).update(id, request);
        assertThrows(ApplicationException.class, () -> controller.update(id, request));
    }
}
