package com.singh.orderservice.controller;

import com.singh.orderservice.dto.OrderRequest;
import com.singh.orderservice.model.Order;
import com.singh.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  String PlaceOrder(@RequestBody OrderRequest orderRequest){
         orderService.PlaceOrder(orderRequest);
        return  "Order is placed successfully";
    }

    @GetMapping
    public List<Order>  GetAllOrderLists(){
      return   orderService.GetAllOrders();
    }
}
