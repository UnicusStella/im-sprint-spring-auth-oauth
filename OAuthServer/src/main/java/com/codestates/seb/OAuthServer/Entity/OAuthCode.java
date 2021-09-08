package com.codestates.seb.OAuthServer.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class OAuthCode {

    @Id
    private Long id;

    @Column(nullable = false, length = 100)
    private String ClientId;

    @Column(nullable = false, length = 100)
    private String ClientSecret;

    public OAuthCode(){ }

}
