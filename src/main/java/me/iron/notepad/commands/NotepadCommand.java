package me.iron.notepad.commands;

import me.iron.notepad.Notepad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import me.iron.notepad.config.NotepadConfig;
import me.iron.notepad.util.chat.ChatUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;

public class NotepadCommand extends CommandBase {
    
    private final List aliases;

    private final Notepad np = new Notepad();

    public NotepadCommand() {

        this.aliases = new ArrayList();
        this.aliases.add("note");
        this.aliases.add("n");
        this.aliases.add("notepad");

    }


    public String getCommandName() {
        return "note";
    }

    public String getCommandUsage(ICommandSender sender) {
        return "note";
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public List getCommandAliases() {
        return this.aliases;
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {

            try {
                np.readPage(1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (args.length == 1) {
            if (
                    args[0].equalsIgnoreCase("removeall") ||
                    args[0].equalsIgnoreCase("deleteall") ||
                    args[0].equalsIgnoreCase("clearall") ||
                    args[0].equalsIgnoreCase("clear")
            ) {

                try {
                    np.resetFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (args[0].contains("open")) {
                try {
                    np.openFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                me.iron.notepad.Notepad.printHelp();
            } else {
                try {
                    np.writeLine(convertStringArrayToString(args, " "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("add")) {
                try {
                    np.writeLine(convertStringArrayToString(ignoreFirst(args), " "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (args.length == 2 && (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete"))) {
                try {
                    int lineNumber = Integer.parseInt(args[1]);
                    np.deleteLine(lineNumber-1);
                } catch (Exception e) {
                    me.iron.notepad.Notepad.printHelp();
                }
            } else if (args[0].equalsIgnoreCase("page")) {
                try {
                    int page = Integer.parseInt(args[1]);
                    np.readPage(page);
                } catch (NumberFormatException | IOException e) {
                    me.iron.notepad.Notepad.printHelp();
                }
            } else {
                try {
                    np.writeLine(convertStringArrayToString(args, " "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                int page = Integer.parseInt(args[0]);
                np.readPage(page);
            } catch (NumberFormatException | IOException e) {
                me.iron.notepad.Notepad.printHelp();
            }
        }

    }

    protected String[] ignoreFirst(String[] str) {
        String[] out = new String[str.length - 1];
        for (int i = 1; i < str.length; i++)
            out[i - 1] = str[i];
        return out;
    }

    private static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }

}

