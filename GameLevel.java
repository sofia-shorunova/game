package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Base class for a game level: sets up the student and delegates level-specific entities.
 */
public abstract class GameLevel extends GameWorld {
    private Student student;

    public GameLevel(Game game) {
        super(game);
    }

    /**
     * Create the player and attach collision handling, then build level entities.
     */
    public void populate() {
        student = new Student(this);
        student.setPosition(new Vec2(0, -5));
        student.resetPickupCount();
        student.addCollisionListener(new PickupCollision(student, this));
        setupLevelEntities();
    }

    /**
     * Subclasses implement this to add platforms, enemies, pickups, etc.
     */
    public abstract void setupLevelEntities();

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Check if the level's win condition is met.
     */
    public abstract boolean isComplete();

    /**
     * Unique identifier used for saving/loading.
     */
    public abstract String getLevelName();
}
