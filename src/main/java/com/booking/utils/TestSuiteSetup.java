package com.booking.utils;

import org.testng.annotations.BeforeClass;

public class TestSuiteSetup {
    public static String accessToken;

    @BeforeClass
    public void suiteSetup() {
        System.out.println("Before Class " );
        accessToken = GenerateToken.generateAccessToken();
        System.out.println("Access Token: " + accessToken);
    }
}
