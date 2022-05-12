package com.noti.platform.first.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noti.platform.first.domain.email.request.CommonInfo;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Component
public class CloudApiHelper {
    public static CommonInfo readJsonFile() throws IOException {
        String path = System.getProperty("user.home");
        Reader reader = new FileReader(path+"/info.json");

        ObjectMapper apiInfo = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        JsonNode readApiInfo = apiInfo.readTree(reader);

        return apiInfo.readValue(readApiInfo.toString(), CommonInfo.class);
    }
}
