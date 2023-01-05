package com.singh.inventoryservice.service;

import com.singh.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public Boolean IsInStock(String sku){
        return  inventoryRepository.findByskuCode(sku).isPresent();
    }
}
