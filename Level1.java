package game;

import org.jbox2d.common.Vec2;
import city.cs.engine.*;

public class Level1 extends GameLevel {
    private Enemy enemy1;
    private Gun gun;

    public Level1(Game game) {
        super(game); // Calls parent constructor
    }

    @Override
    public void setupLevelEntities() {
        // Reset the pickup count for the student
        getStudent().resetPickupCount();

        // Create and position the enemy
        enemy1 = new Enemy(this, "data/icy.png");
        enemy1.setPosition(new Vec2(5, -2));
        addStepListener(new EnemyController(enemy1, getStudent())); // Control enemy movement

        // Create and position the gun
        gun = new Gun(this);
        gun.setPosition(new Vec2(5, 0));

        // Create ground platforms across the level
        for (int i = -5; i <= 5; i++) {
            Platform platform = new Platform(this);
            platform.setPosition(new Vec2(i * 3.9f, -6));
        }
    }

    public Enemy getEnemy() {
        return enemy1; // Return the created enemy
    }

    @Override
    public boolean isComplete() {
        return getStudent().getCredits() == 5; // Level complete when student collects 5 credits
    }

    @Override
    public String getLevelName() {
        return "Level1"; // Return the name of the level
    }
}
