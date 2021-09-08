package com.codestates.seb.OAuthServer.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {

    private String access_token;
    private String scope;
    private String token_type;

}
