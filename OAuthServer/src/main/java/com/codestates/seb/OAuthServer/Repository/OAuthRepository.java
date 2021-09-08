package com.codestates.seb.OAuthServer.Repository;

import com.codestates.seb.OAuthServer.Entity.OAuthCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class OAuthRepository {

    private final EntityManager entityManager;

    @Autowired
    public OAuthRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional
    public OAuthCode FindUserOAuthCode(){
        return entityManager.find(OAuthCode.class, 0L);
    }

}
