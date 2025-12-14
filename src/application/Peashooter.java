package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;

public class Peashooter extends Plant {

    private static final double FIRE_INTERVAL = 3; // seconds
    private AnchorPane root;

    public Peashooter(double x, double y, AnchorPane root) {
        super(x, y, 100, 0, 0, 60, 80, "PeaShooter", "/Assets/peashooter.gif");
        this.root = root;
        startShooting();
    }

    private boolean isZombieInLane() {
        for (Zombie z : Adventure.allZombies) {
            if (z.isAlive && Math.abs(z.getY() - this.getY()) < 50) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Pea fire() {
        return new Pea(this);
    }

    @Override
    public void startShooting() {
        if (shootTimeline != null) return; // prevent duplicates

        shootTimeline = new Timeline(
        	    new KeyFrame(Duration.seconds(FIRE_INTERVAL), e -> {
        	        if (!isAlive) {
        	            stopShooting();
        	            return;
        	        }

        	        if (isZombieInLane()) {
        	            Pea pea = fire();
        	            Adventure.allObjects.add(pea);
        	            Adventure.allProjectiles.add(pea);
        	            root.getChildren().add(pea.getImageView());
        	        }
        	    })
        	);
        	shootTimeline.setCycleCount(Timeline.INDEFINITE);
        	shootTimeline.play();
    }

    @Override
    public void stopShooting() {
        if (shootTimeline != null) {
            shootTimeline.stop();
            shootTimeline = null;
        }
    }
}
