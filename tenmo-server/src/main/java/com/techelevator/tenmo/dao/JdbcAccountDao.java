package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Account account = mapRowToAccount(results);
                accounts.add(account);
            }
        } catch (DataAccessException e) {
            System.out.println("Error");
        }
        return accounts;
    }

    @Override
    public Account getUserById(int userId) {
        String string = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(string, userId);
            account = mapRowToAccount(result);
        } catch (DataAccessException e) {
            System.out.println("Error");
        }
        return account;
    }

   public int getAccountIdByUserId(int userId) {
        String string = "SELECT account_id FROM account WHERE user_id = ?";
        int accountId = 0;
        SqlRowSet results = null;
        try {
            results = jdbcTemplate.queryForRowSet(string, userId);
            if (results.next()) {
                accountId = results.getInt("account_id");
            }
        } catch (DataAccessException e) {
            System.out.println();
        }
        return accountId;
    }


    @Override
    public Account getAccountByUserId(int userId) {
        String string = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(string, userId);
            account = mapRowToAccount(result);
        } catch (DataAccessException e) {
            System.out.println("Error");
        }
        return account;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        String string = "SELECT balance FROM account WHERE user_id = ?";
        SqlRowSet results = null;
        BigDecimal balance = null;
        try {
            results = jdbcTemplate.queryForRowSet(string, userId);
            if (results.next()) {
                balance = results.getBigDecimal("balance");
            }
        } catch (DataAccessException e) {
            System.out.println("Error");
        }
        return balance;
    }

    @Override
    public BigDecimal subtractBalance(BigDecimal moneySubtract, int userId) {
        Account account = getAccountByUserId(userId);
        BigDecimal updatedBalance = account.getBalance().subtract(moneySubtract);
        String string = "UPDATE account SET balance = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(string, updatedBalance, userId);
        } catch (DataAccessException e) {
            System.out.println("Error");
        }
        return account.getBalance();
    }

    @Override
    public BigDecimal addBalance(BigDecimal moneyAdd, int userId) {
        Account account = getAccountByUserId(userId);
        BigDecimal updatedBalance = account.getBalance().add(moneyAdd);
        String string = "UPDATE account SET balance = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(string, updatedBalance, userId);
        } catch (DataAccessException e) {
            System.out.println("Error");
        }
        return account.getBalance();
    }

    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setUserId(result.getInt("user_id"));
        account.setBalance(result.getBigDecimal("balance"));
        account.setAccountId(result.getInt("account_id"));
        return account;
    }



}
