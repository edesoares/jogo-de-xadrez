package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca{

	private Cor cor;
	private int contagem;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	public int getContagem() {
		return contagem;
	}

	public void incrContagem() {
		contagem++;
	}

	public void decrContagem() {
		contagem--;
	}

	public Jogada getJogada () {
		return Jogada.computarJogada(posicao);
	}

	protected boolean temInimigo(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(pos);
		return p != null && p.getCor() != cor;
	}

}