package com.example.tp4camunda;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AdviceService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${advice.api.url:https://api.adviceslip.com/advice}")
    private String adviceApiUrl;

    public String getAdvice() {
        Map<?, ?> response = restTemplate.getForObject(adviceApiUrl, Map.class);
        if (response == null || !(response.get("slip") instanceof Map<?, ?> slip)
                || slip.get("advice") == null) {
            throw new IllegalStateException("Advice API returned an unexpected response");
        }
        return slip.get("advice").toString();
    }
}
