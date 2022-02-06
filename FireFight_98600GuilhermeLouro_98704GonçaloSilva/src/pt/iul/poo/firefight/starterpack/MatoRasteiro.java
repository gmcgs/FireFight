package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class MatoRasteiro extends Vegetation {

	private int counter = 3;

	public MatoRasteiro(Point2D position) {
		super(0.05, position, "grass", 0, false, false, false);
	}

	@Override
	public boolean isBurnable() {
		if (getName().equals("burntgrass"))
			return false;

		return true;
	}

	@Override
	public void setAblaze(double probabilidade) {
		if (isBurnable()) {
			double prob = Math.random();
			if (prob < probabilidade)
				setOnFire();
		}
	}

	@Override
	public void howLongTilAsh() {

		if (this.isBurning())
			this.counter--;

		if (counter == 0) {
			this.setName("burntgrass");
			makeunburnable();
			this.hasBecomeAsh();
		}
	}
}
