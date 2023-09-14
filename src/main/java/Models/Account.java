/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hung
 */
public class Account {

  private int accountID;
  private int customerID;
  private byte adminID;
  private String username;
  private String email;
  private String password;
  private String accountType;

  public Account() {
  }

  public Account(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Account(String username, String email, String password, String accountType) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.accountType = accountType;
  }

  public Account(int customerID, String username, String email, String password, String accountType) {
    this.customerID = customerID;
    this.username = username;
    this.email = email;
    this.password = password;
    this.accountType = accountType;
  }

  public Account(byte adminID, String username, String email, String password, String accountType) {
    this.adminID = adminID;
    this.username = username;
    this.email = email;
    this.password = password;
    this.accountType = accountType;
  }

  public Account(int accountID, int customerID, String username, String email, String password, String accountType) {
    this.accountID = accountID;
    this.customerID = customerID;
    this.username = username;
    this.email = email;
    this.password = password;
    this.accountType = accountType;
  }

  public Account(int accountID, byte adminID, String username, String email, String password, String accountType) {
    this.accountID = accountID;
    this.adminID = adminID;
    this.username = username;
    this.email = email;
    this.password = password;
    this.accountType = accountType;
  }

  public int getAccountID() {
    return accountID;
  }

  public void setAccountID(int accountID) {
    this.accountID = accountID;
  }

  public int getCustomerID() {
    return customerID;
  }

  public void setCustomerID(int customerID) {
    this.customerID = customerID;
  }

  public byte getAdminID() {
    return adminID;
  }

  public void setAdminID(byte adminID) {
    this.adminID = adminID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

}
