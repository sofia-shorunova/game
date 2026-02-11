package game;

import city.cs.engine.World;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jbox2d.common.Vec2;

public class GameWorld extends World implements ActionListener {
    protected Timer pickupTimer;
    protected int score;
    private Game game;
    private Enemy enemy;
    private PickupCollision pickupCollision;

    public GameWorld(Game game) {
        super();
        this.game = game;
        this.score = 0;

        // Create platforms across the level
        for (int i = -5; i <= 5; i++) {
            Platform platform = new Platform(this);
            platform.setPosition(new Vec2(i * 3.9f, -6));
        }

        // Set up timer to spawn pickups
        pickupTimer = new Timer(10000, this);
        pickupTimer.setInitialDelay(1000);
        pickupTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Spawn a new Pickup at a random position
        float x = (float) (Math.random() * 20 - 10);
        float y = (float) (Math.random() * 8);

        Pickup p = new Pickup(this);
        p.setPosition(new Vec2(x, y));

        // Add the collision listener for pickups
        if (pickupCollision != null) {
            p.addCollisionListener(pickupCollision);
        }

        System.out.println("Spawned a new Pickup at: " + x + ", " + y);
    }

    public int getPickupScore() {
        // Return pickup count from collision listener
        if (pickupCollision != null) {
            return pickupCollision.getPickupCount();
        } else {
            return 0;
        }
    }

    public void setPickupCollision(PickupCollision pickupCollision) {
        this.pickupCollision = pickupCollision;
    }

    public void increaseScore(int amount) {
        // Increase the score by a given amount
        score += amount;
    }

    public Game getGame() {
        return game;
    }

    public Student getStudent() {
        return game.getStudent();
    }

    public int getScore() {
        return score;
    }

    public void goToNextLevel() {
        // Clean up before moving to the next level
        if (enemy != null) {
            enemy.destroy();
        }

        game.goToNextLevel();
        this.score = 0;  // Reset score on level change
        // Pickup count will reset by creating a new PickupCollision
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    protected void setScore(int score) {
        this.score = score;
    }
}
