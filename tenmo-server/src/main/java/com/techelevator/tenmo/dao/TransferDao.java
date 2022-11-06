package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfers();

    List<Transfer> getTransfersByUserId(int userId);

    List<Transfer> getTransfersByAccountId(int accountFrom);

    List<Transfer> getPendingTransfers(int userId);

    Transfer getTransferByTransferId(int transferId);

    Transfer createTransfer(Transfer transfer);

    void updateTransfer(Transfer transfer);

    Transfer getTransfer(int transferId);

    void setTransfer(int transferId);

    String getTransferTypeId();

    void setTransferTypeId(String transferTypeId);

    String getTransferStatusId();

    void setTransferStatusId(String transferStatusId);

    int getAccountFrom();

    void setAccountFrom(int accountFrom);

    int getAccountTo();

    void setAccountTo(int accountTo);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    void sendMoney(int accountTo, int accountFrom, BigDecimal amount);


}

