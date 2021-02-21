package com.darkjeff.ssl.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private static String PWD = "password";
    private static String TST_LOCATION = "./ssl-client-custom-cert/truststore.jks";

    private HttpsURLConnection connection;

    public Client(String serverAddr, int port) {
        LOGGER.info("Creating client instance ....");
        try {
            URL url = new URL("https://" + serverAddr + ":" + port + "/");
            connection = (HttpsURLConnection) url.openConnection();
            SSLContext sslContext = createSSLContext();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
        } catch (IOException e) {
            LOGGER.error("Couldn't create client.", e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Server instance created!");
    }

    public String request() {
        LOGGER.info("Firing request ....");
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            while ((input = br.readLine()) != null) {
                sb.append(input);
            }
            br.close();
        } catch (IOException e) {
            LOGGER.error("Couldn't get response from server.", e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Got response!");
        return sb.toString();
    }

    private SSLContext createSSLContext() {
        LOGGER.info("Creating SSLContext ....");
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            KeyStores keys = new KeyStores(null, null, TST_LOCATION, PWD);
            sslContext.init(null, keys.getTrustManagers(), null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("SSLContext created!");
        return sslContext;
    }

}
