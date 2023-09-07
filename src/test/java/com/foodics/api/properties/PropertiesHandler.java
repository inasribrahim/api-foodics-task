package com.foodics.api.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesHandler {
    private static final String ROOT_PATH = System.getProperty("user.dir")
            .concat("/resources/config/");
    private static final String API_CONFIG_PATH=ROOT_PATH.concat("api_config.properties");
    public static Properties setAPIConfig() throws  IOException {
        Properties configProperties = new Properties();
        FileInputStream inputStream = new FileInputStream(API_CONFIG_PATH);
        configProperties.load(inputStream);
        return configProperties;
    }
}
