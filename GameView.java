package game;

import city.cs.engine.UserView;
import javax.swing.*;
import java.awt.*;

public class GameView extends UserView {
    private Image background;
    private GameWorld world;
    private Game game;

    // Constructor: Initializes the world, game, and background
    public GameView(GameWorld world, int width, int height, Game game) {
        super(world, width, height);
        this.world = world;
        this.game = game;
        updateBackground(); // Update background when game starts
    }

    // Update the world reference
    public void updateWorld(GameWorld world) {
        this.world = world;
    }

    // Update background based on current level
    public void updateBackground() {
        if (game.getLevel() instanceof Level1) {
            background = new ImageIcon("data/backgroundwinx.gif").getImage();
        } else if (game.getLevel() instanceof Level2) {
            background = new ImageIcon("data/backgroundlevel2.gif").getImage();
        } else {
            background = new ImageIcon("data/backgroundlevel3.gif").getImage();
        }
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this); // Draw background image
    }

    @Override
    public void paintForeground(Graphics2D g) {
        g.setColor(Color.BLACK);

        // Display score and enemy health based on current level
        if (game.getLevel() instanceof Level1) {
            g.drawString("Score: " + world.getScore(), 10, 20);
            Enemy enemy = world.getEnemy(); // Get enemy from GameWorld
            if (enemy != null) {
                g.setColor(Color.RED); // Red text for health
                g.drawString("Enemy Health: " + (int) enemy.getHealth() + "%", 10, 40);
            }
        }
        if (game.getLevel() instanceof Level2 || game.getLevel() instanceof Level3) {
            g.drawString("Score: " + world.getScore(), 10, 20);
        }
    }
}
