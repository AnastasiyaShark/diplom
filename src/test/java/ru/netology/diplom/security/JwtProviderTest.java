package ru.netology.diplom.security;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;


public class JwtProviderTest {

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";
    @Test
    public void testValidateJwtToken() {
      JwtProvider jwtProvider = new JwtProvider();
      assertTrue(jwtProvider.validateJwtToken(token));

    }

    @Test
    public void testNotValidateJwtToken() {
        JwtProvider jwtProvider = new JwtProvider();
        assertFalse(jwtProvider.validateJwtToken(token + "not"));

    }
    @Test
    public void testGetUserNameFromJwtToken() {
        JwtProvider jwtProvider = new JwtProvider();
        assertThat(jwtProvider.getUserNameFromJwtToken(token), equalTo("User2"));

    }

}