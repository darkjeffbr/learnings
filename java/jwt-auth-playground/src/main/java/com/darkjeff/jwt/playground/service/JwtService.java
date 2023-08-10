package com.darkjeff.jwt.playground.service;

import com.darkjeff.jwt.playground.model.entity.User;
import lombok.SneakyThrows;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String PRIVATE_KEY_PATH = "classpath:security/jwt-sign";
    private final String PUBLIC_KEY_PATH = "classpath:security/jwt-sign.pub";

    private final String JWT_ISSUER = "jwt-auth-playground";
    private final String JWT_SUBJECT = "user";
    private final String JWT_CLAIM_EMAIL = "email";
    private final String JWT_CLAIM_ROLES = "roles";
    private final float JWT_EXPIRATION_MINUTES = 10;

    @SneakyThrows
    public String generateToken(User user) {
        // Generate an RSA key pair
        RsaJsonWebKey rsaJsonWebKey = generateWebKey();

        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer(JWT_ISSUER);  // who creates the token and signs it
        // claims.setAudience(user.getName()); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(JWT_EXPIRATION_MINUTES); // time when the token will expire
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(JWT_SUBJECT); // the subject/principal is whom the token is about
        claims.setClaim(JWT_CLAIM_EMAIL, user.getEmail()); // additional claims/attributes about the subject can be added
        List<String> groups = user.getRoles().stream().map(ur -> ur.getRole().toString()).collect(Collectors.toList());
        claims.setStringListClaim(JWT_CLAIM_ROLES, groups); // multivalued claims work too and will end up as a JSON array

        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // Here it is a JWS, so we create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(rsaJsonWebKey.getPrivateKey());

        // Set the Key ID (kid) header because it's just the polite thing to do.
        // We only have one key in this example but a using a Key ID helps
        // facilitate a smooth key rollover process
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
        // representation, which is a string consisting of three dot ('.') separated
        // base64url-encoded parts in the form Header.Payload.Signature
        // If you wanted to encrypt it, you can simply set this jwt as the payload
        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
        String jwt = jws.getCompactSerialization();
        return jwt;
    }

    @SneakyThrows
    private RsaJsonWebKey generateWebKey() {
        File privateKeyResource = ResourceUtils.getFile(PRIVATE_KEY_PATH);

        // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);

        // Give the JWK a Key ID (kid)
        rsaJsonWebKey.setKeyId("kid-jwt-sign");

        //
        String privateKeyContent = new String(Files.readAllBytes(privateKeyResource.toPath()));

        privateKeyContent = privateKeyContent
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----BEGIN OPENSSH PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----END OPENSSH PRIVATE KEY-----", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(privateKeyContent.getBytes());
        PrivateKey privateKey = kf.generatePrivate(keySpecPKCS8);

        rsaJsonWebKey.setPrivateKey(privateKey);
        return rsaJsonWebKey;
    }

    @SneakyThrows
    public boolean validateToken(String token) {
        // Generate an RSA key pair
        RsaJsonWebKey rsaJsonWebKey = generateWebKey();

        // Validate and process the JWT.
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer(JWT_ISSUER) // whom the JWT needs to have been issued by
                //.setExpectedAudience("Audience") // to whom the JWT is intended for
                .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
                .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                        AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256) // which is only RS256 here
                .build(); // create the JwtConsumer instance

        try {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
            System.out.println("JWT validation succeeded! " + jwtClaims);
            return true;
        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            System.out.println("Invalid JWT! " + e);

            // Programmatic access to (some) specific reasons for JWT invalidity is also possible
            // should you want different error handling behavior for certain conditions.

            // Whether the JWT has expired being one common reason for invalidity
            if (e.hasExpired()) {
                System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
            }

            // Or maybe the audience was invalid
            if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
                System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
            }
        }
        return false;
    }

}
