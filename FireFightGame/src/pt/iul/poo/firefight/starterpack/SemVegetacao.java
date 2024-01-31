package pt.iul.poo.firefight.starterpack;

import pt.iul.poo.firefight.starterpack.utils.Point2D;
import pt.iul.poo.firefight.starterpack.gui.ImageTile;

public class SemVegetacao implements ImageTile {
	
	private Point2D position;

	public SemVegetacao(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return "land";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}
}

