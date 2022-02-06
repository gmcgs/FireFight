package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Pine extends Vegetation {

	private int counter = 10;

	public Pine(Point2D position) {
		super(0.15, position, "pine", 0, false, false, false);
	}

	@Override
	public boolean isBurnable() {
		if (getName().equals("burntpine"))
			return false;
		return true;
	}

	@Override
	public void setAblaze(double probabilidade) {
		if (isBurnable()) {
			double prob = Math.random();
			if (prob < getProbabilidade())
				setOnFire();
		}
	}

	@Override
	public void howLongTilAsh() {
		if (this.isBurning())
			this.counter--;
		else
			this.counter = 10;

		if (this.counter == 0) {
			setName("burntpine");
			makeunburnable();
			hasBecomeAsh();
		}
	}
}
