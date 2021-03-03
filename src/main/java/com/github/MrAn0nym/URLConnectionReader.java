package com.github.MrAn0nym;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class URLConnectionReader {
    public static List<String> downloadinformation(String role) throws IOException {
        List<String> list = new ArrayList<>();
        list.clear();
        URL url = new URL("https://gist.githubusercontent.com/MrAn0nym/baa4fae05f0e4a3b9cbe1d5aceeb50be/raw/" + role + ".list" + "?_=" + System.currentTimeMillis());
        URLConnection yc = url.openConnection();
        yc.setUseCaches(false);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine != null) {
                list.add(inputLine.toLowerCase());
            }
        }
        in.close();
        return list;
    }
}
