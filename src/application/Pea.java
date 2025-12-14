package application;

public class Pea extends Projectile {

    public Pea(Plant shooter) {
    	super(
    		    shooter.x + shooter.width + 5,             // x
    		    shooter.y + shooter.height - 80,      // y: pea.height / 2
    		    1, 25, 0.12, 35, 35, "Pea", "/Assets/pea.png"
    		);

        // Update ImageView to match starting coordinates
        this.setX(this.x);
        this.setY(this.y);
    }
}
