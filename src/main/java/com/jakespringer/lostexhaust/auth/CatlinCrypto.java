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

    // public static void main(String[] args) throws Exception {
    // String encoded =
    // "9454824206A31304A17298A77E256A0353535A84A72701F6162BF6DCE79CC2EC69A0B946CE82BC61607A3A5345C42468DE4E6BB27F5E7C06191605910F9F62A86D9CED492B2ACF9275B229649F5FC5640EB14096250CAB94A6EE292323838951FC2FA9E24FA33382A9B76306D9F24E19865780F5BE6A5A1A30E6BB13C9FC583703D9D1BB02D0E4791CE06B9896402628C68596E2801204A0CC7C801ABFB5166C5938E182A13906B66704C4868C74ECAC9EF068C09A9FFBF012AE90EFDE5EBDCCC2D825EDDEB9E10BE1D19E542526C06C691A8838F3445A5A8F7898473812445D0EC69A0551095038DBB510E3F360D50BA9491565B27BB9230B42A120C0760B8B";
    // Key k = getMessageFromString(encoded);
    // System.out.println(k.id + " " + k.timestamp + " " + k.ip);
    // }
}