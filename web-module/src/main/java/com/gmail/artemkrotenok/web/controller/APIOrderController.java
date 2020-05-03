package com.gmail.artemkrotenok.web.controller;

import java.util.List;

import com.gmail.artemkrotenok.service.OrderService;
import com.gmail.artemkrotenok.service.model.OrderDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.artemkrotenok.web.constant.ControllerConstant.FIRST_PAGE_FOR_PAGINATION;

@RestController
@RequestMapping("/api/orders")

public class APIOrderController {

    private final OrderService orderService;

    public APIOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getItemsByPage(
            @RequestParam(name = "page", required = false) Integer page) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        return orderService.getOrdersByPageSorted(page);
    }

    @GetMapping(value = "/{id}")
    public OrderDTO getItemById(@PathVariable(name = "id") Long id) {
        return orderService.findById(id);
    }

}
