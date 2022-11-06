package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransferService {

    private String URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser user;
    private AccountService accountService;

    public TransferService(String url, AuthenticatedUser user) {
        this.user = user;
        URL = url;
    }

    public Transfer sendMoney() {
        Transfer transfer = new Transfer();
        try {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the account you wish to send money to: ");

        transfer.setAccountTo(accountService.getAccountIdByUserId(Integer.parseInt(scanner.nextLine())));

        transfer.setAccountFrom(accountService.getAccountIdByUserId(user.getUser().getId()));

        System.out.println("Enter amount you want to send: ");

        transfer.setAmount(new BigDecimal(Double.parseDouble(scanner.nextLine())));

        transfer = restTemplate.exchange(URL + "sendMoney/" + transfer.getAccountTo() + transfer.getAccountFrom() + transfer.getAmount(),
                    HttpMethod.PUT,
                    transferToken(transfer),
                    Transfer.class).getBody();
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }


    private HttpEntity token() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity token = new HttpEntity(headers);
        return token;
    }

    private HttpEntity<Transfer> transferToken(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity<Transfer> token = new HttpEntity<>(transfer, headers);
        return token;
    }


}
