package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;

import java.util.List;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends GameElement {

	public Fire(Point2D position) {
		super(position, "fire", 2);
	}

	public List<ImageTile> fireSpreader(Fire fire) {
		List<ImageTile> aux = new ArrayList<>();
		List<Point2D> pontosAVolta = fire.getPosition().getNeighbourhoodPoints();
		for (ImageTile i : motor.getTileList()) {
			if (pontosAVolta.contains(i.getPosition()) && i instanceof Vegetation && ((Vegetation) i).isBurnable()
					&& !((Vegetation) i).isBurning() && !motor.getFireman().getPosition().equals(i.getPosition())) {
				((Vegetation) i).setAblaze(((Vegetation) i).getProbabilidade());
				if (((Vegetation) i).isBurning()) {
					aux.add(new Fire(i.getPosition()));
				}
			}
		}
		return aux;
	}

	public static void apagarFogoNaPosicao(Point2D position) {
		List<ImageTile> aux = new ArrayList<>();
		for (ImageTile i : motor.getTileList())
			if (i.getPosition().equals(position) && i instanceof Fire) {
				motor.getGUI().removeImage(i);
				aux.add(i);
			}
		motor.removeListFromExistence(aux);
	}

	public void actuallySetOnFire() {
		List<ImageTile> aux = new ArrayList<>();
		for (ImageTile i : motor.getTileList()) {
			if (i instanceof Fire) {
				aux.addAll(((Fire) i).fireSpreader(((Fire) i)));
			}
		}
		motor.addListToTileList(aux);
		motor.addListToGUI(aux);
	}

}
