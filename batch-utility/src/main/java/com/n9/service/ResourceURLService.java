package com.n9.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResourceURLService {

    @Value("${transformTinyUrl}")
    private String transformTinyUrl;

    RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    public String proccessUrl(String tinyUrl) throws Exception {

        String responseUrl = null;

        JSONObject transformObj = new JSONObject();
        JSONObject urlObj = new JSONObject();
        urlObj.put("url", tinyUrl);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(urlObj);
        transformObj.put("urlList", jsonArray);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<String>(transformObj.toString(), headers);

        ResponseEntity<String> responseEntityStr = restTemplate.
                exchange(transformTinyUrl, HttpMethod.POST, request, String.class);

        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());
        responseUrl = root.at("/content").get(tinyUrl).asText();
        return responseUrl;

    }

}
