package com.github.namenlosxy;

import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class TrappedTools implements ModInitializer {
    public static List<String> member;

    public static LinkedHashMap<String, List<String>> roles = new LinkedHashMap<>();

    public static void reloadRoles() throws IOException {


            if (!roles.isEmpty()) {
                roles.clear();
            }
            for(String elem : URLConnectionReader.downloadInformation("factions")) {
                try {
                member = URLConnectionReader.downloadInformation(elem.substring(8));
                roles.put(elem,member);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void onInitialize() {
        try {
            reloadRoles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
