package me.iron.notepad.util.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatUtil {

    public final String[] colors = new String[]{
            "aqua",
            "black",
            "dark_aqua",
            "dark_blue",
            "dark_gray",
            "dark_green",
            "dark_purple",
            "dark_red",
            "gold",
            "gray",
            "green",
            "light_purple",
            "red",
            "white",
            "yellow"
    };

    public final List<String> colorsList = new ArrayList<String>(Arrays.asList(colors));

    public final String[] styles = new String[]{
            "bold",
            "italic",
            "obfuscated",
            "reset",
            "strikethrough",
            "underline"
    };

    public final List<String> stylesList = new ArrayList<String>(Arrays.asList(styles));


    public static void addMessage(EnumChatFormatting color1, String message1, EnumChatFormatting color2, String message2) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color1 + ("" + message1) + color2 + ("" + message2)));
    }

    public static void addMessage(EnumChatFormatting color, String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color + ("" + message)));
    }

    public static void addMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public EnumChatFormatting parseColor(String color) {
        switch (color) {
            case "aqua":
                return EnumChatFormatting.AQUA;
            case "black":
                return EnumChatFormatting.BLACK;
            case "dark_aqua":
                return EnumChatFormatting.DARK_AQUA;
            case "dark_blue":
                return EnumChatFormatting.DARK_BLUE;
            case "dark_gray":
                return EnumChatFormatting.DARK_GRAY;
            case "dark_green":
                return EnumChatFormatting.DARK_GREEN;
            case "dark_purple":
                return EnumChatFormatting.DARK_PURPLE;
            case "dark_red":
                return EnumChatFormatting.DARK_RED;
            case "gold":
                return EnumChatFormatting.GOLD;
            case "gray":
                return EnumChatFormatting.GRAY;
            case "green":
                return EnumChatFormatting.GREEN;
            case "light_purple":
                return EnumChatFormatting.LIGHT_PURPLE;
            case "red":
                return EnumChatFormatting.RED;
            case "white":
                return EnumChatFormatting.WHITE;
            case "yellow":
                return EnumChatFormatting.YELLOW;

            default:
                return EnumChatFormatting.RESET;
        }
    }

    public EnumChatFormatting parseStyle(String style) {
        switch (style) {
            case "bold":
                return EnumChatFormatting.BOLD;
            case "italic":
                return EnumChatFormatting.ITALIC;
            case "obfuscated":
                return EnumChatFormatting.OBFUSCATED;
            case "strikethrough":
                return EnumChatFormatting.STRIKETHROUGH;
            case "reset":
                return EnumChatFormatting.RESET;
            case "underline":
                return EnumChatFormatting.UNDERLINE;
            default:
                return EnumChatFormatting.RESET;
        }
    }

    public String[] splitComponentString(String string) {
        String[] arrayOfString = string.split(",", -1);

        return arrayOfString;
    }
}
