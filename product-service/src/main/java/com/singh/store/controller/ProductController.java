package com.singh.store.controller;

import com.singh.store.dto.ProductRequest;
import com.singh.store.model.Product;
import com.singh.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity createProducts(@RequestBody ProductRequest productRequest){
            productService.createProducts(productRequest);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> GetAllProducts(){
      return   ResponseEntity.ok(productService.GetAllProducts());
    }
}
