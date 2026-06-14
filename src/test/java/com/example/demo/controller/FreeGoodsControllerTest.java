package com.example.demo.controller;

import com.example.demo.model.FreeGoods;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FreeGoodsControllerTest {

    @Test
    void getFreeGoods_returnsSeededSampleFreeGoods() {
        FreeGoodsController controller = new FreeGoodsController();

        List<FreeGoods> freeGoods = controller.getFreeGoods();

        assertThat(freeGoods).hasSize(1);

        FreeGoods sample = freeGoods.get(0);
        assertThat(sample.getId()).isNotNull();
        assertThat(sample.getCode()).isEqualTo("FG-0001");
        assertThat(sample.getName()).isEqualTo("Sample Free Good");
        assertThat(sample.getQuantity()).isEqualTo(10);
    }

    @Test
    void createFreeGoods_addsNewFreeGoodsAndReturnsCreated() {
        FreeGoodsController controller = new FreeGoodsController();
        FreeGoods newItem = new FreeGoods(null, "FG-0002", "New Free Good", 5);

        ResponseEntity<FreeGoods> response = controller.createFreeGoods(newItem);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        FreeGoods created = response.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getCode()).isEqualTo("FG-0002");
        assertThat(created.getName()).isEqualTo("New Free Good");
        assertThat(created.getQuantity()).isEqualTo(5);

        List<FreeGoods> freeGoods = controller.getFreeGoods();
        assertThat(freeGoods).hasSize(2);
        assertThat(freeGoods)
                .extracting(FreeGoods::getCode)
                .containsExactlyInAnyOrder("FG-0001", "FG-0002");
    }
}
