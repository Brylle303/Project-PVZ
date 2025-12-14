package application;

public abstract class Projectile extends GameObject{
	
	public Projectile(double x, double y, double inpHealth, double inpDamage, double inpSpeed, double inpWidth, double inpHeight, String inpEntityName, String gifPath) {
		super(x, y, inpHealth, inpDamage, inpSpeed, inpWidth, inpHeight, inpEntityName, gifPath);
	}
	
	@Override
	public void move() {
	    this.x += this.speed;
	    this.setX(this.x);
	}


}
