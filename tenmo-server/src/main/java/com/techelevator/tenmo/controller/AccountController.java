package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigDecimal;
import java.util.List;


@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {


    private AccountDao accountDao;
    private UserDao userDao;
    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "accounts", method = RequestMethod.GET)
    public List <Account> accounts() {
        List <Account> accounts = accountDao.getAccounts();
        return accounts;
    }

    @RequestMapping(path = "accountsId/{userId}", method = RequestMethod.GET)
    public int getAccountId (@PathVariable int userId) {
        int accountId = accountDao.getAccountIdByUserId(userId);
        return accountId;
    }

    @RequestMapping(path = "accounts/{userId}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int userId) {
        return accountDao.getAccountByUserId(userId);
    }

    @RequestMapping(path = "balance/{userId}", method = RequestMethod.GET)
    public BigDecimal getBalance (@PathVariable int userId) {
        BigDecimal balance = accountDao.getBalance(userId);
        return balance;
    }

    @RequestMapping(path = "users", method = RequestMethod.GET)
    public List <User> users() {
        List <User> listUsers = userDao.findAll();
        return listUsers;
    }


}
