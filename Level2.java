package game;

import org.jbox2d.common.Vec2;
import city.cs.engine.*;

public class Level2 extends GameLevel {
    private Enemy enemy1;

    public Level2(Game game) {
        super(game); // Calls the GameLevel constructor
    }

    @Override
    public void setupLevelEntities() {
        // Reset the student's pickup count and credits for Level 2
        getStudent().resetPickupCount();
        getStudent().setCredits(0);

        // Create and position enemy with a different image for Level 2
        enemy1 = new Enemy(this, "data/flora.png");
        enemy1.setPosition(new Vec2(5, -2));
        addStepListener(new EnemyController(enemy1, getStudent())); // Enemy control

        // Create platforms across the level
        for (int i = -5; i <= 5; i++) {
            Platform platform = new Platform(this);
            platform.setPosition(new Vec2(i * 3.9f, -6)); // Platforms spaced across the level
        }

        // Setup pickup collision listener for the level
        PickupCollision pickupCollision = new PickupCollision(getStudent(), this);
    }

    public Enemy getEnemy() {
        return enemy1; // Return the created enemy
    }

    @Override
    public boolean isComplete() {
        // Level completes when the student collects 3 credits
        return getStudent().getCredits() == 3;
    }

    @Override
    public String getLevelName() {
        return "Level2"; // Return the level name
    }
}
