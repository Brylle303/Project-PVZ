package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plant {
  protected int health;
  protected double x;
  protected double y;
  protected ImageView imageView;

  public Plant(String imagePath, int health) {
    this.health = health;
    imageView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
    imageView.setFitWidth(60); // default size
    imageView.setFitHeight(80);
    imageView.setPreserveRatio(true);
  }

  public ImageView getImageView() {
    return imageView;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
    imageView.setLayoutX(x);
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
    imageView.setLayoutY(y);
  }

  public void takeDamage(int dmg) {
    health -= dmg;
    if (health <= 0) {
      // Optional: remove plant from screen
      imageView.setVisible(false);
    }
  }
}