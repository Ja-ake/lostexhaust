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
        System.out.println("Keyfile.length = " + keyfile.length());
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

     public static void main(String[] args) throws Exception {
	     String encoded =
	    		 "A6AF0B6864542F3F9C86BA4E55EB18B5B6E220C692C8D9DDEFD8A2BB5BA9BB7270246D97BAF3F83EEF43061D9FB72DA60FD6CA81EA8051F3B0CE46E4926499AA39444EDD4C56C54D31795F46DEBB21449862B334D0BC540A21795DD58F6B2F5E7B54F4C2ED908F861598F2C19ED4A0612A5606D52CB252F525A2892CFC74FA1538A1B5D0D8A2732DD5ACE8114ABC274E25C8A2955F96EC5BE0E5234A91B609A6317233C126CBCE8942A68618DDCB99F037CAE6618D600489A36BDFCF1B80C0A4A246A0A469A38B232DE091463F38E4D46C5C84DE68C9806682FCE8B3D5320995E2D2EBBA56B52DB0657B918884A4C5DA7ECAB6CDBC6E6BDF770606D520017A91";
	     String decrypted = decryptRSA(encoded, LeWebserver.CONF_DIR + "public.der");
	     System.out.println(decrypted);
     }
     
     public static void test() {
	     String encoded =
	    		 "A6AF0B6864542F3F9C86BA4E55EB18B5B6E220C692C8D9DDEFD8A2BB5BA9BB7270246D97BAF3F83EEF43061D9FB72DA60FD6CA81EA8051F3B0CE46E4926499AA39444EDD4C56C54D31795F46DEBB21449862B334D0BC540A21795DD58F6B2F5E7B54F4C2ED908F861598F2C19ED4A0612A5606D52CB252F525A2892CFC74FA1538A1B5D0D8A2732DD5ACE8114ABC274E25C8A2955F96EC5BE0E5234A91B609A6317233C126CBCE8942A68618DDCB99F037CAE6618D600489A36BDFCF1B80C0A4A246A0A469A38B232DE091463F38E4D46C5C84DE68C9806682FCE8B3D5320995E2D2EBBA56B52DB0657B918884A4C5DA7ECAB6CDBC6E6BDF770606D520017A91";
	     String decrypted;
		try {
			decrypted = decryptRSA(encoded, LeWebserver.CONF_DIR + "public.der");
		     System.out.println(decrypted);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
}