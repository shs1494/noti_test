package com.noti.platform.first.master.email.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.platform.first.common.CloudApiHelper;
import com.noti.platform.first.domain.email.request.CommonInfo;
import com.noti.platform.first.domain.email.request.RequestDTO;
import com.noti.platform.first.domain.email.response.EmailResultHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class EmailService {

    @Autowired
    private CommonInfo commonInfo;

    public EmailResultHeader emailSendFromOpenApi(RequestDTO requestDTO) throws IOException {

        ResponseEntity<String> response = postTemplateBuild(requestDTO);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        JsonNode readBody = objectMapper.readTree(response.getBody());
        readBody = readBody.get("header");

        return objectMapper.readValue(readBody.toString(), EmailResultHeader.class);
    }

    public ResponseEntity<String> postTemplateBuild(RequestDTO requestDTO) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("X-Secret-Key", CloudApiHelper.readJsonFile().getEmailSecretkey());
        headers.set("X-Secret-Key", commonInfo.getEmailSecretkey());

        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson(requestDTO.getMailType(), requestDTO.getReceiveTypes()), headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlBuild(requestDTO.getMailType()));

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, requestEntity, String.class);
    }

    public String urlBuild(String mailType) throws IOException {
        String uri = "";
        switch (mailType) {
            case "normal":
                uri = "/sender/mail";
                break;
            case "auth":
                uri = "/sender/auth-mail";
                break;
            case "tag":
                uri = "/sender/tagMail";
                break;
        }

        String url = "https://api-mail.cloud.toast.com/email/v2.0/appKeys/" + CloudApiHelper.readJsonFile().getEmailAppkey() + uri;

        return url;
    }

    public String requestJson(String mailType, List<String> receiveTypes) throws IOException {
        String jsonBody = "";
        Set<String> receiveTypeSets = new HashSet<>(receiveTypes);
        switch (mailType) {
            case "normal":
                String receiveType = "";
                if (receiveTypeSets.contains("MRT1")) {
                    receiveType += ",{\"receiveMailAddr\":\"" + CloudApiHelper.readJsonFile().getEmailAddress() + "\",\"receiveType\":\"MRT1\"}";
                }
                if (receiveTypeSets.contains("MRT2")) {
                    receiveType += ",{\"receiveMailAddr\":\"" + CloudApiHelper.readJsonFile().getEmailAddress() + "\",\"receiveType\":\"MRT2\"}";
                }
                jsonBody = "{\"senderAddress\":\"" + CloudApiHelper.readJsonFile().getEmailAddress() + "\", \"title\":\"test\",\"body\":\"일반 테스트\",\"receiverList\":["
                        + "{\"receiveMailAddr\":\"" + CloudApiHelper.readJsonFile().getEmailAddress() + "\",\"receiveType\":\"MRT0\"}"
                + receiveType + "]}";
                break;
            case "auth":
                jsonBody = "{\"senderAddress\":\"" + CloudApiHelper.readJsonFile().getEmailAddress() + "\", \"title\":\"test\",\"body\":\"인증 테스트\",\"receiver\":{\"receiveMailAddr\":\"" + CloudApiHelper.readJsonFile().getEmailAddress() + "\"}}";
                break;
            case "tag":
                jsonBody = "{\"senderAddress\":\"" + CloudApiHelper.readJsonFile().getEmailAddress() + "\", \"title\":\"test\",\"body\":\"태그 테스트\",\"receiverList\":[{\"receiveMailAddr\":\"" + CloudApiHelper.readJsonFile().getEmailAddress() + "\",\"receiveType\":\"MRT0\"}]}";
                break;
        }
        return jsonBody;
    }

}
