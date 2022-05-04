package com.noti.platform.first.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.platform.first.common.CloudApiInfo;
import com.noti.platform.first.domain.response.EmailResultHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Service
public class EmailService {

    public EmailResultHeader emailSendFromOpenApi(String typeData) throws IOException {

        ResponseEntity<String> response = postTemplateBuild(typeData);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        JsonNode readBody = objectMapper.readTree(response.getBody());
        readBody = readBody.get("header");

        return objectMapper.readValue(readBody.toString(), EmailResultHeader.class);
    }

    @Autowired
    CloudApiInfo cloudApiInfo;

    public ResponseEntity<String> postTemplateBuild(String typeData) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Secret-Key",cloudApiInfo.readJsonFile().getEmailSecretkey());

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson(typeData), headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlBuild(typeData));

        System.out.println(builder.toUriString());

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
    }

    public String urlBuild(String typeData) throws IOException {
        String uri = "";
        if (typeData.equals("normal")) {
            uri = "/sender/mail";
        } else {
            uri = "/sender/auth-mail";
        }

        String url = "https://api-mail.cloud.toast.com/email/v2.0/appKeys/" + cloudApiInfo.readJsonFile().getEmailAppkey() + uri;

        return url;
    }

    public String requestJson(String typeData) throws IOException {
        String jsonBody = "";
        if (typeData.equals("normal")) {
            jsonBody = "{\"senderAddress\":\"" + cloudApiInfo.readJsonFile().getEmailAddress() + "\", \"title\":\"test\",\"body\":\"일반 테스트\",\"receiverList\":[{\"receiveMailAddr\":\"" + cloudApiInfo.readJsonFile().getEmailAddress() + "\",\"receiveType\":\"MRT0\"}]}";
        } else {
            jsonBody= "{\"senderAddress\":\"" + cloudApiInfo.readJsonFile().getEmailAddress() + "\", \"title\":\"test\",\"body\":\"인증 테스트\",\"receiver\":{\"receiveMailAddr\":\"" + cloudApiInfo.readJsonFile().getEmailAddress() + "\",\"receiveType\":\"MRT0\"}}";
        }
        return jsonBody;
    }
}
