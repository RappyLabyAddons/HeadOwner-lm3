package com.rappytv.headowner;

import com.rappytv.headowner.events.KeyInputEvent;
import com.rappytv.headowner.modules.HeadModule;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class HeadOwnerAddon extends LabyModAddon {

    public static String prefix = "";
    public static boolean enabled = true;
    public static int length = 10;
    public static int copyKey = Keyboard.KEY_K;

    public static HeadOwnerAddon instance;

    @Override
    public void onEnable() {
        instance = this;
        getApi().registerModule(new HeadModule());
        getApi().registerForgeListener(new KeyInputEvent());
    }

    @Override
    public void loadConfig() {
        enabled = getConfig().has("enabled") ? getConfig().get("enabled").getAsBoolean() : enabled;
        length = getConfig().has("length") ? getConfig().get("length").getAsInt() : length;
        copyKey = getConfig().has("copyKey") ? getConfig().get("copyKey").getAsInt() : copyKey;
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        BooleanElement enabledEl = new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), new Consumer<Boolean>() {

            @Override
            public void accept(Boolean value) {
                enabled = value;

                getConfig().addProperty("enabled", enabled);
                saveConfig();
            }
        }, enabled);

        NumberElement lengthEl = new NumberElement("Block reach distance", new ControlElement.IconData(Material.MAP), length)
                .addCallback((integer) -> {
                    length = integer;

                    getConfig().addProperty("length", length);
                    saveConfig();
                });

        KeyElement copykeyEl = new KeyElement("Copy head data to clipboard", new ControlElement.IconData(Material.COAL), copyKey, new Consumer<Integer>() {

            @Override
            public void accept(Integer value) {
                copyKey = value;

                getConfig().addProperty("copyKey", copyKey);
                saveConfig();
            }
        });

        list.add(enabledEl);
        list.add(lengthEl);
        list.add(copykeyEl);
    }
}
