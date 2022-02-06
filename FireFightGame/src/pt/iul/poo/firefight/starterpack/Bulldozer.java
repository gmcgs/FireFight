package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Bulldozer extends MovableObject {

	private String sentido = "bulldozer_down";

	public Bulldozer(Point2D position) {
		super(position, "bulldozer_down", 3);
	}

	@Override
	public void move(int key) {
		switch (key) {
		case KeyEvent.VK_RIGHT:
			if (!isThereFireThere(getPosition(), Direction.RIGHT)
					&& canMoveTo(getPosition().plus(Direction.RIGHT.asVector()))
					&& !isBulldozerThere(Direction.RIGHT)) {
				setPosition(getPosition().plus(Direction.RIGHT.asVector()));
				moveTheBulldozer();
				sentido = "bulldozer_right";
			}
			break;
		case KeyEvent.VK_LEFT:
			if (!isThereFireThere(getPosition(), Direction.LEFT)
					&& canMoveTo(getPosition().plus(Direction.LEFT.asVector()))
					&& !isBulldozerThere(Direction.LEFT)) {
				setPosition(getPosition().plus(Direction.LEFT.asVector()));
				moveTheBulldozer();
				sentido = "bulldozer_left";
			}
			break;
		case KeyEvent.VK_UP:
			if (!isThereFireThere(getPosition(), Direction.UP)
					&& canMoveTo(getPosition().plus(Direction.UP.asVector()))
					&& !isBulldozerThere(Direction.UP)) {
				setPosition(getPosition().plus(Direction.UP.asVector()));
				moveTheBulldozer();
				sentido = "bulldozer_up";
			}
			break;
		case KeyEvent.VK_DOWN:
			if (!isThereFireThere(getPosition(), Direction.DOWN)
					&& canMoveTo(getPosition().plus(Direction.DOWN.asVector()))
					&& !isBulldozerThere(Direction.DOWN)) {
				setPosition(getPosition().plus(Direction.DOWN.asVector()));
				moveTheBulldozer();
				sentido = "bulldozer_down";
			}
			break;
		}
	}

	@Override
	public String getName() {
		return sentido;
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	public boolean isBulldozerThere(Direction direcao) {
		for (ImageTile i : motor.getTileList()) {
			if (i instanceof Bulldozer  && i.getPosition().equals(getPosition().plus(direcao.asVector())) ) {
				return true;
			}
		}
		return false;
	}
	
	public void moveTheBulldozer() {
		List<ImageTile> aux = new ArrayList<>();
		ImageTile territa = null;
		for (ImageTile i : motor.getTileList())
			if (i instanceof Vegetation && i.getPosition().equals(getPosition())
					&& !((Vegetation) i).isBurning()) {
				aux.add(i);
				territa = new Terra(new Point2D(getPosition().getX(), getPosition().getY()));
			}
		motor.removeFromGUI(this);
		motor.addToGUI(this);
		if (territa != null) {
			motor.removeListFromExistence(aux);
			motor.addToTileList(territa);
			motor.addToGUI(territa);
		}
	}

	public boolean isThereFireThere(Point2D point, Direction direcao) {
		for (ImageTile i : motor.getTileList()) {
			if (i.getPosition().equals(point.plus(direcao.asVector())) && i instanceof Fire) {
				return true;
			}
		}
		return false;
	}

}
