/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class Voucher {

    private byte voucher_id;
    private byte voucher_discount_percent;
    private String voucher_name;

    public Voucher() {
    }

    public Voucher(byte voucher_id, byte voucher_discount_percent, String voucher_name) {
        this.voucher_id = voucher_id;
        this.voucher_discount_percent = voucher_discount_percent;
        this.voucher_name = voucher_name;
    }

    public byte getVoucher_discount_percent() {
        return voucher_discount_percent;
    }

    public void setVoucher_discount_percent(byte voucher_discount_percent) {
        this.voucher_discount_percent = voucher_discount_percent;
    }

    public byte getVoucher_Id() {
        return voucher_id;
    }

    public void setVoucher_Id(byte voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getName() {
        return voucher_name;
    }

    public void setName(String voucher_name) {
        this.voucher_name = voucher_name;
    }

}
