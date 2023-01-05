package com.singh.inventoryservice.service;

import com.singh.inventoryservice.dto.InventoryResponse;
import com.singh.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> IsInStock(List<String> sku){
        return  inventoryRepository.findByskuCodeIn(sku).stream()
                .map(Inventory ->
                      InventoryResponse.builder()
                              .skuCode(Inventory.getSkuCode())
                              .inStock(Inventory.getQuantity() > 0)
                              .build()
                        ).toList();
    }
}
