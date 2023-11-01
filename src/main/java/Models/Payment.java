/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class Payment {
    private int order_id;
    private byte payment_method_id;
    private BigDecimal payment_total = new BigDecimal(BigInteger.ZERO);
    private String payment_content;
    private String payment_bank;
    private String payment_code;
    private byte payment_status;
    private Timestamp payment_time;

    public Payment(int order_id, byte payment_method_id, BigDecimal payment_total,String payment_content, String payment_bank, String payment_code, byte payment_status, Timestamp payment_time) {
        this.order_id = order_id;
        this.payment_method_id = payment_method_id;
        this.payment_total = payment_total;
        this.payment_content = payment_content;
        this.payment_bank = payment_bank;
        this.payment_code = payment_code;
        this.payment_status = payment_status;
        this.payment_time = payment_time;
    }
    
    public Payment(int order_id, byte payment_method_id, String payment_content, String payment_bank, String payment_code, byte payment_status, Timestamp payment_time) {
        this.order_id = order_id;
        this.payment_method_id = payment_method_id;
        this.payment_content = payment_content;
        this.payment_bank = payment_bank;
        this.payment_code = payment_code;
        this.payment_status = payment_status;
        this.payment_time = payment_time;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public byte getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(byte payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public BigDecimal getPayment_total() {
        return payment_total;
    }

    public void setPayment_total(BigDecimal payment_total) {
        this.payment_total = payment_total;
    }

    public String getPayment_content() {
        return payment_content;
    }

    public void setPayment_content(String payment_content) {
        this.payment_content = payment_content;
    }

    public String getPayment_bank() {
        return payment_bank;
    }

    public void setPayment_bank(String payment_bank) {
        this.payment_bank = payment_bank;
    }

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public byte getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(byte payment_status) {
        this.payment_status = payment_status;
    }

    public Timestamp getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Timestamp payment_time) {
        this.payment_time = payment_time;
    }
}
