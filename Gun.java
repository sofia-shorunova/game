package game;

import city.cs.engine.*;

/**
 * Gun pickup class.
 */
public class Gun extends DynamicBody {
    private static final Shape shape = new BoxShape(0.5f, 0.5f);
    private boolean collected = false; // Flag to track if the gun has been collected

    public Gun(World world) {
        super(world, shape);
        addImage(new BodyImage("data/gun.png", 1f)); // Add gun image
    }

    // Returns if the gun has been collected
    public boolean isCollected() {
        return collected;
    }

    // Marks the gun as collected or not
    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
