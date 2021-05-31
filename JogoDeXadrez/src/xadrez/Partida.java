package xadrez;

import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	private Tabuleiro tabuleiro;

	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciarPecas();
	}

	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j <tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	private void colocaNovaPeca (char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new Jogada(coluna, linha).lerPosicao());
	}

	private void iniciarPecas() {
		colocaNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocaNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocaNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocaNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCA));

        colocaNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETA));
        colocaNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETA));
        colocaNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETA));
        colocaNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETA));
        colocaNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETA));
        colocaNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETA));
	}
}