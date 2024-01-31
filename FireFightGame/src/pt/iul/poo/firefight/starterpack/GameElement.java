package pt.iul.poo.firefight.starterpack;

import pt.iul.poo.firefight.starterpack.gui.ImageTile;
import pt.iul.poo.firefight.starterpack.utils.Point2D;

public abstract class GameElement implements ImageTile {

	private Point2D position;
	private String name;
	private int layer;
	public static GameEngine motor = GameEngine.getInstance();

	public GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name = name;
		this.layer = layer;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
}