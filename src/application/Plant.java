package application;

import javafx.animation.Timeline;


public abstract class Plant extends GameObject{

	protected Timeline shootTimeline;
	
	public Plant(double x, double y, double inpHealth, double inpDamage, double inpSpeed, double inpWidth, double inpHeight, String inpEntityName, String gifPath) {
		super(x, y, inpHealth, inpDamage, inpSpeed, inpWidth, inpHeight, inpEntityName, gifPath);
	}
	
	@Override
	public void move() {
		
	}
	public abstract Pea fire();
	
	public void placePlant() {
		this.setX(this.x);
		this.setY(this.y);
	}
	
    public abstract void startShooting();
    public abstract void stopShooting();
}

	
