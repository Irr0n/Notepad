package me.iron.notepad.commands;

import me.iron.notepad.Notepad;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import me.iron.notepad.config.NotepadConfig;
import me.iron.notepad.util.chat.ChatUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import tv.twitch.chat.Chat;

public class NotepadCommand extends CommandBase {
    
    private final List aliases;

    private final Notepad np = new Notepad();

    private final ChatUtil c = new ChatUtil();

    File notesFile = new NotepadConfig().notesFile;

    Path notepadPath = new NotepadConfig().notepadPath;

    Path categoriesListPath = new NotepadConfig().categoriesListPath;
    Path categoriesPath = new NotepadConfig().categoriesPath;

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return super.addTabCompletionOptions(sender, args, pos);
    }

    public List<String> categories() throws IOException {
        return np.readCategories();
    }

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
                np.readPage(notepadPath, 1);
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
                    np.resetFile(notepadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (args[0].contains("open")) {
                try {
                    np.openFile(notesFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                me.iron.notepad.Notepad.printHelp(1);
            } else if (args[0].equalsIgnoreCase("colors")) {
                for (String color : c.colorsList) {
                    ChatUtil.addMessage(c.parseColor(color), color);
                }
            }else if (args[0].equalsIgnoreCase("category") || args[0].equalsIgnoreCase("categories")) {
                try {
                    np.readCategories();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    np.writeLine(notepadPath, convertStringArrayToString(args, " "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (args.length >= 2) {

            if (args[0].equalsIgnoreCase("add")) {

                try {
                    if (categories().contains(args[1])) {

                        //todo: add note with category

                        if (args[2].equalsIgnoreCase("add")) {

                            //add note content

                        } else {

                            //try to add note content

                        }


                    } else {
                        try {
                            np.writeLine(notepadPath, convertStringArrayToString(ignoreFirst(args), " "));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (args.length == 2 && (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete"))) {

                try {
                    int lineNumber = Integer.parseInt(args[1]);
                    np.deleteLine(notepadPath, lineNumber-1);
                } catch (Exception e) {
                    me.iron.notepad.Notepad.printHelp(1);
                }

            } else if (args[0].equalsIgnoreCase("page")) {

                try {
                    int page = Integer.parseInt(args[1]);
                    np.readPage(notepadPath, page);
                } catch (NumberFormatException | IOException e) {
                    me.iron.notepad.Notepad.printHelp(1);
                }

            } else if (args[0].equalsIgnoreCase("category") || args[0].equalsIgnoreCase("categories") || args[0].equalsIgnoreCase("folder") || args[0].equalsIgnoreCase("folders")) {

                //todo: update category reference lines upon notes change

                if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("new")) {
                    if (c.colorsList.contains(String.valueOf(args[3]))) {
                        try {
                            np.writeLine(categoriesListPath, (String.valueOf(args[2]) + ", " + String.valueOf(args[3])));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            np.writeLine(categoriesListPath, (String.valueOf(args[2]) + ", none"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("delete")) {
                    try {
                        int lineNumber = Integer.parseInt(args[2]);
                        try {
                            np.deleteLine(categoriesListPath, lineNumber-1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (NumberFormatException exception) {
                        String categoryName = String.valueOf(args[2]);
                        List lines = null;
                        try {
                            lines = np.readAllLines(categoriesListPath);
                        } catch (IOException e) {
                            ChatUtil.addMessage(EnumChatFormatting.GRAY, "No categories.");
                        }
                        try {
                            int lineIndex = 0;
                            for (Object line : lines) {
                                String[] lineArray = c.splitComponentString(String.valueOf(line));
                                ChatUtil.addMessage(lineArray[0]);
                                if (String.valueOf(lineArray[0]).equalsIgnoreCase(String.valueOf(categoryName))) {
                                    np.deleteLine(categoriesListPath, lineIndex);
                                }
                                lineIndex++;
                            }
                        } catch (Exception e) {
                            ChatUtil.addMessage(EnumChatFormatting.GRAY, ("Category: " + categoryName + " was not found."));
                        }
                    }

                } else if (args[1].equalsIgnoreCase("color")) {
                    int lineNumber = 0;
                    String categoryName = null;

                    try {
                        lineNumber = Integer.parseInt(args[2]);
                    } catch (NumberFormatException exception) {
                        categoryName = String.valueOf(args[2]);
                    }

                    List lines = null;
                    try {
                        lines = np.readAllLines(categoriesListPath);
                    } catch (IOException e) {
                        ChatUtil.addMessage(EnumChatFormatting.GRAY, "No categories.");
                    }

                    try {
                        int lineIndex = 0;
                        for (Object line : lines) {
                            String[] lineArray = c.splitComponentString(String.valueOf(line));
                            if (categoryName != null) {
                                if (String.valueOf(lineArray[0]).equalsIgnoreCase(String.valueOf(categoryName))) {
                                    lineNumber = lineIndex + 1;
                                    categoryName = lineArray[0];
                                    break;
                                }
                            } else if (categoryName == null){
                                if (lineIndex+1 == lineNumber) {
                                    categoryName = lineArray[0];
                                }
                            }
                            lineIndex++;
                        }
                    } catch (Exception e) {
                        ChatUtil.addMessage(EnumChatFormatting.GRAY, ("Category: " + categoryName + " was not found."));
                    }


                    String color = String.valueOf(args[3]);
                    if (c.colorsList.contains(color)) {
                        try {
                            np.writeLine(categoriesListPath, lineNumber-1, (categoryName + ", " + color));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ChatUtil.addMessage(EnumChatFormatting.GRAY, ("Color: " + color + " was not found."));
                    }
                } else {
                    //set to -1 as to not conflict with any lines
                    int lineNumber = -1;
                    try {
                        lineNumber = Integer.parseInt(args[1]);
                    } catch (NumberFormatException exception) {
                        ChatUtil.addMessage(EnumChatFormatting.GRAY, ("Invalid line number."));
                    }
                    try {
                        if (lineNumber != -1 && lineNumber <= np.readAllLines(notepadPath).size()) {
                            try {
                                if (categories().contains(args[2])) {

                                    //todo: assign line the give category

                                } else {
                                    ChatUtil.addMessage(EnumChatFormatting.GRAY, ("Category: " + String.valueOf(args[2])+ " was not found."));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ChatUtil.addMessage(EnumChatFormatting.GRAY, ("Line number out of index."));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {
                        if (categories().contains(args[1])) {

                            //todo: print note lines of said category

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            } else if (args[0].equalsIgnoreCase("help")) {

                try {
                    int page = Integer.parseInt(args[1]);
                    ChatUtil.addMessage(String.valueOf(page));
                    me.iron.notepad.Notepad.printHelp(page);
                } catch (NumberFormatException e) {
                    me.iron.notepad.Notepad.printHelp(1);
                }

            } else {
                try {
                    np.writeLine(notepadPath, convertStringArrayToString(args, " "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {

            try {
                int page = Integer.parseInt(args[0]);
                np.readPage(notepadPath, page);
            } catch (NumberFormatException | IOException e) {
                me.iron.notepad.Notepad.printHelp(1);
            }
        }
    }

    protected String[] ignoreFirst(String[] str) {
        String[] out = new String[str.length - 1];
        for (int i = 1; i < str.length; i++)
            out[i - 1] = str[i];
        return out;
    }

    protected String[] ignoreFirst(String[] str, Integer items) {
        String[] out = new String[str.length - items];
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

