/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class Role {

    private int accountID;
    private byte roleID;
    private String fullname;
    private String username;
    private String email;
    private String accountType;

    public Role() {
    }

    public Role(int accountID, byte RoleID, String fullname, String username, String email, String accountType) {
        this.accountID = accountID;
        this.roleID = RoleID;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.accountType = accountType;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setRoleID(byte roleID) {
        this.roleID = roleID;
    }
    
    public byte getRoleID() {
        return roleID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
