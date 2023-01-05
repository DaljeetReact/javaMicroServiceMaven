package com.singh.inventoryservice.repository;

import com.singh.inventoryservice.dto.InventoryResponse;
import com.singh.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory> findByskuCodeIn(List<String> sku);
}
