package com.example.demo.model;

public class Promotion {

    private Long id;
    private String productCode;
    private String freeGoodCode;
    private int buyQuantity;
    private int getQuantity;

    public Promotion() {
    }

    public Promotion(Long id, String productCode, String freeGoodCode, int buyQuantity, int getQuantity) {
        this.id = id;
        this.productCode = productCode;
        this.freeGoodCode = freeGoodCode;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getFreeGoodCode() {
        return freeGoodCode;
    }

    public void setFreeGoodCode(String freeGoodCode) {
        this.freeGoodCode = freeGoodCode;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public int getGetQuantity() {
        return getQuantity;
    }

    public void setGetQuantity(int getQuantity) {
        this.getQuantity = getQuantity;
    }
}
