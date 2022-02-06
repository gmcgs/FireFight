package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Fireman extends MovableObject {

	public Fireman(Point2D position) {
		super(position, "fireman", 3);
	}

	// Move numa direcao aleatoria
	@Override
	public void move(int key) {

		switch (key) {
		case KeyEvent.VK_RIGHT:
			if (canMoveTo(getPosition().plus(Direction.RIGHT.asVector())) && !isFiremanGonnaBeOnFire(key, getPosition()))
				setPosition(getPosition().plus(Direction.RIGHT.asVector()));
			Agua agua = new Agua(new Point2D(getPosition().getX(), getPosition().getY()), "water_right");
			agua.daslkfa(Direction.RIGHT);
			break;
		case KeyEvent.VK_LEFT:
			if (canMoveTo(getPosition().plus(Direction.LEFT.asVector())) && !isFiremanGonnaBeOnFire(key, getPosition()))
				setPosition(getPosition().plus(Direction.LEFT.asVector()));
			Agua aguaLeft = new Agua(new Point2D(getPosition().getX(), getPosition().getY()), "water_left");
			aguaLeft.daslkfa(Direction.LEFT);
			break;
		case KeyEvent.VK_UP:
			if (canMoveTo(getPosition().plus(Direction.UP.asVector())) && !isFiremanGonnaBeOnFire(key, getPosition()))
				setPosition(getPosition().plus(Direction.UP.asVector()));
			Agua aguaUp = new Agua(new Point2D(getPosition().getX(), getPosition().getY()), "water_up");
			aguaUp.daslkfa(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			if (canMoveTo(getPosition().plus(Direction.DOWN.asVector())) && !isFiremanGonnaBeOnFire(key, getPosition()))
				setPosition(getPosition().plus(Direction.DOWN.asVector()));
			Agua aguaDown = new Agua(new Point2D(getPosition().getX(), getPosition().getY()), "water_down");
			aguaDown.daslkfa(Direction.DOWN);
			break;
		}
	}

	public boolean isFiremanGonnaBeOnFire(int key, Point2D position) {
		List<Point2D> foguinhos = new ArrayList<>();
		for (int i = 0; i < motor.getTileList().size(); i++) {
			if (motor.getTileList().get(i).getName().equals("fire"))
				foguinhos.add(motor.getTileList().get(i).getPosition());
		}
		switch (key) {
		case KeyEvent.VK_RIGHT:
			Point2D adireita = new Point2D(position.getX() + 1, position.getY());
			if (foguinhos.contains(adireita))
				return true;
			else
				return false;
		case KeyEvent.VK_LEFT:
			Point2D aesquerda = new Point2D(position.getX() - 1, position.getY());
			if (foguinhos.contains(aesquerda))
				return true;
			else
				return false;
		case KeyEvent.VK_UP:
			Point2D acima = new Point2D(position.getX(), position.getY() - 1);
			if (foguinhos.contains(acima))
				return true;
			else
				return false;
		case KeyEvent.VK_DOWN:
			Point2D abaixo = new Point2D(position.getX(), position.getY() + 1);
			if (foguinhos.contains(abaixo))
				return true;
			else
				return false;
		}

		return false;

	}

	@Override
	public boolean isMovable() {
		return true;
	}
}