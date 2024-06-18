package com.github.moruke.wall.identity.authentication.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class PasswordUtils {
    @Value("${wall.rules.password.max-length:18}")
    private int length;

    @Value("${wall.rules.password.chars:ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz}")
    private String chars;

    @Value("${wall.rules.password.numbers:0123456789}")
    private String numbers;

    @Value("${wall.rules.password.specials:!@#$%^&*()_+|~-=`{}[]:\";'<>?,./}")
    private String specials;


    public String random() {
        return RandomStringUtils.random(18, chars + numbers + specials);
    }


}
