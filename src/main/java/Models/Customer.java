/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hung
 */
public class Customer {

  private int customerID;
  private String firstName;
  private String lastName;
  private String fullName;
  private String gender;
  private String phone;
  private String address;

  public Customer() {
  }

  public Customer(String firstName, String lastName, String gender, String phone, String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.phone = phone;
    this.address = address;
  }

  public Customer(int customerID, String firstName, String lastName, String gender, String phone, String address) {
    this.customerID = customerID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = lastName.trim() + " " + firstName.trim();
    this.gender = gender;
    this.phone = phone;
    this.address = address;
  }

  public int getCustomerID() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
