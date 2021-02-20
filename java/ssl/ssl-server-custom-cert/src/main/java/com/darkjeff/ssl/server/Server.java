package com.darkjeff.ssl.server;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static String SERVER_PWD = "123456";
    private static String KST_LOCATION = "./ssl-server-custom-cert/server.jks";

    private HttpsServer httpsServer;
    private int port;

    public Server(int port){
        LOGGER.info("Creating server instance ....");
        this.port = port;
        try {
            httpsServer = HttpsServer.create(new InetSocketAddress(port), 0);
            httpsServer.setHttpsConfigurator(new HttpsConfigurator(createSSLContext()));
            httpsServer.createContext("/", new RequestHandler());
        } catch (IOException e) {
            LOGGER.error("Server couldn't start : ", e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Server instance created!");
    }

    public void start(){
        LOGGER.info("Starting server ....");
        httpsServer.start();
        LOGGER.info("Server is up on port {} !", port);
    }

    private SSLContext createSSLContext(){
        SSLContext sslContext;
        LOGGER.info("Creating SSLContext ....");
        try {
            sslContext = SSLContext.getInstance("TLS");
            KeyStores keys = new KeyStores(KST_LOCATION, SERVER_PWD, null, null);
            sslContext.init(keys.getKeyManagers(), null, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            LOGGER.error("Couldn't create SSLContext : ", e);
            throw new RuntimeException(e);
        }
        LOGGER.info("SSLContext created!");
        return sslContext;
    }

}
