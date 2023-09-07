package com.foodics.api.generate_data;

import com.github.javafaker.Faker;

import java.util.Random;
import java.util.UUID;

public final class GenerateData {
private GenerateData(){}

    public static String getEmailAddress(){
            return String.valueOf(new Faker().internet().emailAddress().replaceFirst("@.*$", "@foodics.com"));
    }

    public static String generatePassword() {
        String specialChars = "!@#$%^&*";
        String numbers = "0123456789";
        int maxLength = 8;
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        while (password.length() < maxLength) {
            int option = random.nextInt(3);

            switch (option) {
                case 0:
                    password.append((char) (random.nextInt(26) + 'a'));
                    break;
                case 1:
                    password.append((char) (random.nextInt(26) + 'A'));
                    break;
                case 2:
                    if (random.nextBoolean()) {
                        password.append(specialChars.charAt(random.nextInt(specialChars.length())));
                    } else {
                        password.append(numbers.charAt(random.nextInt(numbers.length())));
                    }
                    break;
            }
        }

        return password.toString();
    }
    public static String getToken(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
