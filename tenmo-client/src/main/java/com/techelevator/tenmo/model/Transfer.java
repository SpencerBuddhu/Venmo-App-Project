package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private String transferTypeId;
    private String transferStatusId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;
    private String userTo;
    private String userFrom;
    Account idkAccountTo;
    Account idkAccountFrom;


    public Account getIdkAccountTo() {
        return idkAccountTo;
    }

    public void setIdkAccountTo(Account idkAccountTo) {
        this.idkAccountTo = idkAccountTo;
    }

    public Account getIdkAccountFrom() {
        return idkAccountFrom;
    }

    public void setIdkAccountFrom(Account idkAccountFrom) {
        this.idkAccountFrom = idkAccountFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(String transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(String transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
