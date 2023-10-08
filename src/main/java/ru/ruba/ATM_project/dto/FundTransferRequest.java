package ru.ruba.ATM_project.dto;

import java.math.BigDecimal;

public class FundTransferRequest {
    private String sourceAccountNumber;
    private String sourceAccountPinCode;
    private String targetAccountNumber;
    private BigDecimal amount;
    private String pinCode;

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }
    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }
    public String getSourceAccountPinCode() {
        return sourceAccountPinCode;
    }
    public void setSourceAccountPinCode(String sourceAccountPinCode) {
        this.sourceAccountPinCode = sourceAccountPinCode;
    }
    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }
    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getPinCode() {
        return pinCode;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
