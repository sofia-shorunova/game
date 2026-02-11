package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import city.cs.engine.*;

public class StudentController implements KeyListener {
    private Student student;
    private Game game;
    private static final BodyImage imageRight = new BodyImage("data/stella.png", 4f);
    private static final BodyImage imageLeft = new BodyImage("data/stella1.png", 4f);

    public StudentController(Student student, Game game) {
        this.student = student;
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // Handle left movement
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            student.startWalking(-5);
            if (student.getImages().isEmpty() || !student.getImages().get(0).equals(imageLeft)) {
                student.removeAllImages();
                student.addImage(imageLeft);
            }
        }
        // Handle right movement
        else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            student.startWalking(5);
            if (student.getImages().isEmpty() || !student.getImages().get(0).equals(imageRight)) {
                student.removeAllImages();
                student.addImage(imageRight);
            }
        }
        // Handle jump action
        else if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_W) {
            student.jump(12);
        }

        // Save game action
        if (code == KeyEvent.VK_S) {
            try {
                GameSaverLoader.save(game.getLevel(), "savefile.txt");
                System.out.println("Game saved.");
            } catch (IOException ex) {
                System.out.println("Failed to save game: " + ex.getMessage());
            }
        }

        // Load game action
        if (code == KeyEvent.VK_L) {
            try {
                GameLevel loadedLevel = GameSaverLoader.load("savefile.txt", game);
                game.setLevel(loadedLevel);
                System.out.println("Game loaded.");
            } catch (IOException ex) {
                System.out.println("Failed to load game: " + ex.getMessage());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        // Stop walking when movement keys are released
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A ||
                code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            student.stopWalking();
        }
    }

    public void updateStudent(Student student) {
        this.student = student;
    }
}
