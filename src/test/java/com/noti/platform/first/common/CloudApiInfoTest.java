package com.noti.platform.first.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CloudApiInfoTest {
    @Test
    public void readJsonFile() throws IOException {
        String path = System.getProperty("user.home");
        Reader reader = new FileReader(path+"/info.json");

        ObjectMapper apiInfo = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        JsonNode readApiInfo = apiInfo.readTree(reader);

        System.out.println(readApiInfo.get("emailAppkey"));
        System.out.println(readApiInfo.get("emailSecretkey"));
    }
}
