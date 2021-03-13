package com.github.namenlosxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("trappedtools")
public class TrappedTools {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static List<String> member;
    public static LinkedHashMap<String, List<String>> roles = new LinkedHashMap<>();

    public TrappedTools() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void reloadRoles() throws IOException {


        if (!roles.isEmpty()) {
            roles.clear();
        }
        for (String elem : URLConnectionReader.downloadInformation("factions")) {
            try {
                member = URLConnectionReader.downloadInformation(elem.substring(8));
                roles.put(elem, member);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        try {
            reloadRoles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
