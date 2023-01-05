package com.singh.store.service;


import com.singh.store.dto.ProductRequest;
import com.singh.store.model.Product;
import com.singh.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;

    public void createProducts(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product has been saved" + product.getId());
    }

    public List<Product> GetAllProducts() {
       return productRepository.findAll();
    }
}
