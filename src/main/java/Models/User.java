/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Timestamp;

public class User {
    private int userID;
    private int customerID;
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private Timestamp lasttime_order;
    public User() {
    }

    public User(int userID, int customerID, String username, String firstName, String lastName, String fullName, String gender, String phone, String email, String address) {
        this.userID = userID;
        this.customerID = customerID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
    
    public User(int userID, int customerID, String username, String firstName, String lastName, String fullName, String gender, String phone, String email, String address, Timestamp lasttime_order) {
        this.userID = userID;
        this.customerID = customerID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.lasttime_order = lasttime_order;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getLasttime_order() {
        return lasttime_order;
    }

    public void setLasttime_order(Timestamp lasttime_order) {
        this.lasttime_order = lasttime_order;
    }
}
