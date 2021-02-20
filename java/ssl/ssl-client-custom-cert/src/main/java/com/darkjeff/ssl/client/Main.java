package com.darkjeff.ssl.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Client client = new Client("localhost", 31745);
        LOGGER.info(client.request());
    }
}
