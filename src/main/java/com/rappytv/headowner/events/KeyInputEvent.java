package com.rappytv.headowner.events;

import com.rappytv.headowner.HeadOwnerAddon;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;

import com.rappytv.headowner.util.Util;
import net.labymod.main.LabyMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeyInputEvent {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (HeadOwnerAddon.copyKey != -1) {
            if (HeadOwnerAddon.enabled && Keyboard.isKeyDown(HeadOwnerAddon.copyKey)) {
                Util.Skull skull = Util.getSkullLooking();
                String name = skull.getCopy();
                StringSelection stringSelection = new StringSelection(name);

                try {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, (ClipboardOwner) null);
                } catch (IllegalStateException e) {
                    Thread t = new Thread(() -> {
                        try {
                            this.wait(100L);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(stringSelection, (ClipboardOwner) null);
                        } catch (InterruptedException | IllegalStateException ex) {
                            ex.printStackTrace();
                            LabyMod.getInstance().getLabyModAPI().displayMessageInChat("[HeadOwner] Can't modify clipboard :/");
                        }
                    });
                    t.start();
                }
            }

        }
    }
}
