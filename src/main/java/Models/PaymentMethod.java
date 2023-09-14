/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hung
 */
public class PaymentMethod {

  private byte paymentMethodID;
  private String paymentMethod;

  public PaymentMethod() {
  }

  public PaymentMethod(byte paymentMethodID, String paymentMethod) {
    this.paymentMethodID = paymentMethodID;
    this.paymentMethod = paymentMethod;
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

}
