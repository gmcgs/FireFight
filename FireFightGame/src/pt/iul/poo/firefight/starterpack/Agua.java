package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.List;

import pt.iul.poo.firefight.starterpack.gui.ImageTile;
import pt.iul.poo.firefight.starterpack.utils.Direction;
import pt.iul.poo.firefight.starterpack.utils.Point2D;

public class Agua extends GameElement {
	
	private String sentido;

	public Agua(Point2D position, String sentido) {
		super(position, sentido, 3);
		this.sentido = sentido;
	}
	
	public String getSentido() {
		return sentido;
	}

	public void daslkfa(Direction s) {
		List<ImageTile> aux = new ArrayList<>();
		Agua rega = null;
		setPosition(getPosition().plus(s.asVector()));
		for (ImageTile i : motor.getTileList()) {
			if (i.getPosition().equals(getPosition()) && i instanceof Fire) {
				aux.add(i);
				rega = new Agua(i.getPosition(), getSentido());
				for (ImageTile turnOff : motor.getTileList())
					if (turnOff instanceof Vegetation && turnOff.getPosition().equals(i.getPosition())) {
						((Vegetation) turnOff).setOnFire();
					}
			}
		}
		if (rega != null) {
			motor.removeListFromExistence(aux);
			motor.addToTileList(rega);
			motor.getGUI().addImage(rega);
			motor.addPoints();
		}
	}
}