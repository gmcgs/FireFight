package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.List;

import pt.iul.poo.firefight.starterpack.gui.ImageTile;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import pt.iul.poo.firefight.starterpack.gui.ImageTile;
import pt.iul.poo.firefight.starterpack.utils.Point2D;

public abstract class Vegetation extends GameElement implements Burnable {

	private double probabilidade;
	private boolean onFire;
	private boolean isBeingWatered;
	private boolean isAsh;

	public Vegetation(double probabilidade, Point2D position, String name, int layer, boolean onFire,
			boolean isBeingWatered, boolean isAsh) {
		super(position, name, layer);
		this.probabilidade = probabilidade;
		this.onFire = onFire;
		this.isBeingWatered = isBeingWatered;
		this.isAsh = isAsh;
	}

	public abstract void setAblaze(double probabilidade);

	public boolean setOnFire() {
		if (onFire) {
			onFire = false;
			return onFire;
		} else {
			onFire = true;
			return onFire;
		}
	}

	public void makeunburnable() {
		onFire = false;
	}

	public double getProbabilidade() {
		return probabilidade;
	}

	public boolean isBurning() {
		return onFire;
	}

	public boolean isBeingwatered() {
		if (isBeingWatered) {
			isBeingWatered = false;
			return isBeingWatered;
		} else {
			isBeingWatered = true;
			return isBeingWatered;
		}
	}

	public void hasBurned(String newName) {
		setName(newName);
	}

	public abstract void howLongTilAsh();
	
	public boolean isItAsh() {
		return isAsh;
	}
	
	public void hasBecomeAsh() {
		motor.removePoints();
		isAsh = true;
	}
	
	public static void theEnd() {
		List<Point2D> posicoes = new ArrayList<>();
		for (ImageTile k : motor.getTileList())
			if (k instanceof Vegetation && ((Vegetation) k).isBurning()) {
				String nomeAntigo = k.getName();
				((Vegetation) k).howLongTilAsh();
				if (k.getName().equals("burnt" + nomeAntigo)) {
					posicoes.add(k.getPosition());
				}
			}
		for (Point2D i : posicoes)
			Fire.apagarFogoNaPosicao(i);
	}

}
