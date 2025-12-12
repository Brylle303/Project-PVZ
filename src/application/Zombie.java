package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Zombie {
  protected int health;
  protected double coordinates;
  protected ImageView imageView;

  protected double width = 70;
  protected double height = 90;

//  public Zombie(String gifPath) {
//    imageView = new ImageView(new Image(getClass().getResource(gifPath).toExternalForm()));
//    imageView.setFitWidth(500);
//    imageView.setFitHeight(700);
//    imageView.setPreserveRatio(true);
//    health = 100; // default health
//  }
  
  public Zombie(String gifPath, double width, double height) {
	    imageView = new ImageView(new Image(getClass().getResource(gifPath).toExternalForm()));
	    imageView.setFitWidth(width);
	    imageView.setFitHeight(height);
	    imageView.setPreserveRatio(true);
	    health = 100; // default health
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
}
