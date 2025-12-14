package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class Zombie extends GameObject{
	
	private Timeline eatTimeline;
	public boolean isEating = false;
	
	public Zombie(double x, double y, double inpHealth, double inpDamage, double inpSpeed, double inpWidth, double inpHeight, String inpEntityName, String gifPath) {
		super(x, y, inpHealth, inpDamage, inpSpeed, inpWidth, inpHeight, inpEntityName, gifPath);
		//this.setX(x);
		//this.setY(y);
	}
	@Override
	public void move() {
		if(!(this.isEating)) {
		this.x -= this.speed;
		this.setX(this.x);
		}
	}
	 
	
    public void startEating(Plant plant) {
        if (isEating) return;

        isEating = true;

        eatTimeline = new Timeline(new KeyFrame(Duration.millis(800), e -> {
            
        	if (plant.isAlive) {
                plant.takeDamage(damage);
            } else {
                stopEating();
            }
        }));
        eatTimeline.setCycleCount(Timeline.INDEFINITE);
        eatTimeline.play();
    }

    public void stopEating() {
        isEating = false;
        if (eatTimeline != null) {
            eatTimeline.stop();
            eatTimeline = null;
        }
    }

}
