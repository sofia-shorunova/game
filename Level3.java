package game;

import org.jbox2d.common.Vec2;
import city.cs.engine.*;

public class Level3 extends GameLevel {
    private Enemy enemyLeft;
    private Enemy enemyRight;

    public Level3(Game game) {
        super(game);
    }

    @Override
    public void setupLevelEntities() {
        // Set initial student position
        getStudent().setPosition(new Vec2(5, -2));

        // Create platforms for the level
        for (int i = -5; i <= 5; i++) {
            Platform platform = new Platform(this);
            platform.setPosition(new Vec2(i * 3.9f, -6)); // Position platforms
        }

        // --- First enemy (left) ---
        enemyLeft = new Enemy(this, "data/enemy.png"); // Load enemy image
        enemyLeft.setPosition(new Vec2(-10, -2)); // Set position
        enemyLeft.setPatrolLimits(-15, 0); // Patrols left side
        addStepListener(new EnemyController(enemyLeft, getStudent())); // Control enemy movement

        // --- Second enemy (right) ---
        enemyRight = new Enemy(this, "data/enemy.png"); // Load enemy image
        enemyRight.setPosition(new Vec2(10, -2)); // Set position
        enemyRight.setPatrolLimits(0, 15); // Patrols right side
        addStepListener(new EnemyController(enemyRight, getStudent())); // Control enemy movement
    }

    @Override
    public boolean isComplete() {
        // Level is complete when student collects 5 credits
        return getStudent().getCredits() == 5;
    }

    @Override
    public String getLevelName() {
        return "Level3"; // Return level name
    }
}
