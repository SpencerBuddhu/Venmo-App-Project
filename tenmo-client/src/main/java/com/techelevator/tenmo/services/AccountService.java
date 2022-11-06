package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser user;

    public AccountService(String url, AuthenticatedUser user) {
        this.user = user;
        URL = url;
    }

    public BigDecimal printBalance() {
        BigDecimal balance = new BigDecimal(0);
        try {
            balance = restTemplate.exchange(URL + "balance/" + user.getUser().getId(),
                    HttpMethod.GET,
                    token(),
                    BigDecimal.class).getBody();
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public Account[] printAccounts() {
        Account[] accounts = null;
        try {
            accounts = restTemplate.exchange(URL + "accounts",
                    HttpMethod.GET,
                    token(),
                    Account[].class).getBody();
            for (Account account: accounts) {
                System.out.println("Account ID: " + account.getAccountId() + "      User ID: " + account.getUserId());
            }
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    public Account getAccountByUserId(int userId) {
        Account account = null;
        try {
            account = restTemplate.exchange(URL + "accounts/" + userId,
                    HttpMethod.GET,
                    token(),
                    Account.class).getBody();
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    public int getAccountIdByUserId(int userId) {
        int accountId = 0;
        try {
            accountId = restTemplate.exchange(URL + "accountsId" + userId,
                    HttpMethod.GET,
                    token(),
                    Integer.class).getBody();
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return accountId;
    }

    private HttpEntity token() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity token = new HttpEntity(headers);
        return token;
    }

}
