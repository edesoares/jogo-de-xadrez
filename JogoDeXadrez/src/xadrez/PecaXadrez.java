package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca{

	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

	protected boolean temInimigo(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(pos);
		return p != null && p.getCor() != cor;
	}

}