/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class Voucher {

    private byte voucherID;
    private String name;
    private byte voucher_discount_percent;
    

    public Voucher() {
    }

    public Voucher(byte voucherID, String name, byte voucher_discount_percent) {
        this.voucherID = voucherID;
        this.name = name;
        this.voucher_discount_percent = voucher_discount_percent;
    }

    public byte getVoucher_discount_percent() {
        return voucher_discount_percent;
    }

    public void setVoucher_discount_percent(byte voucher_discount_percent) {
        this.voucher_discount_percent = voucher_discount_percent;
    }

    public byte getVoucherID() {
        return voucherID;
    }

    public void setVoucherID(byte voucherID) {
        this.voucherID = voucherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
