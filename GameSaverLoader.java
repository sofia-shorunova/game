package game;

import java.io.*;
import java.util.StringTokenizer;

public class GameSaverLoader {

    // Save the current level and student credits to a file
    public static void save(GameLevel level, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName, false); // Overwrite existing file
        String line = level.getLevelName() + "," + level.getStudent().getCredits(); // Save level and credits
        writer.write(line);
        writer.close(); // Close the file
    }

    // Load the saved game state from a file
    public static GameLevel load(String fileName, Game game) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine(); // Read the saved data
        reader.close();

        StringTokenizer tokenizer = new StringTokenizer(line, ",");
        String levelName = tokenizer.nextToken(); // Extract level name
        int credits = Integer.parseInt(tokenizer.nextToken()); // Extract credits

        // Create the appropriate level based on the saved data
        GameLevel level;
        if (levelName.equals("Level1")) {
            level = new Level1(game);
        } else if (levelName.equals("Level2")) {
            level = new Level2(game);
        } else if (levelName.equals("Level3")) {
            level = new Level3(game);
        } else {
            throw new IOException("Unknown level name: " + levelName); // Error if level is unknown
        }

        level.getStudent().setCredits(credits); // Set the student's credits
        return level;
    }
}
