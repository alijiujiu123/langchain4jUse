package org.example.entity;

public class InvoiceData {
    private String invoiceId;       // 唯一标识
    private String invoiceNumber;   // 发票号码
    private String status;          // 开票状态
    private double amount;          // 金额
    private String issuedAt;        // 开票时间

    // 构造器
    public InvoiceData(String invoiceId, String invoiceNumber, String status, double amount, String issuedAt) {
        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.status = status;
        this.amount = amount;
        this.issuedAt = issuedAt;
    }

    @Override
    public String toString() {
        return "InvoiceData{" +
                "invoiceId='" + invoiceId + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", issuedAt='" + issuedAt + '\'' +
                '}';
    }
}
