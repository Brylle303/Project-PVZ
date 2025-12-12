package application;

public class Peashooter extends Plant {

  public Peashooter(double x, double y) {
    super("/Assets/peashooter.gif", 100); // image and health
    setX(x + 15);
    setY(y + 15);
  }

  // Shoot a pea
  public Pea shoot() {
    // Pea spawns at front of shooter
    double peaX = getX() + imageView.getFitWidth();
    double peaY = getY() + imageView.getFitHeight() / 2;
    return new Pea(peaX, peaY);
  }
}
