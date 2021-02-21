package com.darkjeff.ssl.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        //System.setProperty("javax.net.debug","all");
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        Client client = new Client("localhost", 31745);
        LOGGER.info(client.request());
    }
}
