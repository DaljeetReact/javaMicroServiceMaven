package com.singh.orderservice.service;

import com.singh.orderservice.dto.InventoryResponse;
import com.singh.orderservice.dto.OrderLineItemsDto;
import com.singh.orderservice.dto.OrderRequest;
import com.singh.orderservice.model.Order;
import com.singh.orderservice.model.OrderLineItems;
import com.singh.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

     @Autowired
     private WebClient webClient;

     @Value("${uri.Invetory}")
    private String inventoryURL;


    public  void  PlaceOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        //orderLineItems = [orderLineItemsDto,orderLineItemsDto];orderLineItemsDto Is a object
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                        .stream()
                        .map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).toList();
        order.setOrderLineItems(orderLineItems);


        //skuCodes = [sku1,sku2,sku3];
        List<String> skuCodes = orderRequest.getOrderLineItemsDtoList().stream()
                .map(orderLineItemsDto -> orderLineItemsDto.getSkuCode()).toList();

        ///_________________________________________________________________________
        // CAll the inventory Service to check is product is available for order or not
        InventoryResponse[] InventoryResponseArray = webClient.get()
             .uri(inventoryURL, uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
             .retrieve()
             .bodyToMono(InventoryResponse[].class)
             .block();
        ///_________________________________________________________________________

       Boolean AllProductsInStock = Arrays.stream(InventoryResponseArray).allMatch(InventoryResponse::getInStock);

     if(AllProductsInStock && InventoryResponseArray.length > 0){
         orderRepository.save(order); // place order for list of products
     }else{
         // this will throw 500 error
        throw new IllegalArgumentException("the product is out of stock");
     }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return  orderLineItems;
    }
}
