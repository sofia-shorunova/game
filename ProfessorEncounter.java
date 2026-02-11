package game;

import city.cs.engine.*;

/**
 * Handles collisions between the student and other game objects like enemies.
 */
public class ProfessorEncounter implements CollisionListener {
    private final Student student;
    private final GameLevel level;
    private final Game game;

    /**
     * Constructor for the collision listener.
     * @param student the student character
     * @param level the current game level
     * @param game the main game instance
     */
    public ProfessorEncounter(Student student, GameLevel level, Game game) {
        this.student = student;
        this.level = level;
        this.game = game;
    }

    @Override
    public void collide(CollisionEvent e) {
        Body other = e.getOtherBody();

        // Handle collision with Pickup
        if (other instanceof Pickup) {
            student.addCredits(1); // Add credits to student
            other.destroy(); // Destroy pickup
        }

        // Handle collision with Enemy if level is complete
        if (other instanceof Enemy && level.isComplete()) {
            game.goToNextLevel(); // Move to the next level
        }
    }
}
