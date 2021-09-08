package com.codestates.seb.OAuthServer.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {
    private String client_id;
    private String client_secret;
    private String code;

    public UserData() { }

    public UserData(String client_id, String client_secret, String code) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
    }
}
