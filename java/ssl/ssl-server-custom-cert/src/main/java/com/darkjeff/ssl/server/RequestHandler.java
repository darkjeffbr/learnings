package com.darkjeff.ssl.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RequestHandler implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LOGGER.info("Request received");
        byte[] response = "Server says hi!".getBytes();
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200,0);
        exchange.getResponseBody().write(response);
        exchange.getResponseBody().close();
        LOGGER.info("Response sent");
    }
}
