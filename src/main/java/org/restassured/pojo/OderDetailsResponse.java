package org.restassured.pojo;

public class OderDetailsResponse {

    private String message;
    private OrderDetails data;

    public OrderDetails getData() {
        return data;
    }

    public void setData(OrderDetails data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
