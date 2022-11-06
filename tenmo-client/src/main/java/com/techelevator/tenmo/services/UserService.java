package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class UserService {

    private String URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser user;

    public UserService(String url, AuthenticatedUser user) {
        this.user = user;
        URL = url;
    }

    public User[] printUsers() {
        User[] users = null;
        try {
            users = restTemplate.exchange(URL + "users",
                    HttpMethod.GET,
                    token(),
                    User[].class).getBody();
            for (User user: users) {
                System.out.println("ID: " + user.getId() + "      Username: " + user.getUsername());
            }
        } catch (Exception e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    private HttpEntity token() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user.getToken());
        HttpEntity token = new HttpEntity(headers);
        return token;
    }

}
