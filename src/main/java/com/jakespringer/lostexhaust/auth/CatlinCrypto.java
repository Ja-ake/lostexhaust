package com.jakespringer.lostexhaust.auth;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

import com.jakespringer.lostexhaust.LeWebserver;

public class CatlinCrypto {
    public static class Key {
        public String id;
        public long timestamp;
        public String ip;

        public Key(String _id, long _timestamp, String _ip) {
            id = _id;
            timestamp = _timestamp;
            ip = _ip;
        }
    }

    /**
     * Decrypts a string that was encrypted with an RSA private key.
     *
     * @param encoded
     *            the encrypted RSA message, in hex
     * @param publicKeyFileName
     *            filename of the public key, which must be in DER format
     * @return the decrytped string
     *
     * @author Andrew Merrill
     */
    public static String decryptRSA(String encoded, String publicKeyFileName) throws Exception {
        byte[] encoded_bytes = DatatypeConverter.parseHexBinary(encoded);

        File keyfile = new File(publicKeyFileName);
        DataInputStream dis = new DataInputStream(new FileInputStream(keyfile));
        byte[] keyBytes = new byte[(int) keyfile.length()];
        dis.readFully(keyBytes);
        dis.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(spec);

        Cipher rsa = Cipher.getInstance("RSA");
        rsa.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] plainBytes = rsa.doFinal(encoded_bytes);
        return new String(plainBytes);
    }

    private static Pattern _ipPattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    public static Key getMessageFromString(String encrypted) {
        try {
            String decrypted = decryptRSA(encrypted, LeWebserver.CONF_DIR + "public.der");
            String[] parts = decrypted.split("\\|");
            if (parts.length != 3)
                return null;
            String id = parts[0];
            long timestamp;
            try {
                timestamp = Long.parseLong(parts[1]);
            } catch (NumberFormatException e) {
                return null;
            }
            String ip = parts[2];
            if (!_ipPattern.matcher(ip).matches()) {
                return null;
            }

            return new Key(id, timestamp, ip);
        } catch (Exception e) {
            return null;
        }
    }
}