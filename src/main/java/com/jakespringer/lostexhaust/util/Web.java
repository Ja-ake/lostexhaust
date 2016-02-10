package com.jakespringer.lostexhaust.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import jetbrick.io.IoUtils;

public class Web {
    public static String getRequest(String urlToRead) throws IOException {
        // System.out.println("[GET-REQUEST] Requesting " + urlToRead + ".");

        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        }
    }

    public static byte[] getRequestBytes(String urlToRead) throws IOException {
        // System.out.println("[GET-REQUEST] Requesting " + urlToRead + ".");

        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return IoUtils.toByteArray(conn.getInputStream());
    }
}
