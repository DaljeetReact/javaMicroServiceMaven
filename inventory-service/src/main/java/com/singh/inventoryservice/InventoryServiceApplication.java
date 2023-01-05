package com.singh.inventoryservice;

import com.singh.inventoryservice.model.Inventory;
import com.singh.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadRawData(InventoryRepository inventoryRepository){
		List<Inventory> rows = inventoryRepository.findAll();
		if(!rows.isEmpty()){
			return null;
		}
		List<Inventory> data = List.of(
				new Inventory("iphone_11",100),
				new Inventory("iphone_12",10),
				new Inventory("iphone_13",60),
				new Inventory("iphone_14",0)
				);
		inventoryRepository.saveAll(data);
		return null;
	}

}
