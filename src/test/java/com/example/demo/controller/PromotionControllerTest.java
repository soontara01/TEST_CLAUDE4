package com.example.demo.controller;

import com.example.demo.model.Promotion;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionControllerTest {

    @Test
    void getPromotions_returnsSeededBuyOneGetOnePromotion() {
        PromotionController controller = new PromotionController();

        List<Promotion> promotions = controller.getPromotions();

        assertThat(promotions).hasSize(1);

        Promotion sample = promotions.get(0);
        assertThat(sample.getId()).isNotNull();
        assertThat(sample.getProductCode()).isEqualTo("PRD-0001");
        assertThat(sample.getFreeGoodCode()).isEqualTo("FG-0001");
        assertThat(sample.getBuyQuantity()).isEqualTo(1);
        assertThat(sample.getGetQuantity()).isEqualTo(1);
    }

    @Test
    void createPromotion_addsNewPromotionAndReturnsCreated() {
        PromotionController controller = new PromotionController();
        Promotion newPromotion = new Promotion(null, "PRD-0002", "FG-0002", 2, 1);

        ResponseEntity<Promotion> response = controller.createPromotion(newPromotion);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Promotion created = response.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getProductCode()).isEqualTo("PRD-0002");
        assertThat(created.getFreeGoodCode()).isEqualTo("FG-0002");

        assertThat(controller.getPromotions()).hasSize(2);
    }

    @Test
    void deletePromotion_removesExistingPromotion() {
        PromotionController controller = new PromotionController();
        Long sampleId = controller.getPromotions().get(0).getId();

        ResponseEntity<Void> response = controller.deletePromotion(sampleId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(controller.getPromotions()).isEmpty();
    }

    @Test
    void deletePromotion_returnsNotFoundForUnknownId() {
        PromotionController controller = new PromotionController();

        ResponseEntity<Void> response = controller.deletePromotion(999L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void applyPromotion_buyOneGetOne_returnsMatchingFreeQuantity() {
        PromotionController controller = new PromotionController();
        Long sampleId = controller.getPromotions().get(0).getId();

        ResponseEntity<Integer> response = controller.applyPromotion(sampleId, 3);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(3);
    }

    @Test
    void applyPromotion_returnsNotFoundForUnknownId() {
        PromotionController controller = new PromotionController();

        ResponseEntity<Integer> response = controller.applyPromotion(999L, 3);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
