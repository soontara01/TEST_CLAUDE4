package com.example.demo.controller;

import com.example.demo.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductControllerTest {

    @Test
    void getProducts_returnsSeededSampleProduct() {
        ProductController controller = new ProductController();

        List<Product> products = controller.getProducts();

        assertThat(products).hasSize(1);

        Product sample = products.get(0);
        assertThat(sample.getId()).isNotNull();
        assertThat(sample.getCode()).isEqualTo("P-0001");
        assertThat(sample.getName()).isEqualTo("Sample Product");
        assertThat(sample.getPrice()).isEqualTo(19.99);
    }

    @Test
    void createProduct_addsNewProductAndReturnsCreated() {
        ProductController controller = new ProductController();
        Product newProduct = new Product(null, "P-0002", "New Product", 29.99);

        ResponseEntity<Product> response = controller.createProduct(newProduct);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Product created = response.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getCode()).isEqualTo("P-0002");
        assertThat(created.getName()).isEqualTo("New Product");
        assertThat(created.getPrice()).isEqualTo(29.99);

        List<Product> products = controller.getProducts();
        assertThat(products).hasSize(2);
        assertThat(products)
                .extracting(Product::getCode)
                .containsExactlyInAnyOrder("P-0001", "P-0002");
    }
}
