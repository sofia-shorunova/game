package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Enemy with finite-state behavior (Patrol, Chase, Retreat, Idle) and health management.
 */
public class Enemy extends Walker {
    // States defining enemy behavior modes
    private enum State {
        IDLE,      // Stand still
        PATROL,    // Walk back and forth between limits
        CHASE,     // Follow the student when nearby
        RETREAT    // Move away when injured
    }

    private State currentState;        // Currently active behavior state
    private float speed = 2f;          // Movement speed
    private boolean movingRight = true;// Direction flag
    private float health = 100;        // Health percentage (0–100)
    private BodyImage enemyImage;      // Visual sprite for the enemy
    private float patrolLeftLimit = -8; // Left boundary for patrolling
    private float patrolRightLimit = 8; // Right boundary for patrolling

    /**
     * Constructs an Enemy in the given world, using a custom image.
     * @param world    The physics world the enemy belongs to
     * @param imagePath Path to the enemy's image file
     */
    public Enemy(World world, String imagePath) {
        super(world, new BoxShape(1, 2));  // Collider shape
        enemyImage = new BodyImage(imagePath, 4); // Load sprite
        addImage(enemyImage);
        this.currentState = State.PATROL;  // Default to patrolling
    }

    /**
     * Defines custom patrol boundaries.
     * @param left   X-coordinate of left edge
     * @param right  X-coordinate of right edge
     */
    public void setPatrolLimits(float left, float right) {
        this.patrolLeftLimit = left;
        this.patrolRightLimit = right;
    }

    /**
     * Called each physics step to update behavior based on student position and health.
     * @param student The player character to interact with
     */
    public void update(Student student) {
        float distanceToStudent = student.getPosition().x - getPosition().x;

        // State transitions:
        if (health <= 0) {
            currentState = State.IDLE;    // Too injured to move
        } else if (health < 30) {
            currentState = State.RETREAT; // Low health: run away
        } else if (Math.abs(distanceToStudent) < 5) {
            currentState = State.CHASE;   // Student is close: chase
        } else {
            currentState = State.PATROL;  // Otherwise keep patrolling
        }

        // Execute behavior for the current state
        switch (currentState) {
            case IDLE:
                stopWalking();
                break;
            case PATROL:
                patrol();
                break;
            case CHASE:
                chaseStudent(distanceToStudent);
                break;
            case RETREAT:
                retreatFromStudent(distanceToStudent);
                break;
        }
    }

    /**
     * Patrols between the left and right limits, clamping position at edges.
     */
    private void patrol() {
        float x = getPosition().x;
        if (x <= patrolLeftLimit) {
            movingRight = true;
            setPosition(new Vec2(patrolLeftLimit, getPosition().y));
        } else if (x >= patrolRightLimit) {
            movingRight = false;
            setPosition(new Vec2(patrolRightLimit, getPosition().y));
        }
        startWalking(movingRight ? speed : -speed);
    }

    /**
     * Chases the student but never crosses patrol boundaries.
     * @param distanceToPlayer Signed distance to the student (positive: student is right)
     */
    private void chaseStudent(float distanceToPlayer) {
        float x = getPosition().x;
        // Prevent leaving patrol area
        if (x <= patrolLeftLimit) {
            startWalking(speed);
            return;
        }
        if (x >= patrolRightLimit) {
            startWalking(-speed);
            return;
        }
        // Move toward the student
        startWalking(distanceToPlayer > 0 ? speed : -speed);
    }

    /**
     * Retreats from the student but respects patrol boundaries.
     * @param distanceToStudent Signed distance to the student (positive: student is right)
     */
    private void retreatFromStudent(float distanceToStudent) {
        float x = getPosition().x;
        if (x <= patrolLeftLimit) {
            startWalking(speed);
            return;
        }
        if (x >= patrolRightLimit) {
            startWalking(-speed);
            return;
        }
        // Move away from the student
        startWalking(distanceToStudent > 0 ? -speed : speed);
    }

    /**
     * Inflicts damage to the enemy, clamping at zero.
     * @param amount Amount to subtract from health
     */
    public void takeDamage(float amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    /**
     * Returns the enemy's current health percentage.
     */
    public float getHealth() {
        return health;
    }

    /**
     * Directly sets the enemy's health (clamped at zero).
     */
    public void setHealth(float health) {
        this.health = Math.max(0, health);
    }

    /**
     * Returns true if the enemy has no health remaining.
     */
    public boolean isDead() {
        return health <= 0;
    }
}
