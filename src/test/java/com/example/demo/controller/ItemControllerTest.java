package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ItemControllerTest {

    @Test
    void deleteItem_removesExistingItem() {
        ItemController controller = new ItemController();
        Long sampleId = controller.getItems().get(0).getId();

        ResponseEntity<Void> response = controller.deleteItem(sampleId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(controller.getItems()).isEmpty();
    }

    @Test
    void deleteItem_returnsNotFoundForUnknownId() {
        ItemController controller = new ItemController();

        ResponseEntity<Void> response = controller.deleteItem(999L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
