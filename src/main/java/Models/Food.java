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
    private Short quantity;
    private byte status;
    private byte rate;
    private byte discountPercent;   
    private String imageURL;
    private byte foodTypeID;
    private String foodType;

    public Food() {
    }
    
    public Food(String foodName, String description, BigDecimal foodPrice, byte status, byte rate, byte discountPercent, String imageURL, byte foodTypeID, String foodType) {
        this.foodName = foodName;
        this.description = description;
        this.foodPrice = foodPrice;
        this.status = status;
        this.rate = rate;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
        this.foodType = foodType;
    }
    
    public Food(String foodName, String description, BigDecimal foodPrice, byte status, byte rate, byte discountPercent, String imageURL, byte foodTypeID) {
        this.foodName = foodName;
        this.description = description;
        this.foodPrice = foodPrice;
        this.status = status;
        this.rate = rate;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
    }
    
    public Food(short foodID, String foodName, String description, BigDecimal foodPrice, byte status, byte rate, byte discountPercent, String imageURL, byte foodTypeID) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.description = description;
        this.foodPrice = foodPrice;
        this.status = status;
        this.rate = rate;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
    }

    public Food(short foodID, String foodName, String description, BigDecimal foodPrice, byte status, byte rate, byte discountPercent, String imageURL, byte foodTypeID, String foodType) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.description = description;
        this.foodPrice = foodPrice;
        this.status = status;
        this.rate = rate;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
        this.foodType = foodType;
    }
    
     public Food(short foodID, String foodName, String description, BigDecimal foodPrice, Short quantity, byte status, byte rate, byte discountPercent, String imageURL, byte foodTypeID, String foodType) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.description = description;
        this.foodPrice = foodPrice;
        this.quantity = quantity;
        this.status = status;
        this.rate = rate;
        this.discountPercent = discountPercent;
        this.imageURL = imageURL;
        this.foodTypeID = foodTypeID;
        this.foodType = foodType;
    }

    public Food(short foodID, byte discountPercent) {
        this.foodID = foodID;
        this.discountPercent = discountPercent;
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

//    public String getFoodPriceFormat() {
//        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
//        return decimalFormat.format(this.foodPrice-(this.foodPrice * this.discountPercent / 100));
//    }
    
    public String getFoodPriceFormat() {
        BigDecimal discountedPrice = foodPrice.subtract(foodPrice.multiply(BigDecimal.valueOf(discountPercent / 100)));
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");
        return decimalFormat.format(discountedPrice);
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

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getRate() {
        return rate;
    }

    public void setRate(byte rate) {
        this.rate = rate;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }
}
