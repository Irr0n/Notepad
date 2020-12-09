package me.iron.notepad.util.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtil {

    public static void addMessage(EnumChatFormatting color1, String message1, EnumChatFormatting color2, String message2) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color1 + ("" + message1) + color2 + ("" + message2)));
    }

    public static void addMessage(EnumChatFormatting color, String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color + ("" + message)));
    }

    public static void addMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }



}
