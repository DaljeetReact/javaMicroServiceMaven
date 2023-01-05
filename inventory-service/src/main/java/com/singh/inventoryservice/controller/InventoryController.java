package com.singh.inventoryservice.controller;

import com.singh.inventoryservice.dto.InventoryResponse;
import com.singh.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> IsInStock(@RequestParam List<String> skuCode){
        return inventoryService.IsInStock(skuCode);
    }
}
