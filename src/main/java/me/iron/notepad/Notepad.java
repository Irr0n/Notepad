package me.iron.notepad;

import me.iron.notepad.config.NotepadConfig;
import me.iron.notepad.util.chat.ChatUtil;

import me.iron.notepad.util.file.FileUtil;
import net.minecraft.util.EnumChatFormatting;
import tv.twitch.chat.Chat;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Notepad {

    FileUtil f = new FileUtil();

    ChatUtil c = new ChatUtil();

    File notesFile = new NotepadConfig().notesFile;

    Path notepadPath = new NotepadConfig().notepadPath;
    Path categoriesListPath = new NotepadConfig().categoriesListPath;
    Path categoriesPath = new NotepadConfig().categoriesPath;

    public void readPage(Path path, Integer page) throws IOException {
        try {
            int totalPages = (int)(f.readAllLines(path).size() / NotepadConfig.ENTRIES_PER_PAGE);

            if (totalPages == 0) { totalPages = 1; }

            if (page > totalPages) { page = totalPages; }

            ChatUtil.addMessage(EnumChatFormatting.DARK_GRAY, ("(Page " + page + " of " + totalPages + ")"));
            for (int i = 0; i <= NotepadConfig.ENTRIES_PER_PAGE; i++) {
                ChatUtil.addMessage(EnumChatFormatting.GRAY, f.readLine(path, (i + ((page - 1) * NotepadConfig.ENTRIES_PER_PAGE))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List readAllLines(Path path) throws IOException {
        return f.readAllLines(path);
    }

    public void deleteLine(Path path, Integer lineIndex) throws Exception {
        f.deleteLine(path, lineIndex);
    }

    public void writeLine(Path path, Integer lineIndex, String lineString) throws IOException {
        f.writeLine(path, lineIndex, lineString);
    }

    public void writeLine(Path path, String lineString) throws IOException {
        //attempt to write to +1 line out of index to write to the end of the file
        f.writeLine(path,f.readAllLines(path).size()+1, lineString);
    }

    public void readLine(Path path, Integer lineNumber) throws IOException {
        f.readLine(path, lineNumber);
    }

    public static void printHelp(Integer page) {
        if (page == 1) {
            ChatUtil.addMessage(EnumChatFormatting.DARK_GRAY, "- Notepad Usage - 1/2");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note <page> ", EnumChatFormatting.DARK_GRAY, "(Shows page)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note add <content> ", EnumChatFormatting.DARK_GRAY, "(Adds note)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note help <page>",EnumChatFormatting.DARK_GRAY, "(Shows help)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note open ", EnumChatFormatting.DARK_GRAY, "(Opens the file)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note remove | delete <line> ", EnumChatFormatting.DARK_GRAY, "(Removes note)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note removeall | deleteall | clear | clearall ", EnumChatFormatting.DARK_GRAY, "(Clears all notes)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note colors ", EnumChatFormatting.DARK_GRAY, "(Displays colors)");
        } else if (page == 2) {
            ChatUtil.addMessage(EnumChatFormatting.DARK_GRAY, "- Notepad Category Usage - 2/2");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note category ", EnumChatFormatting.DARK_GRAY, "(Shows categories)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note category <category name>", EnumChatFormatting.DARK_GRAY, "(Shows from category)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note add <category name> add <content> ", EnumChatFormatting.DARK_GRAY, "(Adds note with category)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note category add | new <category name> <color> ", EnumChatFormatting.DARK_GRAY, "(Adds category)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note category remove <category name | line> ", EnumChatFormatting.DARK_GRAY, "(Removes category)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note category <line> <category name> ", EnumChatFormatting.DARK_GRAY, "(Applies or removes category)");
            ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note category color <category name | line> <color> ", EnumChatFormatting.DARK_GRAY, "(Changes category color)");
        } else {
            printHelp(1);
        }

    }

    public void resetFile(Path path) throws IOException {
        f.clearFile(path);
    }

    public void openFile(File file) throws IOException {
        Desktop.getDesktop().open(file);
    }

    public void readCategories() throws IOException {
        ChatUtil.addMessage(EnumChatFormatting.DARK_GRAY, ("(Categories)"));
        for (int i = 0; i <= readAllLines(categoriesListPath).size(); i++) {
            try {
                String[] arrayLine = c.splitComponentString(f.readLine(categoriesListPath, i));

                ChatUtil.addMessage(c.parseColor(arrayLine[1].trim()), arrayLine[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }

        }

    }

    protected static void onError() {
        ChatUtil.addMessage(EnumChatFormatting.RED, "There was an error, please check the logs!");
        System.out.println("There was an error!");
        (new Throwable()).printStackTrace();
    }
}
