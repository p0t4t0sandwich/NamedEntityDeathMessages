package dev.neuralnexus.namedentitydeathmessages;

import com.mojang.logging.LogUtils;
import dev.dejvokep.boostedyaml.YamlDocument;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgeMain.MODID)
public class ForgeMain {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "namedentitydeathmessages";

    // Config
    public static YamlDocument config;

    // Directly reference a slf4j logger
    public static final Logger logger = LogUtils.getLogger();

    // Singleton instance
    private static ForgeMain instance;

    public static ForgeMain getInstance() {
        return instance;
    }


    public ForgeMain() {
        // Singleton instance
        instance = this;

        // Config
        try {
            config = YamlDocument.create(new File("./" + "config" + "/ForgeMain", "config.yml"),
                    Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("config.yml"))
            );
            config.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Register ourselves for server and other game events we are interested in
//        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(ForgeEventHandler.class);
    }

//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {
//        // Do something when the server starts
//        MinecraftForge.EVENT_BUS.register(ForgeEventHandler.class);
//        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
//    }
}
