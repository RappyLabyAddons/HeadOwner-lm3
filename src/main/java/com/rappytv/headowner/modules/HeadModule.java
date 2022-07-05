package com.rappytv.headowner.modules;

import com.rappytv.headowner.util.Util;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class HeadModule extends SimpleModule {

    @Override
    public String getDisplayName() {
        return "HeadOwner";
    }

    @Override
    public String getDisplayValue() {
        Util.Skull skull = Util.getSkullLooking();
        return skull.getDisplay();
    }

    @Override
    public String getDefaultValue() {
        return "?";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.SKULL_ITEM);
    }

    @Override
    public void loadSettings() {
    }

    @Override
    public String getSettingName() {
        return "HeadOwner";
    }

    @Override
    public String getDescription() {
        return "Shows the owner of the head you're looking at";
    }

    @Override
    public int getSortingId() {
        return 0;
    }

    @Override
    public ModuleCategory getCategory(){
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }

    @Override
    public String getControlName() {
        return "HeadOwner";
    }

    @Override
    public boolean isShown(){
        Util.Skull skull = Util.getSkullLooking();

        return skull.isShown();
    }
}
