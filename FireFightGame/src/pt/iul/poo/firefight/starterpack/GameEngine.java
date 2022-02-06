package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Point2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!

public class GameEngine implements Observer {

	private static GameEngine INSTANCE;

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<ImageTile> tileList; // Lista de imagens
	private Fireman fireman; // Referencia para o bombeiro
	private Bulldozer theBulldozer;
	private List<ImageTile> bulldozer = new ArrayList<>();
	private List<ImageTile> planes = new ArrayList<>();
	private boolean enterable = true;
	private boolean isIn = false;
	private int counter = -1;
	private String nome;
	private File[] files = new File("levels").listFiles();
	private int pontos;
	private Pontuacao pontuacao;

	private GameEngine() {

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI
		tileList = new ArrayList<>();
	}

	public static GameEngine getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GameEngine();
		}
		return INSTANCE;
	}

	public List<ImageTile> getTileList() {
		return tileList;
	}

	public void setTileList(List<ImageTile> aux) {
		tileList = aux;
	}

	public ImageMatrixGUI getGUI() {
		return gui;
	}

	public void addListToTileList(List<ImageTile> aux) {
		tileList.addAll(aux);
	}

	public void addToTileList(ImageTile tile) {
		tileList.add(tile);
	}

	public void removeListFromExistence(List<ImageTile> aux) {
		tileList.removeAll(aux);
		gui.removeImages(aux);
	}

	public void removeFromExistence(ImageTile aux) {
		tileList.remove(aux);
		gui.removeImage(aux);
	}

	public void addListToGUI(List<ImageTile> aux) {
		gui.addImages(aux);
	}

	public void addToGUI(ImageTile aux) {
		gui.addImage(aux);
	}

	public void removeListFromGUI(List<ImageTile> aux) {
		gui.removeImages(aux);
	}

	public void removeFromGUI(ImageTile aux) {
		gui.removeImage(aux);
	}

	public Fireman getFireman() {
		return fireman;
	}

	public void removePoints() {
		pontos = pontos - 25;
	}

	public void addPoints() {
		pontos = pontos + 25;
	}

	public String getNome() {
		return nome;
	}

	public int getPontuacao() {
		return pontos;
	}

	public void setPontuacao(int pontos) {
		this.pontos = pontos;
	}

	public int getMapa() {
		return counter;
	}

	public List<ImageTile> listaDeFogos() {
		List<ImageTile> aux = new ArrayList<>();
		tileList.forEach(s1 -> {
			if (s1 instanceof Fire)
				aux.add(s1);
		});
		return aux;
	}

	public int colunaComMaisFogos() {
		List<ImageTile> listaDeMaisFogos = listaDeFogos();
		int[] colunas = new int[10];
		int indice = 0;
		Iterator<ImageTile> it = listaDeMaisFogos.iterator();
		while (it.hasNext()) {
			ImageTile i = it.next();
			colunas[i.getPosition().getX()]++;
		}
		for (int i = 1; i < colunas.length; i++)
			if (colunas[i - 1] < colunas[i])
				indice = i;
		return indice;
	}

	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado
	// (neste caso seria a GUI)
	@Override
	public void update(Observed source) {
		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada
		gui.setStatusMessage("Nome: " + nome + "   Pontuação: " + Integer.toString(pontos));
		removeAguas();
		planeInteractions(key);
		bulldozerInteractions(key);
		// movimento bombeiro
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
			if (isIn == false) {
				(fireman).move(key);
				enterable = true;
			} else {
				((Bulldozer) theBulldozer).move(key);
			}
			spread();
			try {
				destroyLevel();
			} catch (FileNotFoundException e) {
			}
			Vegetation.theEnd();
		}
	}

	// Criacao dos objetos e envio das imagens para GUI
	public void start() throws FileNotFoundException {
		createTheTerrain(); // criar mapa do terreno
		sendImagesToGUI(); // enviar as imagens para a GUI
		update(gui);
	}

	// Criacao do terreno - so' pinheiros neste exemplo
	private void createTheTerrain() throws FileNotFoundException {
		Scanner s = new Scanner(loadLevel());
		int i = 0;
		while (s.hasNextLine()) {
			if (i < 10) {
				char[] linha = s.nextLine().toCharArray();
				for (int col = 0; col < 10; col++) {
					switch (linha[col]) {
					case 'p':
						tileList.add(new Pine(new Point2D(col, i)));
						break;
					case 'm':
						tileList.add(new MatoRasteiro(new Point2D(col, i)));
						break;
					case 'e':
						tileList.add(new Eucalipto(new Point2D(col, i)));
						break;
					case '_':
						tileList.add(new Terra(new Point2D(col, i)));
						break;
					default:
						break;
					}
				}
				i++;
			} else {
				String objeto = s.next().trim();
				int x = s.nextInt();
				int y = s.nextInt();
				if (objeto.equals("Fireman")) {
					tileList.add(fireman = new Fireman(new Point2D(x, y)));
				} else if (objeto.equals("Fire")) {
					Point2D point = new Point2D(x, y);
					tileList.forEach(f -> {
						if (f.getPosition().equals(point))
							((Vegetation) f).setOnFire();
					});
					tileList.add(new Fire(point));
				} else if (objeto.equals("Bulldozer")) {
					bulldozer.add(new Bulldozer(new Point2D(x, y)));
				}
			}
		}
		tileList.addAll(bulldozer);
		s.close();
	}

	public File loadLevel() {
		if (counter == -1) {
			nome = JOptionPane.showInputDialog("Enter player name:");
			if (nome.isEmpty()) {
				while (nome.isEmpty()) {
					nome = JOptionPane.showInputDialog("You need to have player name. Enter player name:");
				}
			}
			gui.setStatusMessage(nome);
		}
		counter++;
		if (counter < files.length) {
			return files[counter];
		}
		return null;
	}

	public void destroyLevel() throws FileNotFoundException {
		int m = 0;
		Iterator<ImageTile> it = tileList.iterator();
		while (it.hasNext()) {
			ImageTile i = it.next();
			if (i instanceof Fire)
				m++;
		}

		if (m == 0) {
			pontuacao = new Pontuacao(getNome(), getPontuacao());
			pontuacao.pontuacoesMaximas();
			gui.setMessage("Parabéns, passaste o nível " + (counter + 1) + " com " + getPontuacao() + " pontos!");
			setPontuacao(100);
			wipe();
			start();
		}
	}

	public void wipe() {
		if (counter == files.length - 1)
			System.exit(0);
		enterable = true;
		isIn = false;
		planes.clear();
		bulldozer.clear();
		tileList.clear();
		gui.clearImages();
	}

	public void removeAguas() {
		List<ImageTile> aux = new ArrayList<>();
		for (ImageTile i : tileList) {
			if (i instanceof Agua) {
				aux.add(i);
			}
		}
		tileList.removeAll(aux);
		gui.removeImages(aux);
	}

	public void spread() {
		List<ImageTile> aux = new ArrayList<>();
		for (ImageTile i : tileList) {
			if (i instanceof Fire) {
				aux.addAll(((Fire) i).fireSpreader(((Fire) i)));
			}
		}
		tileList.addAll(aux);
		gui.addImages(aux);
	}

	public void bulldozerInteractions(int key) {
		if (fireman != null && bulldozer != null)
			if (isIn == false) {
				for (ImageTile i : bulldozer)
					if (enterable && (fireman.getPosition().equals(i.getPosition()))) {
						theBulldozer = (Bulldozer) i;
						gui.removeImage(fireman);
						isIn = true;
					}
				// sai
			} else if (key == KeyEvent.VK_ENTER) {
				fireman.setPosition(theBulldozer.getPosition());
				gui.addImage(fireman);
				isIn = false;
				enterable = false;
			}
	}

	public void planeInteractions(int key) {
		if (planes != null)
			planes.forEach(i -> ((Aviao) i).move(key));

		// aviao
		if (key == KeyEvent.VK_P) {
			Aviao plane = new Aviao(new Point2D(colunaComMaisFogos(), GRID_HEIGHT - 1));
			planes.add(plane);
			tileList.addAll(planes);
			gui.addImages(planes);
		}
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		gui.addImages(tileList);
		gui.update();
	}
}