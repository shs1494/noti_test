package com.noti.platform.first.master.email.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.platform.first.domain.email.request.*;
import com.noti.platform.first.domain.email.response.EmailResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private CommonInfo commonInfo;

    public EmailResultInfo emailSendFromOpenApi(RequestDTO requestDTO) throws IOException {

        ResponseEntity<String> response = newPostTemplateBuild(requestDTO);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        JsonNode readBody = objectMapper.readTree(response.getBody());
//        readBody = readBody.get("header");

        return objectMapper.readValue(readBody.toString(), EmailResultInfo.class);
    }

    public ResponseEntity<String> newPostTemplateBuild(RequestDTO requestDTO) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Secret-Key", commonInfo.getEmailSecretkey());

        HttpEntity<String> requestEntity = new HttpEntity<>(emailRequestJsonInit(requestDTO),headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlBuild(requestDTO.getMailType()));

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity(builder.toUriString(), requestEntity, String.class);
    }

    public String emailRetrieveFromOpenApi(String requestId) throws IOException {

        ResponseEntity<String> response = getTemplateBuild(requestId);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        JsonNode readBody = objectMapper.readTree(response.getBody());
        String result = readBody.get("body").get("data").get("mailStatusName").toString();

        return result;
    }

    public ResponseEntity<String> getTemplateBuild(String requestId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Secret-Key", commonInfo.getEmailSecretkey());

        HttpEntity<String> requestEntitiy = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlBuild(""))
                .path("/sender/mail/").path(requestId).path("/0");
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntitiy, String.class);
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
            case "ad":
                uri = "/sender/ad-mail";
                break;
            default: uri = "";
        }

        String url = "https://api-mail.cloud.toast.com/email/v2.0/appKeys/" + commonInfo.getEmailAppkey() + uri;

        return url;
    }

    public String emailRequestJsonInit(RequestDTO requestDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (requestDTO.getMailType().equals("auth")) {
            ReceiveInfo receiveInfo = new ReceiveInfo(commonInfo.getEmailAddress());
            AuthemailRequestInfo authemailRequestInfo = AuthemailRequestInfo.builder()
                    .senderAddress(commonInfo.getEmailAddress())
                    .title("Test")
                    .body(requestDTO.getMailType()+" 발송 테스트")
                    .receiver(receiveInfo)
                    .build();
            return objectMapper.writeValueAsString(authemailRequestInfo);
        } else {
            String subTitle = "";
            if (requestDTO.getMailType().equals("ad")) {
                subTitle = "(광고)";
            }
            List<ReceiveInfo> receiverList = new ArrayList<>();
            receiverList.add(new ReceiveInfo(commonInfo.getEmailAddress(), "MRT0"));

            for (int i = 0;i<requestDTO.getReceiveTypes().size();i++) {
                receiverList.add(new ReceiveInfo(commonInfo.getEmailAddress(), requestDTO.getReceiveTypes().get(i)));
            }
            EmailRequestInfo emailRequestInfo = EmailRequestInfo.builder()
                    .senderAddress(commonInfo.getEmailAddress())
                    .title(subTitle+"Test")
                    .body(requestDTO.getMailType()+" 발송 테스트")
                    .receiverList(receiverList)
                    .build();
            return objectMapper.writeValueAsString(emailRequestInfo);
        }
    }
}
