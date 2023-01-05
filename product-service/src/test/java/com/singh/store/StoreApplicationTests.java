package com.singh.store;

import com.singh.store.model.Product;
import com.singh.store.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
class StoreApplicationTests {

	@Container
	static  MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));


	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		mongoDBContainer.start();
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
		private ObjectMapper objectMapper;

	@AfterEach
	void cleanUp() {
		this.productRepository.deleteAll();
	}

	@Test
	void ShouldCreateProduct() throws Exception {
		Product product = getProductRequet();
		String ProductString =  objectMapper.writeValueAsString(product);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ProductString)
		).andExpect(status().isCreated());
	}

	private Product getProductRequet() {
		return Product.builder()
				.name("iphone 14")
				.description("this is description for iphone 14")
				.price(BigDecimal.valueOf(1300))
				.build();
	}

}
