package game.puzzlegame;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameData {
    private String[] header1;
    private String[] header2;
    private String[] header3and4;
    private String[] clues;
    private String story;
    private ArrayList<Integer[]> gridAnswers = new ArrayList<>();

    public GameData(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            parseData(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseData(List<String> lines) {
        for (String line : lines) {
            if (line.startsWith("header1:")) {
                header1 = line.substring("header1:".length()).trim().split(", ");
            } else if (line.startsWith("header2:")) {
                header2 = line.substring("header2:".length()).trim().split(", ");
            } else if (line.startsWith("header3and4:")) {
                header3and4 = line.substring("header3and4:".length()).trim().split(", ");
            } else if (line.startsWith("clues:")) {
                clues = line.substring("clues:".length()).trim().split("\\|");
            } else if (line.startsWith("story:")) {
                story = line.substring("story:".length()).trim();
            } else if (line.startsWith("grid")) {
                gridAnswers.add(parseGrid(line));
            }
        }
    }

    private Integer[] parseGrid(String line) {
        String[] pairs = line.substring(line.indexOf(':') + 1).trim().split(" ");
        Integer[] grid = new Integer[pairs.length]; // Assuming each pair consists of 2 integers

        for (int i = 0; i < pairs.length; i++) {
            grid[i] = Integer.parseInt(pairs[i]);
        }
        return grid;
    }


    // Getter methods
    public String[] getHeader1() {
        return header1;
    }

    public String[] getHeader2() {
        return header2;
    }

    public String[] getHeader3and4() {
        return header3and4;
    }

    public String[] getClues() {
        return clues;
    }

    public String getStory() {
        return story;
    }

    public ArrayList<Integer[]> getGridAnswers() {
        return gridAnswers;
    }
}
