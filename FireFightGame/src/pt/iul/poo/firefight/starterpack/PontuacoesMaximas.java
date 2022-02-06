package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PontuacoesMaximas {

	public static GameEngine motor = GameEngine.getInstance();

	public void pontuacoesMaximas() {
		List<Pontuacao> pontuacoesMaximas = new ArrayList<>();
		try {
			Scanner s = new Scanner(new File("PontuaçõesMáximasMapa" + motor.getMapa() + ".txt"));
			pontuacoesMaximas = getPontuacoesMaximas(pontuacoesMaximas, s);
			String aux = concatnator(pontuacoesMaximas);
			write(aux);
		} catch (FileNotFoundException e) {
			try {
				write("Jogador: " + motor.getNome().replaceAll("\\s+","") + " Pontuação: " + motor.getPontuacao());
			} catch (FileNotFoundException e1) {
			}
		}
	}
	
	private class ComparadorDePontuacoes implements Comparator<Pontuacao>{
		
		@Override
		public int compare(Pontuacao arg0, Pontuacao arg1) {
			return arg1.getPontuacao()-arg0.getPontuacao();
		}
		
	}

	public List<Pontuacao> getPontuacoesMaximas(List<Pontuacao> pontuacoesMaximas, Scanner s) {
		while (s.hasNextLine()) {
			String[] aux = s.nextLine().split(" ");
			pontuacoesMaximas.add(new Pontuacao(aux[1].trim(), Integer.parseInt(aux[3].trim())));
		}
			pontuacoesMaximas.add(new Pontuacao(motor.getNome(), motor.getPontuacao()));
			
			pontuacoesMaximas.sort(new ComparadorDePontuacoes());

		return pontuacoesMaximas;
	}
	
	public String concatnator(List<Pontuacao> pontuacoesMaximas) {
		String aux = pontuacoesMaximas.get(0).toString();
		int i = 1;
		for (Pontuacao p : pontuacoesMaximas) {
			if (i > 1 && i <= 5) {
				aux = aux.concat(System.lineSeparator() + p.toString());
			}
			i++;
		}
		return aux;
	}

	public void write(String aux) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File("PontuaçõesMáximasMapa" + motor.getMapa() + ".txt"));
		writer.println(aux);
		writer.close();
	}
}
