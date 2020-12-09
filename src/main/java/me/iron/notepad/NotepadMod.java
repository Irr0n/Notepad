package me.iron.notepad;

import me.iron.notepad.commands.NotepadCommand;
import me.iron.notepad.config.NotepadConfig;
import me.iron.notepad.util.chat.ChatUtil;
import me.iron.notepad.util.file.FileUtil;

import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.File;
import java.io.IOException;

@Mod(modid = NotepadMod.MOD_ID, name = NotepadMod.NAME, version = NotepadMod.VERSION, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class NotepadMod
{
    public static final String MOD_ID = "notepad";
    public static final String NAME = "Notepad";
    public static final String VERSION = "1.0";


    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        new Notepad();
        new FileUtil();
        new ChatUtil();
        new NotepadConfig();

        MinecraftForge.EVENT_BUS.register(this);

        try {
            new FileUtil().findFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadCommands();
    }

    private void loadCommands() {
        ClientCommandHandler.instance.registerCommand((ICommand)new NotepadCommand());
    }
}
