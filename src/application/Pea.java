package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pea {
  private ImageView node;
  private double speed = 5;

  public Pea(double startX, double startY) {
    node = new ImageView(new Image(getClass().getResource("/Assets/pea.png").toExternalForm()));
    node.setFitWidth(40); // adjust size
    node.setPreserveRatio(true);
    node.setLayoutX(startX);
    node.setLayoutY(startY - 30);
  }

  public void move() {
    node.setLayoutX(node.getLayoutX() + speed);
  }

  public ImageView getNode() {
    return node;
  }
}
