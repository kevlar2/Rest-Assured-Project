package org.restassured.pojo;

public class OrderDetail {

    String country;
    String ProductOrderedId;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProductOrderedId() {
        return ProductOrderedId;
    }

    public void setProductOrderedId(String productOrderedId) {
        ProductOrderedId = productOrderedId;
    }
}
