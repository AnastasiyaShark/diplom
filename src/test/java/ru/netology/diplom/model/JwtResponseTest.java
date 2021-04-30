package ru.netology.diplom.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JwtResponseTest {

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";
    @Test
    public void testDeSerializingWithJsonCreatorJwtResponse() throws IOException {
        String jsonString = String.format("{\"auth-token\": \"%s\"}", token);

        ObjectMapper mapper = new ObjectMapper();
        JwtResponse jwtResponse = mapper.readValue(jsonString, JwtResponse.class);
        assertThat(jwtResponse.getToken(), equalTo(token));
    }
}
