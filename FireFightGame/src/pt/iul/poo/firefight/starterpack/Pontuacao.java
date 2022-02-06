package pt.iul.poo.firefight.starterpack;

public class Pontuacao extends PontuacoesMaximas{

	public static GameEngine motor = GameEngine.getInstance();

	private String nome;
	private int pontuacao;
	
	public Pontuacao(String nome, int pontuacao) {
		this.nome = nome;
		this.pontuacao = pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getNome() {
		return nome;
	}
	
	public int getPontuacao() {
		return pontuacao;
	}
	
	@Override
	public String toString() {
		return "Jogador: " + nome.replaceAll("\\s+","") + " Pontuação: " + pontuacao;
	}

}
