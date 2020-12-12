package me.iron.notepad.config;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NotepadConfig {

    //TODO: Add entries per page setting to note command
    public static final int ENTRIES_PER_PAGE = 5;

    public static final String NOTEPAD_FILENAME = "notepad.txt";
    public static final String CATEGORIES_LIST_FILENAME = "notepad_categories.txt";
    public static final String CATEGORIES_FILENAME = "categories.txt";

    //public File notesFile = new File(String.valueOf(Minecraft.getMinecraft().mcDataDir + "config/" + FILENAME));
    public File notesFile = new File("config/" + NOTEPAD_FILENAME);
    public File categoriesListFile = new File("config/" + CATEGORIES_LIST_FILENAME);

    //public Path path = Paths.get(String.valueOf(Minecraft.getMinecraft().mcDataDir + "config/" + FILENAME));
    public Path notepadPath = Paths.get("config/" + NOTEPAD_FILENAME);
    public Path categoriesListPath = Paths.get("config/" + CATEGORIES_LIST_FILENAME);

}

