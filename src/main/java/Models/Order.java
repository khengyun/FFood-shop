/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Hung
 */
public class Order {

  private int orderID;
  private int cartID;
  private int customerID;
  private byte orderStatusID;
  private String orderStatus;
  private byte paymentMethodID;
  private String paymentMethod;
  private String contactPhone;
  private String deliveryAddress;
  private List<String> orderItems;
  private Timestamp orderTime;
  private BigDecimal orderTotal = new BigDecimal(BigInteger.ZERO);
  private String orderNote;
  private Timestamp deliveryTime;
  private Timestamp orderCancelTime;

  public Order() {
  }

    public Order(int cartID, int customerID, byte orderStatusID, byte paymentMethodID, String contactPhone, String deliveryAddress, Timestamp orderTime, String orderNote, Timestamp deliveryTime) {
        this.cartID = cartID;
        this.customerID = customerID;
        this.orderStatusID = orderStatusID;
        this.paymentMethodID = paymentMethodID;
        this.contactPhone = contactPhone;
        this.deliveryAddress = deliveryAddress;
        this.orderTime = orderTime;
        this.orderNote = orderNote;
        this.deliveryTime = deliveryTime;
      
    }

  public Order(int cartID, int customerID, byte orderStatusID, String orderStatus,
          byte paymentMethodID, String paymentMethod, String contactPhone,
          String deliveryAddress, List<String> orderItems, BigDecimal orderTotal,
          Timestamp orderTime, String orderNote, Timestamp deliveryTime, Timestamp orderCancelTime) {
    this.cartID = cartID;
    this.customerID = customerID;
    this.orderStatusID = orderStatusID;
    this.orderStatus = orderStatus;
    this.paymentMethodID = paymentMethodID;
    this.paymentMethod = paymentMethod;
    this.contactPhone = contactPhone;
    this.deliveryAddress = deliveryAddress;
    this.orderItems = orderItems;
    this.orderTotal = orderTotal;
    this.orderTime = orderTime;
    this.orderNote = orderNote;
    this.deliveryTime = deliveryTime;
    this.orderCancelTime = orderCancelTime;
  }

  public Order(int orderID, int cartID, int customerID, byte orderStatusID,
          String orderStatus, byte paymentMethodID, String paymentMethod,
          String contactPhone, String deliveryAddress, List<String> orderItems,
          BigDecimal orderTotal, Timestamp orderTime, String orderNote,
          Timestamp deliveryTime, Timestamp orderCancelTime) {
    this.orderID = orderID;
    this.cartID = cartID;
    this.customerID = customerID;
    this.orderStatusID = orderStatusID;
    this.orderStatus = orderStatus;
    this.paymentMethodID = paymentMethodID;
    this.paymentMethod = paymentMethod;
    this.contactPhone = contactPhone;
    this.deliveryAddress = deliveryAddress;
    this.orderItems = orderItems;
    this.orderTotal = orderTotal;
    this.orderTime = orderTime;
    this.orderNote = orderNote;
    this.deliveryTime = deliveryTime;
    this.orderCancelTime = orderCancelTime;
  }

  public int getOrderID() {
    return orderID;
  }

  public void setOrderID(int orderID) {
    this.orderID = orderID;
  }

  public int getCartID() {
    return cartID;
  }

  public void setCartID(int cartID) {
    this.cartID = cartID;
  }

  public int getCustomerID() {
    return customerID;
  }
public void setCustomerID(int customerID) {
    this.customerID = customerID;
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

  public byte getPaymentMethodID() {
    return paymentMethodID;
  }

  public void setPaymentMethodID(byte paymentMethodID) {
    this.paymentMethodID = paymentMethodID;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public List<String> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<String> orderItems) {
    this.orderItems = orderItems;
  }

  public BigDecimal getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(BigDecimal orderTotal) {
    this.orderTotal = orderTotal;
  }

  public Timestamp getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(Timestamp orderTime) {
    this.orderTime = orderTime;
  }

  public String getOrderNote() {
    return orderNote;
  }

  public void setOrderNote(String orderNote) {
    this.orderNote = orderNote;
  }

  public Timestamp getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(Timestamp deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public Timestamp getOrderCancelTime() {
    return orderCancelTime;
  }

  public void setOrderCancelTime(Timestamp orderCancelTime) {
    this.orderCancelTime = orderCancelTime;
  }

}