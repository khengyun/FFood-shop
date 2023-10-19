
package Models;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Voucher {
    private String voucherID;
    private String voucherCode;
    private float voucherValue;
    private int voucherQuantity;
    private int voucherCondition;
    private Date voucherExpDate;
    private String voucherStatus;

    public Voucher() {
    }

    public Voucher(String voucherID, String voucherCode, float voucherValue, int voucherQuantity, int voucherCondition, Date voucherExpDate, String voucherStatus) {
        this.voucherID = voucherID;
        this.voucherCode = voucherCode;
        this.voucherValue = voucherValue;
        this.voucherQuantity = voucherQuantity;
        this.voucherCondition = voucherCondition;
        this.voucherExpDate = voucherExpDate;
        this.voucherStatus = voucherStatus;
    }

    public String getVoucherID() {
        return voucherID;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public float getVoucherValue() {
        return voucherValue;
    }

    public int getVoucherQuantity() {
        return voucherQuantity;
    }

    public int getVoucherCondition() {
        return voucherCondition;
    }

    public Date getVoucherExpDate() {
        return voucherExpDate;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherID(String voucherID) {
        this.voucherID = voucherID;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public void setVoucherValue(float voucherValue) {
        this.voucherValue = voucherValue;
    }

    public void setVoucherQuantity(int voucherQuantity) {
        this.voucherQuantity = voucherQuantity;
    }

    public void setVoucherCondition(int voucherCondition) {
        this.voucherCondition = voucherCondition;
    }

    public void setVoucherExpDate(Date voucherExpDate) {
        this.voucherExpDate = voucherExpDate;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    
    
}
