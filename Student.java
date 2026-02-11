package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Student extends Walker {

    private static final Shape studentShape = new BoxShape(1, 2);
    private static final BodyImage image = new BodyImage("data/stella.png", 4f);

    private int credits;
    private int bookCount;
    private boolean hasGun; // Flag to track if student picked up a gun
    private int gunCount;
    private int pickupCount;
    private static SoundClip collectSound;

    static {
        try {
            collectSound = new SoundClip("data/collect.wav");
            System.out.println("Loading student collect sound");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading collect sound: " + e);
        }
    }

    public Student(World world) {
        super(world, studentShape);
        addImage(image);
        this.credits = 0;
        this.bookCount = 0;
        this.hasGun = false; // Default: no gun
    }

    // Getters and setters for credits
    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addCredits(int amount) {
        this.credits += amount;
    }

    // Reset pickup count
    public void resetPickupCount() {
        pickupCount = 0;
    }

    // Setters for book and gun counts
    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public void setGunCount(int gunCount) {
        this.gunCount = gunCount;
    }

    /** Called when student collects a book */
    public void collectBook() {
        bookCount++;
        if (collectSound != null) {
            collectSound.play(); // Play sound when book is collected
        }
    }

    /** Called when student picks up a gun */
    public void pickupGun() {
        this.hasGun = true;
    }

    /** Check if student has a gun */
    public boolean hasGun() {
        return hasGun;
    }

    // Collect a generic pickup and update count
    public void collectPickup() {
        pickupCount++;
        getWorld().oneStep(); // Update pickup score in GameWorld
    }

    public int getPickupCount() {
        return pickupCount;
    }
}
