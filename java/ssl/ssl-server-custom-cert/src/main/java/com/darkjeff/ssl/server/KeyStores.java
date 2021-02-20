package com.darkjeff.ssl.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;

public class KeyStores {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyStores.class);

    private KeyManagerFactory kmf;
    private TrustManagerFactory tmf;

    public KeyStores(String keyStoreLocation, String keyStorePasswd, String trustStoreLocation, String trustStorePasswd){
        LOGGER.info("Loading secret keys...");
        try {
            if(Objects.nonNull(keyStoreLocation)) {
                KeyStore ks = KeyStore.getInstance("JKS");
                ks.load(new FileInputStream(keyStoreLocation), keyStorePasswd.toCharArray());
                kmf = KeyManagerFactory.getInstance("SunX509");
                kmf.init(ks, keyStorePasswd.toCharArray());
            }
            if(Objects.nonNull(trustStoreLocation)) {
                KeyStore ts = KeyStore.getInstance("JKS");
                ts.load(new FileInputStream(trustStoreLocation), trustStorePasswd.toCharArray());
                tmf = TrustManagerFactory.getInstance("SunX509");
                tmf.init(ts);
            }
        }catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException e){
            LOGGER.error("Couldn't get secret key. ", e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Secret keys loaded");
    }

    public KeyManager[] getKeyManagers(){
        if(Objects.isNull(kmf)){
            throw new IllegalStateException("KeyManagerFactory was not properly initialized");
        }
        return kmf.getKeyManagers();
    }
    public TrustManager[] getTrustManagers(){
        if(Objects.isNull(tmf)){
            throw new IllegalStateException("TrustManagerFactory was not properly initialized");
        }
        return tmf.getTrustManagers();
    }

}

