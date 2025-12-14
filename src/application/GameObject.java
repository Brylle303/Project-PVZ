package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameObject {
	
	public int lane;
	protected double x;
	protected double y;
	protected double health;
	protected double damage;
	protected double speed;
	protected String entityName;
	protected ImageView imageView;
	protected boolean isAlive;
	protected double width;
	protected double height;
	
	
	
	public GameObject(double x, double y, double inpHealth, double inpDamage, double inpSpeed, double inpWidth, double inpHeight, String inpEntityName, String gifPath) {
		this.x 	 = x;
		this.y 	 = y;
		this.health  	 = inpHealth;
		this.entityName  = inpEntityName;
		this.isAlive 	 = true;
		this.width 		 = inpWidth;
		this.height 	 = inpHeight;
		this.damage 	 = inpDamage;
		this.speed = inpSpeed;
		imageView = new ImageView(new Image(getClass().getResource(gifPath).toExternalForm()));
	    imageView.setFitWidth(this.width);
	    imageView.setFitHeight(this.height);
	    imageView.setPreserveRatio(true);
	    this.setX(x);
	    this.setY(y);
	}
	public ImageView getImageView() {
	    return imageView;
	}
	
	public double getX() {
	    return imageView.getLayoutX();
	}
	
	public void setX(double x) {
	    imageView.setLayoutX(x);
	}
	
	public double getY() {
	    return imageView.getLayoutY();
	}
	
	public void setY(double y) {
	    imageView.setLayoutY(y);
	}
	
	public void setHealth(int inpHealth) {
		this.health = inpHealth;
	}
	public void takeDamage(double inpDamage) {
		this.health -= inpDamage;
		  if(this.health <= 0) {
			  this.isAlive = false;
			  this.speed = 0;
			  this.damage = 0;
		  }
	}
		
	public abstract void move();

}
