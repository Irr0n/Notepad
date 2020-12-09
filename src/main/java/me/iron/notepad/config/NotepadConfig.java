package me.iron.notepad.config;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NotepadConfig {

    public static final int ENTRIES_PER_PAGE = 5;

    public static final String NOTEPAD_FILENAME = "notepad.txt";
    public static final String CATEGORIES_FILENAME = "notepad_categories.txt";

    //public File notesFile = new File(String.valueOf(Minecraft.getMinecraft().mcDataDir + "config/" + FILENAME));
    public File notesFile = new File("config/" + NOTEPAD_FILENAME);

    public File categoriesFile = new File("config/" + CATEGORIES_FILENAME);

    //public Path path = Paths.get(String.valueOf(Minecraft.getMinecraft().mcDataDir + "config/" + FILENAME));
    public Path notepadPath = Paths.get("config/" + NOTEPAD_FILENAME);
    public Path categoriesPath = Paths.get("config/" + CATEGORIES_FILENAME);

}

