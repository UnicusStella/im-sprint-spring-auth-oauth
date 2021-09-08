package com.codestates.seb.OAuthServer.Controller;

import com.codestates.seb.OAuthServer.Domain.CallBackAuthorization;
import com.codestates.seb.OAuthServer.Domain.CallBackToken;
import com.codestates.seb.OAuthServer.Domain.Data.Resources;
import com.codestates.seb.OAuthServer.Domain.Data.ResourcesData;
import com.codestates.seb.OAuthServer.Domain.Token;
import com.codestates.seb.OAuthServer.Domain.UserData;
import com.codestates.seb.OAuthServer.Repository.OAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class OAuthController {

    private final OAuthRepository oAuthRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public OAuthController(OAuthRepository oAuthRepository){
        this.oAuthRepository = oAuthRepository;
    }

    @PostMapping(value = "/callback")
    public ResponseEntity<?> PostCallBack(@RequestBody(required = true)CallBackAuthorization authorization){
        try{
            CallBackToken callBackToken = new CallBackToken();
            // Authorization Code와 DB에 있는 User 데이터를 사용하여 토큰을 받아옵니다.
            // [post] Git URL : https://github.com/login/oauth/access_token
            // TODO :
            UserData userData = null; // Git URL에 전달할 데이터 객체를 생성합니다.

            Token token = null; //restTemplate을 사용하여 Git URL에 post 요청을 보냅니다.

            if(token != null){
                callBackToken.setAccessToken(token.getAccess_token());
            }
            return ResponseEntity.ok().body(callBackToken);
        }catch (Exception error){
            return ResponseEntity.badRequest().body("Not found!");
        }
    }

    @GetMapping(value = "/images")
    public ResponseEntity<?> GetImageData(@RequestHeader Map<String, String> header){
        // 헤더에 authorization 값을 확인하여 정보가 있다면 이미지 경로 데이터를 응답합니다.
        // TODO :
        if(){ // 헤더에 내용을 검증하는 내용을 조건을 작성합니다.
            return ResponseEntity.ok()
                    .body(new HashMap<String, ArrayList<Resources>>(){{put("images", ResourcesData.getInstance().getResourcesList());}});
        }else{
            return ResponseEntity.badRequest().body(new HashMap<String, String>(){{put("message", "no permission to access resources");}});
        }
    }
}
