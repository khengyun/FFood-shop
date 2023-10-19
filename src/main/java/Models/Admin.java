/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Hung
 */
public class Admin {

  private byte adminID;
  private String fullName;

  public Admin() {
  }

  public Admin(byte adminID, String fullName) {
    this.adminID = adminID;
    this.fullName = fullName;
  }

  public byte getAdminID() {
    return adminID;
  }

  public void setAdminID(byte adminID) {
    this.adminID = adminID;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

}
