package com.example.mymallapp;

public class WishList {
    private String product_image;
    private String product_title;
    private int free_coupons;
    private int prodid;
    private String rating;
    private int total_ratings;
    private String product_price;
    private String payment_method;

    public WishList(int prodid,String product_image, String product_title, int free_coupons, String rating, String product_price, String payment_method) {
        this.product_image = product_image;
        this.product_title = product_title;
        this.free_coupons = free_coupons;
        this.prodid = prodid;
        this.rating = rating;
        this.total_ratings = total_ratings;
        this.product_price = product_price;
        this.payment_method = payment_method;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public int getFree_coupons() {
        return free_coupons;
    }

    public void setFree_coupons(int free_coupons) {
        this.free_coupons = free_coupons;
    }

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTotal_ratings() {
        return total_ratings;
    }

    public void setTotal_ratings(int total_ratings) {
        this.total_ratings = total_ratings;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
