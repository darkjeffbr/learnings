# SSL Server

## Generate server keypair
```
keytool -genkey -alias localhost-server -keyalg RSA -validity 9999 -keystore server.jks
```
Password: 123456

## Generate server certificate
```
keytool -exportcert -alias localhost-server -keystore server.jks -file localhost-server.cer
```

