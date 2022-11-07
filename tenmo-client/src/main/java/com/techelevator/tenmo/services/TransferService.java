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

    private final String URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthenticatedUser user;


    public TransferService(String url, AuthenticatedUser user) {
        this.user = user;
        URL = url;
    }

    public void sendMoney() {
        AccountService accountService = new AccountService(URL, user);
        Transfer transfer = new Transfer();
        try {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the account you wish to send money to: ");

        transfer.setAccountTo(accountService.getAccountIdByUserId(Integer.parseInt(scanner.nextLine())));

        transfer.setAccountFrom(accountService.getAccountIdByUserId(user.getUser().getId()));

        System.out.println("Enter amount you want to send: ");

        transfer.setAmount(BigDecimal.valueOf(Double.parseDouble(scanner.nextLine())));

        restTemplate.exchange(URL + "sendMoney/" + transfer.getAccountTo() + "/" + transfer.getAccountFrom() + "/" + transfer.getAmount(),
                    HttpMethod.PUT,
                    transferToken(transfer),
                    Transfer.class).getBody();
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }

    }

    public Transfer[] printTransfers(int userId) {
        Transfer[] transferList = null;
        try {
            transferList = restTemplate.exchange(URL + "transfer/user/" + userId,
                    HttpMethod.GET,
                    token(),
                    Transfer[].class).getBody();
            for (Transfer transfer: transferList) {
                System.out.println("Transaction:");
                System.out.println("");
                System.out.println("Transfer ID: " + transfer.getTransferId());
                System.out.println("Transfer Type: " + transfer.getTransferTypeId());
                System.out.println("Transfer Status: " + transfer.getTransferStatusId());
                System.out.println("Account from: " + transfer.getAccountFrom());
                System.out.println("Account to: " + transfer.getAccountTo());
                System.out.println("Amount: " + transfer.getAmount());
                System.out.println("");
                System.out.println("*********************************");
                System.out.println("");
            }
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return transferList;
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
