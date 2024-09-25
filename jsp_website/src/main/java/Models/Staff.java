/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class Staff {

    private byte staffID;
    private String fullName;

    public Staff() {
    }
    
    public Staff(String fullName) {
        this.fullName = fullName;
    }

    public Staff(byte staffID, String fullName) {
        this.staffID = staffID;
        this.fullName = fullName;
    }

    public byte getStaffID() {
        return staffID;
    }

    public void setStaffID(byte staffID) {
        this.staffID = staffID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
