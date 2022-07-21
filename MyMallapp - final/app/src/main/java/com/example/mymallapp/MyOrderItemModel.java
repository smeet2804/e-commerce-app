package com.example.mymallapp;

public class MyOrderItemModel {

    private String product_image;
    private String product_title;
    private String delivery_status;
    private String orderid;
    private int rating;

    public MyOrderItemModel(String orderid,String product_image,int rating ,String product_title, String delivery_status) {
        this.product_image = product_image;
        this.orderid=orderid;
        this.rating=rating;
        this.product_title = product_title;
        this.delivery_status = delivery_status;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
