/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hung
 */
public class OrderStatus {

  private byte orderStatusID;
  private String orderStatus;

  public OrderStatus() {
  }

  public OrderStatus(byte orderStatusID, String orderStatus) {
    this.orderStatusID = orderStatusID;
    this.orderStatus = orderStatus;
  }

  public byte getOrderStatusID() {
    return orderStatusID;
  }

  public void setOrderStatusID(byte orderStatusID) {
    this.orderStatusID = orderStatusID;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

}
