package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Eucalipto extends Vegetation {

	private int counter = 5;

	public Eucalipto(Point2D position) {
		super(0.10, position, "eucaliptus", 0, false, false, false);
	}

	@Override
	public boolean isBurnable() {
		if (getName().equals("burnteucaliptus"))
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

		if (this.counter == 0) {
			this.setName("burnteucaliptus");
			makeunburnable();
			this.hasBecomeAsh();
		}
	}
}
