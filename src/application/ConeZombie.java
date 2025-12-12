package application;

public class ConeZombie extends Zombie {

  public ConeZombie() {
    super("/Assets/coneheadzombie.gif", 70, 90);

    // Scale cone zombie to appear bigger
    imageView.setScaleX(1.7);
    imageView.setScaleY(1.7);

    health = 200; // stronger than normal zombie
  }
}
