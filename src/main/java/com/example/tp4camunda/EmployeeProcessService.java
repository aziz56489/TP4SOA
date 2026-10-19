package com.example.tp4camunda;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeProcessService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${employee.api.url:http://localhost:8080/api/employees}")
    private String employeeApiUrl;

    public String saveEmployee(String name, String email, String jobOffer) {
        Map<?, ?> savedEmployee = restTemplate.postForObject(
                employeeApiUrl,
                Map.of("name", name, "email", email, "jobOffer", jobOffer),
                Map.class);
        if (savedEmployee == null || savedEmployee.get("id") == null) {
            throw new IllegalStateException("Employee API did not return an employee id");
        }
        return savedEmployee.get("id").toString();
    }
}
