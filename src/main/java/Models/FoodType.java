/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hung
 */
public class FoodType {

  private byte foodTypeID;
  private String foodType;
  private String imgURL;

  public FoodType() {
  }

  public FoodType(byte foodTypeID, String foodType) {
    this.foodTypeID = foodTypeID;
    this.foodType = foodType;
  }

    public FoodType(byte foodTypeID, String foodType, String imgURL) {
        this.foodTypeID = foodTypeID;
        this.foodType = foodType;
        this.imgURL = imgURL;
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

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

  
}
