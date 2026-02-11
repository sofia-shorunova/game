package game;

import city.cs.engine.SoundClip;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Main game class: orchestrates level setup, view, controls, and game flow.
 */
public class Game {
    private GameLevel level;           // Current level in play
    private GameView view;             // The graphical view displaying the world
    private StudentController controller; // Handles keyboard input for the student
    private SoundClip gameMusic;       // Background music loop

    /**
     * Entry point: constructs the Game object.
     */
    public static void main(String[] args) {
        new Game();
    }

    /**
     * Constructor: calls all initialization steps in order.
     */
    public Game() {
        initLevel();      // Load first level
        initMusic();      // Start music
        initView();       // Create the window and view
        initController(); // Attach student controls
        initKeyBindings();// Save/load shortcuts
        initFrame();      // Show the window
        startGame();      // Begin physics and focus
    }

    /**
     * Initializes and populates the first level.
     */
    private void initLevel() {
        level = new Level1(this);
        level.populate();  // Build level entities and add collision listeners
    }

    /**
     * Loads background music and begins looping it.
     */
    private void initMusic() {
        try {
            gameMusic = new SoundClip("data/gametheme.wav");
            gameMusic.loop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound: " + e.getMessage());
        }
    }

    /**
     * Constructs the GameView linked to the current level.
     */
    private void initView() {
        view = new GameView(level, 500, 500, this);
    }

    /**
     * Creates the controller that maps key presses to student actions.
     */
    private void initController() {
        controller = new StudentController(level.getStudent(), this);
        view.addKeyListener(controller);
    }

    /**
     * Adds global key shortcuts for saving (S) and loading (L).
     */
    private void initKeyBindings() {
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_S -> saveGame();  // Save on 'S'
                    case KeyEvent.VK_L -> loadGame();  // Load on 'L'
                }
            }
        });
    }

    /**
     * Sets up the JFrame, packs, shows it, and starts a repaint timer (~60 FPS).
     */
    private void initFrame() {
        JFrame frame = new JFrame("City Game");
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        // Repaint the view every 16ms for smooth animation
        Timer repaintTimer = new Timer(16, e -> view.repaint());
        repaintTimer.start();
    }

    /**
     * Starts the physics simulation and gives focus to the view.
     */
    private void startGame() {
        level.start();     // Begin the world's timer steps
        view.requestFocus(); // Ensure keyboard focus
    }

    /**
     * Saves current level state to disk via GameSaverLoader.
     */
    private void saveGame() {
        try {
            GameSaverLoader.save(level, "save.txt");
            System.out.println("Game saved.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads a saved level state, reinitializes view and controller.
     */
    private void loadGame() {
        try {
            if (gameMusic != null) {
                gameMusic.stop(); // Stop music while loading
            }

            GameLevel loadedLevel = GameSaverLoader.load("save.txt", this);
            setLevel(loadedLevel);

            if (gameMusic != null) {
                gameMusic.loop(); // Resume music
            }
            System.out.println("Game loaded.");
        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
    }

    /**
     * Advances to the next level (Level1→Level2→Level3→exit).
     */
    public void goToNextLevel() {
        if (level instanceof Level1) {
            level = new Level2(this);
        } else if (level instanceof Level2) {
            level = new Level3(this);
        } else {
            System.out.println("Well done! Game complete.");
            System.exit(0);
        }

        // Build the new level and refresh the view
        level.populate();
        view.setWorld(level);        // Point the UserView at the new world
        view.updateWorld(level);     // Update our score-tracking reference
        view.updateBackground();     // Change background if needed
        controller.updateStudent(level.getStudent()); // Bind to new student
        level.start();
        view.requestFocus();
    }

    /**
     * Switches to an arbitrary loaded level (e.g., via save/load).
     */
    public void setLevel(GameLevel newLevel) {
        if (level != null) level.stop();
        level = newLevel;
        level.populate();

        view.setWorld(level);
        view.updateWorld(level);
        view.updateBackground();
        controller.updateStudent(level.getStudent());
        level.start();
        view.requestFocus();
    }

    /**
     * Convenience accessor for the current student.
     */
    public Student getStudent() {
        return level.getStudent();
    }

    /**
     * Exposes the active level for querying or view updates.
     */
    public GameLevel getLevel() {
        return level;
    }

    /**
     * Exposes the GameView for external repaint or UI tweaks.
     */
    public GameView getView() {
        return view;
    }
}
