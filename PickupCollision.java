package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

import java.util.Random;

class PickupCollision implements CollisionListener {
    private Student student;
    private GameWorld world;
    private int gunCount = 0; // Number of guns collected
    public int pickupCount = 0; // Number of pickups collected
    private Random rand = new Random();

    public PickupCollision(Student student, GameWorld world) {
        this.student = student;
        this.world = world;
    }

    public int getPickupCount() {
        return pickupCount; // Return number of pickups collected
    }

    @Override
    public void collide(CollisionEvent e) {
        // Handle collision with Pickup
        if (e.getOtherBody() instanceof Pickup pickup) {
            if (pickup.getState() == Pickup.PickupState.IDLE) {
                pickup.setState(Pickup.PickupState.ACTIVATED); // Activate pickup
                System.out.println("Pickup activated!");
            }

            if (pickup.getState() == Pickup.PickupState.ACTIVATED) {
                pickup.setState(Pickup.PickupState.COLLECTED); // Mark as collected
                world.increaseScore(10); // Increase score
                pickup.destroy();
                pickupCount++; // Increase pickup count
                student.collectPickup();
                student.addCredits(1); // Add credits to student
                System.out.println("Pickup collected! Pickups: " + pickupCount + ", Score: " + world.getScore());
                world.getGame().getView().repaint(); // Refresh game view
                if (pickupCount >= 3) { // Next level after 3 pickups
                    System.out.println("Collected 3 pickups! Going to the next level...");
                    world.goToNextLevel();
                }
            }
        }

        // Handle collision with Gun
        if (e.getOtherBody() instanceof Gun gun) {
            gun.destroy();
            gunCount++;
            student.setGunCount(gunCount); // Update gun count

            // Decrease enemy health by 25% when a gun is collected
            if (world.getEnemy() != null) {
                Enemy enemy = world.getEnemy();
                float originalHealth = 100; // Assume original health is 100%
                float healthToSubtract = originalHealth * 0.25f; // 25% damage
                enemy.takeDamage(healthToSubtract); // Apply damage to enemy
                System.out.println("Collected a gun! Enemy's health decreased by 25%. Current health: " + enemy.getHealth());
            }

            // Move to next level after collecting 4 guns
            if (gunCount >= 4) {
                System.out.println("Collected 4 guns! Going to the next level...");
                world.goToNextLevel();
            } else {
                // Spawn new gun at random position
                int x = rand.nextInt(13) - 7;
                Gun newGun = new Gun(student.getWorld());
                newGun.setPosition(new Vec2(x, -2));
            }
        }

        // Handle collision with Enemy
        if (e.getOtherBody() instanceof Enemy) {
            student.destroy(); // Destroy student on enemy collision
            System.exit(0); // End the game
        }
    }
}
