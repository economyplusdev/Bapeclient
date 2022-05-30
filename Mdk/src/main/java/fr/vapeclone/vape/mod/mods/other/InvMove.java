package fr.vapeclone.vape.mod.mods.other;

import org.lwjgl.input.Keyboard;

import fr.vapeclone.vape.mod.Category;
import fr.vapeclone.vape.mod.Mod;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

public class InvMove extends Mod{
	
	public InvMove() {
		super("InvMove", "Move while being in inventory", Category.OTHER);
		this.setKey(Keyboard.KEY_NONE);
	}
	
	@Override
	public void update() {
		if(mc.currentScreen == null)return;
		if(mc.currentScreen instanceof GuiChat)return;
		 KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
         EntityPlayerSP var1;
         if (Keyboard.isKeyDown(208) && mc.thePlayer.rotationPitch < 90.0F) {
            var1 = mc.thePlayer;
            var1.rotationPitch += 6.0F;
         }

         if (Keyboard.isKeyDown(200) && mc.thePlayer.rotationPitch > -90.0F) {
            var1 = mc.thePlayer;
            var1.rotationPitch -= 6.0F;
         }

         if (Keyboard.isKeyDown(205)) {
            var1 = mc.thePlayer;
            var1.rotationYaw += 6.0F;
         }

         if (Keyboard.isKeyDown(203)) {
            var1 = mc.thePlayer;
            var1.rotationYaw -= 6.0F;
         }
	}
}
