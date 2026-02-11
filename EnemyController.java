package game;

import city.cs.engine.*;

/**
 * EnemyController listens to each physics step and drives
 * the Enemy's finite-state update based on the Student's position.
 */
public class EnemyController implements StepListener {
    private Enemy enemy;   // The enemy instance to control
    private Student student; // The player character, used for chasing logic

    /**
     * Constructs a controller for a specific enemy and student.
     * @param enemy   The Enemy this controller will update each step
     * @param student The Student whose position influences the Enemy
     */
    public EnemyController(Enemy enemy, Student student) {
        this.enemy = enemy;
        this.student = student;
    }

    /**
     * Called before each physics step: updates the Enemy's behavior.
     * @param stepEvent Provides timing and world information (unused directly here)
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        // Delegate to the Enemy's state machine, passing in the student
        enemy.update(student);
    }

    /**
     * Called after each physics step: no post-step actions needed currently.
     * @param stepEvent Provides timing and world information (unused)
     */
    @Override
    public void postStep(StepEvent stepEvent) {
        // Intentionally empty: all logic is handled in preStep
    }
}
