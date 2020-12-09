package me.iron.notepad.config;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NotepadConfig {

    public static final int ENTRIES_PER_PAGE = 5;

    public static final String FILENAME = "notepad.txt";

    //public File notesFile = new File(String.valueOf(Minecraft.getMinecraft().mcDataDir + "config/" + FILENAME));
    public File notesFile = new File("config/" + FILENAME);

    //public Path path = Paths.get(String.valueOf(Minecraft.getMinecraft().mcDataDir + "config/" + FILENAME));
    public Path path = Paths.get("config/" + FILENAME);

}

