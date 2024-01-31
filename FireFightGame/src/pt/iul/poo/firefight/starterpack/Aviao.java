package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.List;

import pt.iul.poo.firefight.starterpack.gui.ImageTile;
import pt.iul.poo.firefight.starterpack.utils.Direction;
import pt.iul.poo.firefight.starterpack.utils.Point2D;

public class Aviao extends MovableObject {

	public Aviao(Point2D position) {
		super(position, "plane", 3);
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	@Override
	public void move(int key) {
		List<ImageTile> aux = new ArrayList<>();
		List<ImageTile> waters = new ArrayList<>();
		
		apagarFogosPorAviao(aux, waters);
		apagarFogosPorAviao(aux, waters);

		if (getPosition().getY() < 0) {
			motor.removeFromExistence(this);
		}

	}
	
	public void apagarFogosPorAviao(List<ImageTile> aux, List<ImageTile> waters) {
		if(getPosition().getY() == 9)
			waterBombardment(aux, waters);
		setPosition(getPosition().plus(Direction.UP.asVector()));
		if (getPosition().getY() >= 0) {
			waterBombardment(aux, waters);
		}
		if (!(waters.isEmpty()) && !(aux.isEmpty())) {
			motor.addPoints();
			motor.addListToTileList(waters);
			motor.addListToGUI(waters);
			motor.removeListFromExistence(aux);
		}
	}
	
	public void waterBombardment(List<ImageTile> aux, List<ImageTile> waters) {
			for (ImageTile i : motor.getTileList())
				if (i.getPosition().equals(getPosition()) && i instanceof Fire) {
					aux.add(i);
					waters.add(new Agua(new Point2D(getPosition().getX(), getPosition().getY()),"water_up"));
				}
	}
}