package me.iron.notepad;

import me.iron.notepad.config.NotepadConfig;
import me.iron.notepad.util.chat.ChatUtil;

import me.iron.notepad.util.file.FileUtil;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Notepad {

    public List notes = new ArrayList();

    FileUtil f = new FileUtil();

    File notesFile = new NotepadConfig().notesFile;

    Path notepadPath = new NotepadConfig().notepadPath;

    public void readPage(Integer page) throws IOException {
        try {
            int totalPages = (int)(f.readAllLines(notepadPath).size() / NotepadConfig.ENTRIES_PER_PAGE);

            if (totalPages == 0) { totalPages = 1; }

            if (page > totalPages) { page = totalPages; }

            ChatUtil.addMessage(EnumChatFormatting.DARK_GRAY, ("(Page " + page + " of " + totalPages + ")"));
            for (int i = 0; i <= NotepadConfig.ENTRIES_PER_PAGE; i++) {
                ChatUtil.addMessage(EnumChatFormatting.GRAY, f.readLine(notepadPath, (i + ((page - 1) * NotepadConfig.ENTRIES_PER_PAGE))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteLine(Integer lineNumber) throws Exception {
        f.deleteLine(notepadPath, lineNumber);
    }

    public void writeLine(Integer lineNumber, String lineString) throws IOException {
        f.writeLine(notepadPath, lineNumber, lineString);
    }

    public void writeLine(String lineString) throws IOException {
        //attempt to write to +1 line out of index to write to the end of the file
        f.writeLine(notepadPath,f.readAllLines(notepadPath).size()+1, lineString);
    }

    public void readLine(Integer lineNumber) throws IOException {
        f.readLine(notepadPath, lineNumber);
    }

    public static void printHelp() {
        String[] info = { EnumChatFormatting.GRAY + " /note <page> " + EnumChatFormatting.DARK_GRAY + "(Shows page)", EnumChatFormatting.GRAY + " /note add <content> " + EnumChatFormatting.DARK_GRAY + "(Adds note)", EnumChatFormatting.GRAY + " /note help " + EnumChatFormatting.DARK_GRAY + "(Shows help)", EnumChatFormatting.GRAY + " /note open " + EnumChatFormatting.DARK_GRAY + "(Opens the file)", EnumChatFormatting.GRAY + " /note remove | delete <line> " + EnumChatFormatting.DARK_GRAY + "(Removes note)", EnumChatFormatting.GRAY + " /note removeall | deleteall | clear | clearall " + EnumChatFormatting.DARK_GRAY + "(Clears all notes)" };

        ChatUtil.addMessage(EnumChatFormatting.GRAY, "- Notepad Usage -");
        ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note <page> ", EnumChatFormatting.DARK_GRAY, "(Shows page)");
        ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note add <content> ", EnumChatFormatting.DARK_GRAY, "(Adds note)");
        ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note help ",EnumChatFormatting.DARK_GRAY, "(Shows help)");
        ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note open ", EnumChatFormatting.DARK_GRAY, "(Opens the file)");
        ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note remove | delete <line> ", EnumChatFormatting.DARK_GRAY, "(Removes note)");
        ChatUtil.addMessage(EnumChatFormatting.GRAY, " /note removeall | deleteall | clear | clearall ", EnumChatFormatting.DARK_GRAY, "(Clears all notes)");

    }

    public void resetFile() throws IOException {
        f.clearFile(notepadPath);
    }

    public void openFile() throws IOException {
        Desktop.getDesktop().open(notesFile);
    }

    protected static void onError() {
        ChatUtil.addMessage(EnumChatFormatting.RED, "There was an error, please check the logs!");
        System.out.println("There was an error!");
        (new Throwable()).printStackTrace();
    }
}
