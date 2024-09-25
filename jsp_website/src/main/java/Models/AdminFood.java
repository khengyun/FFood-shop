/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hung
 */
public class AdminFood {

  private byte adminID;
  private short foodID;

  public AdminFood(byte adminID, short foodID) {
    this.adminID = adminID;
    this.foodID = foodID;
  }

  public AdminFood() {
  }

  public byte getAdminID() {
    return adminID;
  }

  public void setAdminID(byte adminID) {
    this.adminID = adminID;
  }

  public short getFoodID() {
    return foodID;
  }

  public void setFoodID(short foodID) {
    this.foodID = foodID;
  }

}
