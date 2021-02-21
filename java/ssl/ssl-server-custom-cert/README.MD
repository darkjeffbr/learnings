# SSL Server

## Certificate chain

In order to create a certificate chain, let's first create the root key pair and certificate.
```
keytool -genkeypair -alias root -keystore root.jks -dname "CN=My-Own-CA" -storepass password -keypass password -ext bc=ca:true
```
**<sub>Note here an extension with BasicaContraint created to show that it's a CA.</sub>**  
```
keytool -exportcert -rfc -keystore root.jks -alias root -storepass password -file root.pem
```  
  
Then, generate a key pair and certificate for the intermediate CA, but this time we will sign the certificate with the root certificate.
```
keytool -genkeypair -alias intermediate -keystore intermediate.jks -dname "CN=Intermediate" -storepass password -keypass password
keytool -certreq -alias intermediate -keystore intermediate.jks -storepass password \
    | keytool -gencert -alias root -keystore root.jks -storepass password -ext bc=0 -ext san=dns:intermediate -rfc -outfile intermediate.pem
```

Finally, generate a key pair and certificate for the server, signed by the intermediate certificate.
```
keytool -genkeypair -alias server -keystore server.jks -dname "CN=server" -storepass password -keypass password
keytool -certreq -alias server -keystore server.jks -storepass password \
    | keytool -gencert -alias intermediate -keystore intermediate.jks -storepass password -ext bc=0 -ext san=dns:localhost -rfc -outfile server.pem
```

## Import certificate chain into server key store
```
keytool -importcert -keystore server.jks -alias root -storepass password -trustcacerts -noprompt -file root.pem
keytool -importcert -keystore server.jks -alias intermediate -storepass password -file intermediate.pem
keytool -importcert -keystore server.jks -alias server -storepass password -file server.pem
```

## Generate trust store
```
keytool -importcert -keystore truststore.jks -alias root -storepass password -trustcacerts -noprompt -file root.pem
keytool -importcert -keystore truststore.jks -alias intermediate -storepass password -file intermediate.pem
keytool -importcert -keystore truststore.jks -alias server -storepass password -file server.pem
```
