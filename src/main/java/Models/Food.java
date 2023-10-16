/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Food {

    private short foodID;
    private String foodName;
    private String description;
    private BigDecimal foodPrice;
    private boolean status;
    private byte discountPercent;
    private String imageURL;
    private byte foodTypeID;
    private String foodType;

    public Food() {
    }

    public Food(String foodName, BigDecimal foodPrice, byte discountPercent, String imageURL, String foodType) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodType = foodType;
    }

    public Food(String foodName, BigDecimal foodPrice, byte discountPercent, String imageURL, byte foodTypeID) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
    }

    public Food(String foodName, BigDecimal foodPrice, byte discountPercent, String imageURL, byte foodTypeID, String foodType) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
        this.foodType = foodType;
    }

    public Food(short foodID, String foodName, BigDecimal foodPrice, byte discountPercent, String imageURL, String foodType) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodType = foodType;
    }

    public Food(short foodID, String foodName, BigDecimal foodPrice, byte discountPercent, String imageURL, byte foodTypeID) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
    }

    public Food(short foodID, String foodName, BigDecimal foodPrice, byte discountPercent, String imageURL, byte foodTypeID, String foodType) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
        this.foodType = foodType;
    }

    public Food(short foodID, String foodName, String description, BigDecimal foodPrice, boolean status, byte discountPercent, String imageURL, byte foodTypeID, String foodType) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.description = description;
        this.foodPrice = foodPrice;
        this.status = status;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
        this.foodType = foodType;
    }

    public short getFoodID() {
        return foodID;
    }

    public void setFoodID(short foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public BigDecimal getFoodPrice() {
        return foodPrice;
    }

    public String getFoodPriceFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        return decimalFormat.format(foodPrice);
    }

    public void setFoodPrice(BigDecimal foodPrice) {
        this.foodPrice = foodPrice;
    }

    public byte getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(byte discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public byte getFoodTypeID() {
        return foodTypeID;
    }

    public void setFoodTypeID(byte foodTypeID) {
        this.foodTypeID = foodTypeID;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
