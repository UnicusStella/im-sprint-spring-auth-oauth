package com.codestates.seb.OAuthServer.Domain.Data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Resources {
    private String file;
    private String blob;
}
