package com.github.MrAn0nym;

import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class NexusClient implements ModInitializer {
    public static List<String> member;

    public static LinkedHashMap<String, List<String>> roles = new LinkedHashMap<>();

    public static void reloadroles() {
        try {
            if (!roles.isEmpty()) {
                roles.clear();
            }
            URLConnectionReader.downloadinformation("roles").forEach(s -> {
                try {
                    member = URLConnectionReader.downloadinformation(s.substring(9));
                    roles.put(s, member);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Loaded namecolors");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitialize() {
        reloadroles();
    }
}
