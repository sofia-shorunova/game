package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * A collectible Pickup that plays a sound and supports FSM behavior.
 */
class Pickup extends Walker {

    // FSM States for the pickup object
    enum PickupState {
        IDLE, ACTIVATED, COLLECTED
    }

    private static SoundClip booksSound; // Sound for pickup collection
    private PickupState state; // Current state of the pickup

    static {
        try {
            booksSound = new SoundClip("data/booksound.wav"); // Load sound file
            System.out.println("Loading books sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e); // Handle sound loading errors
        }
    }

    public Pickup(World world) {
        super(world, new CircleShape(0.5f)); // Create pickup with circular shape
        addImage(new BodyImage("data/pickup.png", 1)); // Add image for the pickup
        this.state = PickupState.IDLE; // Initial state is IDLE
    }

    /** Get current FSM state of this pickup. */
    public PickupState getState() {
        return state;
    }

    /** Set FSM state of this pickup. */
    public void setState(PickupState newState) {
        this.state = newState;
    }

    /** Play sound and destroy this pickup when collected. */
    @Override
    public void destroy() {
        if (booksSound != null) {
            booksSound.play(); // Play sound when collected
        }
        super.destroy(); // Destroy the pickup object
    }
}
