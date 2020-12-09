package me.iron.notepad.util.file;

import me.iron.notepad.config.NotepadConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtil {

    File file = new NotepadConfig().notesFile;

    Path path = new NotepadConfig().notepadPath;

    public void findFile() throws IOException {
        file.createNewFile();
    }

    public String readLine(Path path, Integer lineIndex) throws IOException {

        List<String> lines = readAllLines(path);

        String line;

        if (lineIndex < 0 || !(lineIndex == (int)lineIndex)) {
            throw new IndexOutOfBoundsException();
        }

        try {
            line = Files.readAllLines(path, StandardCharsets.UTF_8).get(lineIndex);
            //System.out.println("Line index #" + lineIndex + ": " + line);
        } catch (IndexOutOfBoundsException e) {
            //return last line if index too high
            //line = Files.readAllLines(path, StandardCharsets.UTF_8).get(lines.size()-1);
            line = "";
            //System.out.println("Line index #" + (lines.size()-1) + ": " + line);
        }


        return line;

    }

    public List readAllLines(Path path) throws IOException {

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        return lines;

    }

    public String writeLine(Path path, Integer lineIndex, String lineString) throws IOException {

        List<String> lines = readAllLines(path);

        if (lines.isEmpty()) {
            //write to line 0 if file is empty
            System.out.println("Empty");
            lines.add(lineString);
            Files.write(path, lines, StandardCharsets.UTF_8);

            return "Wrote: " + lineString + " to line index #0";

        } else if (lineIndex >= lines.size()) {
            //append to end if index too high
            lines.add(lines.size(), lineString);
            Files.write(path, lines, StandardCharsets.UTF_8);

            return "Wrote: " + lineString + " to line index #" + lines.size();

        } else if (lineIndex < 0 || !(lineIndex == (int)lineIndex)) {
            throw new IndexOutOfBoundsException();
        } else {
            System.out.println("yes");
            //replace line
            lines.remove(lines.indexOf(readLine(path, lineIndex)));
            lines.add(lineIndex, lineString);
            Files.write(path, lines, StandardCharsets.UTF_8);

            return "Wrote: " + lineString + " to line index #" + lineIndex;
        }
    }

    public String deleteLine(Path path, Integer lineIndex) throws Exception {
        List<String> lines = readAllLines(path);

        if (lines.isEmpty()) {
            //do nothing
            throw new Exception("Already empty");

        } else if (lineIndex >= lines.size()) {
            //do nothing if index too high

            /*
            lines.remove(lines.size()-1);
            Files.write(path, lines, StandardCharsets.UTF_8);

            System.out.println("Removed line index #" + lines.size() + "");

             */
            throw new IndexOutOfBoundsException();

        } else if (lineIndex < 0 || !(lineIndex == (int)lineIndex)) {

            throw new IndexOutOfBoundsException();
        } else {
            //replace line
            lines.remove(lines.indexOf(readLine(path, lineIndex)));
            Files.write(path, lines, StandardCharsets.UTF_8);

            return "Removed line index #" + lineIndex;
        }

    }

    public String clearFile(Path path) throws IOException {
        List<String> lines = readAllLines(path);
        lines.removeAll(lines);
        Files.write(path, lines, StandardCharsets.UTF_8);

        return "Cleared all lines";
    }

}
