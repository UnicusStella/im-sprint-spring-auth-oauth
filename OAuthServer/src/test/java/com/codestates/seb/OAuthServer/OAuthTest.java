package com.codestates.seb.OAuthServer;

import com.codestates.seb.OAuthServer.CodeStatesSubmit.Submit;
import com.codestates.seb.OAuthServer.Domain.Data.Resources;
import com.codestates.seb.OAuthServer.Domain.Data.ResourcesData;
import com.codestates.seb.OAuthServer.Domain.ImagesData;
import com.codestates.seb.OAuthServer.Domain.UserData;
import com.codestates.seb.OAuthServer.Entity.OAuthCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Objects;

@AutoConfigureMockMvc
@SpringBootTest
public class OAuthTest {

    private static Submit submit = new Submit();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EntityManager entityManager;

    private RestTemplate restTemplate = new RestTemplate();

    @AfterAll
    static void after() throws Exception {
        submit.SubmitJson("im-sprint-spring-token", 2);
        submit.ResultSubmit();
    }

    @BeforeEach
    public void beforEach() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();

        objectMapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .build();
    }

    @Test
    @DisplayName(value = "github에서 accessToken을 받아 올 수 있어야합니다.")
    void PostCallBackTest() throws Exception{
        MvcResult result = null;
        String url = "/callback";
        String standard = "{\"accessToken\":null}";

        OAuthCode oAuthCode = entityManager.find(OAuthCode.class, 0L);

        UserData userdata = new UserData(oAuthCode.getClientId(), oAuthCode.getClientSecret(),"null data");

        try{
            String content = objectMapper.writeValueAsString(userdata);
            result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            submit.ResultSave(result.getResponse().getContentAsString().equals(standard));
        }catch (Exception e){
            System.out.println(e);
        }finally {
            Assertions.assertEquals(result.getResponse().getContentAsString(),standard);
        }
    }

    @Test
    @DisplayName(value = "Header에 authorization 키에 값이 전달이 되면 리소스 데이터를 리턴합니다.")
    void GetImageDataTest() throws Exception{
        MvcResult result = null;
        String url = "/images";
        ArrayList<Resources> standard = ResourcesData.getInstance().getResourcesList();
        ImagesData imagesData = null;

        try{
            result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .header("authorization", "test-data")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            imagesData = objectMapper.readValue(result.getResponse().getContentAsString(), ImagesData.class);
            submit.ResultSave(Objects.equals(standard.get(0).getFile(), imagesData.getImages().get(0).getFile()));
        }catch (Exception e){
            System.out.println(e);
        }finally {
            Assertions.assertEquals(standard.get(0).getFile(),imagesData.getImages().get(0).getFile());
        }
    }

}
