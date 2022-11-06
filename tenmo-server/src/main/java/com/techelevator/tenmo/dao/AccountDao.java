package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    public List<Account> getAccounts();
    public Account getUserById(int userId);
    public Account getAccountByUserId(int userId);
    public BigDecimal getBalance(int userId);
    public BigDecimal subtractBalance(BigDecimal amountToSubtract, int userId);
    public BigDecimal addBalance(BigDecimal amountToAdd, int userId);
    public int getAccountIdByUserId(int userId);

}
