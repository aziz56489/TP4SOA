package com.example.tp4camunda;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS employees (
                    id VARCHAR(36) PRIMARY KEY,
                    name VARCHAR(200) NOT NULL,
                    email VARCHAR(250) NOT NULL,
                    job_offer VARCHAR(250) NOT NULL
                )
                """);
    }

    @PostMapping
    public Map<String, String> addEmployee(@RequestBody EmployeeRequest request) {
        if (request.name() == null || request.name().isBlank()
                || request.email() == null || request.email().isBlank()
                || request.jobOffer() == null || request.jobOffer().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "name, email and jobOffer are required");
        }

        String id = UUID.randomUUID().toString();
        jdbcTemplate.update(
                "INSERT INTO employees (id, name, email, job_offer) VALUES (?, ?, ?, ?)",
                id, request.name(), request.email(), request.jobOffer());
        return Map.of("id", id, "name", request.name(), "email", request.email(),
                "jobOffer", request.jobOffer());
    }

    @GetMapping
    public List<Map<String, Object>> listEmployees() {
        return jdbcTemplate.queryForList(
                "SELECT id, name, email, job_offer AS jobOffer FROM employees ORDER BY name");
    }

    public record EmployeeRequest(String name, String email, String jobOffer) {
    }
}
