package com.darkjeff.ssl.server;

public class Main {
    public static void main(String[] args) {

        Server server = new Server(31745);
        server.start();

    }
}
