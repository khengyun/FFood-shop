/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Timestamp;
import java.util.Date;

public class Voucher {
    private byte voucherID;
    private String voucher_name;
    private String voucher_code;
    private byte voucher_discount_percent;
    private byte voucher_quantity;
    private byte voucher_status;
    private Timestamp voucher_date;
   

    public Voucher() {
    }
    public Voucher(byte voucherID, String voucher_name, byte voucher_discount_percent) {
        this.voucherID = voucherID;
        this.voucher_name = voucher_name;
        this.voucher_discount_percent = voucher_discount_percent;
    }
    
    public Voucher(String voucher_name, byte voucher_discount_percent) {
        this.voucher_name = voucher_name;
        this.voucher_discount_percent = voucher_discount_percent;
    }
    
    public Voucher(String voucher_name, String voucher_code, byte voucher_discount_percent, byte voucher_quantity, byte voucher_status, Timestamp voucher_date) {
        this.voucherID = voucherID;
        this.voucher_name = voucher_name;
        this.voucher_code = voucher_code;
        this.voucher_discount_percent = voucher_discount_percent;
        this.voucher_quantity = voucher_quantity;
        this.voucher_status = voucher_status;
        this.voucher_date = voucher_date;
    }

    public Voucher(byte voucherID, String voucher_name, String voucher_code, byte voucher_discount_percent, byte voucher_quantity, byte voucher_status, Timestamp voucher_date) {
        this.voucherID = voucherID;
        this.voucher_name = voucher_name;
        this.voucher_code = voucher_code;
        this.voucher_discount_percent = voucher_discount_percent;
        this.voucher_quantity = voucher_quantity;
        this.voucher_status = voucher_status;
        this.voucher_date = voucher_date;
    }

    public byte getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(byte voucherID) {
        this.voucherID = voucherID;
    }

    public String getVoucher_name() {
        return voucher_name;
    }

    public void setVoucher_name(String voucher_name) {
        this.voucher_name = voucher_name;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public byte getVoucher_quantity() {
        return voucher_quantity;
    }

    public void setVoucher_quantity(byte voucher_quantity) {
        this.voucher_quantity = voucher_quantity;
    }

    public byte getVoucher_status() {
        return voucher_status;
    }

    public void setVoucher_status(byte voucher_status) {
        this.voucher_status = voucher_status;
    }

    public byte getVoucher_discount_percent() {
        return voucher_discount_percent;
    }
    
    public double getVoucherDiscount() {
        int intValue = Byte.toUnsignedInt(voucher_discount_percent); 
        System.out.println("intValue " + intValue);
        double percentage = (double) intValue / 100;
        return percentage;
    }

    public void setVoucher_discount_percent(byte voucher_discount_percent) {
        this.voucher_discount_percent = voucher_discount_percent;
    }

    public Timestamp getVoucher_date() {
        return voucher_date;
    }

    public void setVoucher_date(Timestamp voucher_date) {
        this.voucher_date = voucher_date;
    }
}

