package com.techelevator.tenmo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{


    private int transferId;
    private String transferTypeId;
    private String transferStatusId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void sendMoney(int accountTo, int accountFrom, BigDecimal amount){
        String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;\n" +     //set balance in original account - transfer
                "UPDATE account SET balance = balance + ? WHERE account_id = ?;\n" +  //set balance in other account + transfer amt
                "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (2, 2, ?, ?, ?);\n" +               //2 for transfer status approved initially
                "COMMIT;";
        jdbcTemplate.update(sql, amount, accountFrom, amount, accountTo, accountFrom, accountTo, amount);
    }


    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public List<Transfer> getTransfersByUserId(int userId) {
        return null;
    }


    @Override
    public List<Transfer> getTransfersByAccountId(int accountFrom) {
        List<Transfer> transfersByAccountId = new ArrayList<>();
        String sql = "SELECT transfer_id FROM transfer WHERE transfer_id = account_from = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfersByAccountId.add(transfer);
        }
        return transfersByAccountId;
    }

    @Override
    public List<Transfer> getPendingTransfers(int userId) {
        List<Transfer> transfersByPendingStatus = new ArrayList<>();
        String sql = "SELECT transfer_id FROM transfer WHERE transfer_status_id = 1";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfersByPendingStatus.add(transfer);
        }
        return transfersByPendingStatus;
    }

    @Override
    public Transfer getTransferByTransferId(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);

        }
        return transfer;
    }

    @Override
    public Transfer createTransfer(Transfer newTransfer) {
        String sql = "INSERT INTO transfer (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING transfer_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getTransferId(), newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId()
                , newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
        return getTransfer(newId);
    }

    @Override
    public void updateTransfer(Transfer updatedTransfer) {
        String sql = "UPDATE transfer " +
                "SET transfer_id = ?, transfer_type_id = ?, transfer_status_id = ?, account_from = ?, account_to = ?, amount = ? " +
                "WHERE transfer_id = ?";
        jdbcTemplate.update(sql, updatedTransfer.getTransferId(), updatedTransfer.getTransferTypeId(), updatedTransfer.getTransferStatusId(),
                updatedTransfer.getAccountFrom(), updatedTransfer.getAccountTo(), updatedTransfer.getAmount());
    }

    @Override
    public Transfer getTransfer(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if(results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public void setTransfer(int transferId) {

    }

    @Override
    public String getTransferTypeId() {
        return transferTypeId;
    }
    @Override
    public void setTransferTypeId(String transferTypeId) {
        this.transferTypeId = transferTypeId;
    }
    @Override
    public String getTransferStatusId() {
        return transferStatusId;
    }
    @Override
    public void setTransferStatusId(String transferStatusId) {
        this.transferStatusId = transferStatusId;
    }
    @Override
    public int getAccountFrom() {
        return accountFrom;
    }
    @Override
    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }
    @Override
    public int getAccountTo() {
        return accountTo;
    }
    @Override
    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }
    @Override
    public BigDecimal getAmount() {
        return amount;
    }
    @Override
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferStatusId(results.getString("transfer_status_id"));
        transfer.setTransferTypeId(results.getString("transfer_type_id"));
        return transfer;
    }


}
