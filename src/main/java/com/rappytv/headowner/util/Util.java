package com.rappytv.headowner.util;

import com.mojang.authlib.properties.Property;
import com.rappytv.headowner.HeadOwnerAddon;
import net.labymod.core.LabyModCore;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Keyboard;

import java.util.Iterator;
import java.util.UUID;

public class Util {

    public static Skull getSkullLooking(){
        TileEntity tileEntity = getTileEntityLooking();

        return new Skull(tileEntity);
    }

    private static TileEntity getTileEntityLooking(){
        try {
            if(LabyModCore.getMinecraft().getPlayer() == null) return null;
            RayTraceResult movingObjectPosition = LabyModCore.getMinecraft().getPlayer().rayTrace(HeadOwnerAddon.length, 1.0F);
            if (movingObjectPosition == null){
                return null;
            }

            BlockPos blockPos = movingObjectPosition.getBlockPos();
            if (blockPos == null){
                return null;
            }

            return LabyModCore.getMinecraft().getWorld().getTileEntity(blockPos);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static class Skull {

        private int type = -1;
        private String username = null;
        private UUID uuid = null;
        private String value = null;

        public Skull(TileEntity tileEntity){
            if(!(tileEntity instanceof TileEntitySkull))
                return;

            TileEntitySkull tileEntitySkull = (TileEntitySkull) tileEntity;
            this.type = tileEntitySkull.getSkullType();

            if(tileEntitySkull.getPlayerProfile() != null){
                this.username = tileEntitySkull.getPlayerProfile().getName();
                this.uuid = tileEntitySkull.getPlayerProfile().getId();

                Iterator<Property> propertyIterator = tileEntitySkull.getPlayerProfile().getProperties().get("textures").iterator();
                while(propertyIterator.hasNext()){
                    this.value = propertyIterator.next().getValue();
                }
            }
        }

        private String getSkullTypeName(){
            switch (this.type){
                case 0:
                    return "Skeleton Skull";
                case 1:
                    return "Wither Skeleton Skull";
                case 2:
                    return "Zombie Head";
                case 3:
                    return "Player Head";
                case 4:
                    return "Creeper Head";
                case 5:
                    return "Dragon Head";
                default:
                    return "Unknown Skull Type: " + this.type;
            }
        }

        public String getDisplay(){
            if(this.username != null)
                return this.username;

            if(this.value != null){
                if(HeadOwnerAddon.copyKey != -1)
                    return "Unknown Head (created by texture value, press " + Keyboard.getKeyName(HeadOwnerAddon.copyKey) + " to copy skull data)";

                return "Unknown Head (created by texture value)";
            }

            return getSkullTypeName();
        }

        public String getCopy(){
            String uuid = this.uuid == null ? "Unknown" : this.uuid.toString();
            String username = this.username == null ? "Unknown" : this.username;
            String value = this.value == null ? "Unknown" : this.value;
            String type = getSkullTypeName();

            return "Skull type: " + type + " (" + this.type + ")" +
                    ", " +
                    "Username: " + username +
                    ", " +
                    "UUID: " + uuid +
                    ", " +
                    "Texture value: " + value;
        }

        public boolean isShown() {
            return (type != -1);
        }
    }
}
