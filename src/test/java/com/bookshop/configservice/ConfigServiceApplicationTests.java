package com.bookshop.configservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConfigServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    @Disabled("You can enable this test if your config-repo is publicly available")
    void defaultConfigurationAvailable() {
        ResponseEntity<Environment> entity = restTemplate
                .getForEntity("/application/default", Environment.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}