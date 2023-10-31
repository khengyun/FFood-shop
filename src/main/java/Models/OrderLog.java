/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Timestamp;

public class OrderLog {
    private int log_id;
    private int order_id;
    private byte staff_id;
    private byte admin_id;
    private String fullname;
    private String log_activity;
    private Timestamp log_time;

    public OrderLog(int log_id, int order_id, String log_activity, Timestamp log_time) {
        this.log_id = log_id;
        this.order_id = order_id;
        this.log_activity = log_activity;
        this.log_time = log_time;
    }
    
    public OrderLog(int order_id, String log_activity, Timestamp log_time) {
        this.order_id = order_id;
        this.log_activity = log_activity;
        this.log_time = log_time;
    }
    
    public OrderLog(int log_id, int order_id, byte staff_id, byte admin_id, String log_activity, Timestamp log_time) {
        this.log_id = log_id;
        this.order_id = order_id;
        this.staff_id = staff_id;
        this.admin_id = admin_id;
        this.log_activity = log_activity;
        this.log_time = log_time;
    }

    public OrderLog(int log_id, int order_id, String fullname, String log_activity, Timestamp log_time) {
        this.log_id = log_id;
        this.order_id = order_id;
        this.fullname = fullname;
        this.log_activity = log_activity;
        this.log_time = log_time;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public byte getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(byte staff_id) {
        this.staff_id = staff_id;
    }

    public byte getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(byte admin_id) {
        this.admin_id = admin_id;
    }

    public String getLog_activity() {
        return log_activity;
    }

    public void setLog_activity(String log_activity) {
        this.log_activity = log_activity;
    }

    public Timestamp getLog_time() {
        return log_time;
    }

    public void setLog_time(Timestamp log_time) {
        this.log_time = log_time;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
