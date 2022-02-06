package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class MovableObject extends GameElement implements Movable {

	public MovableObject(Point2D position, String name, int layer) {
		super(position, name, layer);
	}
	
	// Verifica se a posicao p esta' dentro da grelha de jogo
		public boolean canMoveTo(Point2D p) {
			if (p.getX() < 0)
				return false;
			if (p.getY() < 0)
				return false;
			if (p.getX() >= GameEngine.GRID_WIDTH)
				return false;
			if (p.getY() >= GameEngine.GRID_HEIGHT)
				return false;
			return true;
		}
}
