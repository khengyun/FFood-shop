/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class PromotionManager {

    private byte proID;
    private String fullName;

    public PromotionManager() {
    }
    
    public PromotionManager(String fullName) {
        this.fullName = fullName;
    }

    public PromotionManager(byte proID, String fullName) {
        this.proID = proID;
        this.fullName = fullName;
    }

    public byte getProID() {
        return proID;
    }

    public void setProID(byte proID) {
        this.proID = proID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
