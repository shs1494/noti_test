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
import org.springframework.web.util.UriComponents;
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

        RestTemplate restTemplate = new RestTemplate();

//        return restTemplate.postForEntity(builder.toUriString(), requestEntity, String.class);
        return restTemplate.postForEntity(newUrlBuilder(requestDTO.getMailType(), requestDTO.isEachType()), requestEntity, String.class);
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
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(newUrlBuilder("mail",false))
                .path("/"+requestId).path("/0")
                .build();

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(builder.toString(), HttpMethod.GET, requestEntitiy, String.class);
    }

    public String newUrlBuilder(String mailType, boolean eachType) {
        if (eachType) {
            if (mailType.equals("mail")) {
                mailType = "eachMail";
            }
            else {
                mailType = "ad-eachMail";
            }
        }
        UriComponents builder = UriComponentsBuilder
                .fromUriString("https://api-mail.cloud.toast.com/email/v2.0/appKeys/")
                .path(commonInfo.getEmailAppkey())
                .path("/sender/")
                .path(mailType)
                .build();

        return builder.toString();
    }

    public String emailRequestJsonInit(RequestDTO requestDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (requestDTO.getMailType().equals("auth-mail")) {
//            ReceiveInfo receiveInfo = new ReceiveInfo(commonInfo.getEmailAddress());
            ReceiveInfo receiveInfo = new ReceiveInfo(requestDTO.getMailAddressList().get(0));
            AuthemailRequestInfo authemailRequestInfo = AuthemailRequestInfo.builder()
                    .senderAddress(commonInfo.getEmailAddress())
                    .title("Test")
                    .body(requestDTO.getMailType()+" ?????? ?????????")
                    .receiver(receiveInfo)
                    .build();

            return objectMapper.writeValueAsString(authemailRequestInfo);
        } else {
            String subTitle = "";
            if (requestDTO.getMailType().equals("ad-mail")) {
                subTitle = "(??????)";
            }
            List<ReceiveInfo> receiverList = new ArrayList<>();
            receiverList.add(new ReceiveInfo(requestDTO.getMailAddressList().get(0)));

            for (int i = 0;i<requestDTO.getReceiveTypes().size();i++) {
                System.out.println(requestDTO.getMailAddressList().get(i+1));
                System.out.println(requestDTO.getReceiveTypes().get(i));
                receiverList.add(new ReceiveInfo(requestDTO.getMailAddressList().get(i+1), requestDTO.getReceiveTypes().get(i)));
            }

            EmailRequestInfo emailRequestInfo = EmailRequestInfo.builder()
                    .senderAddress(commonInfo.getEmailAddress())
                    .title(subTitle+"Test")
                    .body(requestDTO.getMailType()+" ?????? ?????????")
                    .receiverList(receiverList)
                    .build();

            return objectMapper.writeValueAsString(emailRequestInfo);
        }
    }
}
