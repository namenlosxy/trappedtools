package com.github.namenlosxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class URLConnectionReader {
    public static List<String> downloadInformation(String role) throws IOException {
        List<String> list = new ArrayList<>();
        URL url = new URL("https://raw.githubusercontent.com/namenlosxy/playerlist/main/factions/" + role + ".list" + "?_=" + System.currentTimeMillis());
        URLConnection yc = url.openConnection();
        yc.setUseCaches(false);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            list.add(inputLine.toLowerCase());
        }
        in.close();
        return list;
    }
}
