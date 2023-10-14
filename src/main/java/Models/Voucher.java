/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class Voucher {

    private byte voucherID;
    private byte voucher_discount_percent;
    private String name;

    public Voucher() {
    }

    public Voucher(byte voucherID, byte voucher_discount_percent, String name) {
        this.voucherID = voucherID;
        this.voucher_discount_percent = voucher_discount_percent;
        this.name = name;
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
