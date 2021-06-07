package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private Partida partida;

	public Peao(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "p";
	}

	@Override
	public boolean[][] movPossiveis() {
		boolean[][]matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		// peças brancas
		if (getCor() == Cor.BRANCA) {
			p.definirPos(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posExiste(p) && !getTabuleiro().temPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.definirPos(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posExiste(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posExiste(p2) && !getTabuleiro().temPeca(p2) && getContagem() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.definirPos(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posExiste(p) && temInimigo(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.definirPos(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posExiste(p) && temInimigo(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// En Passant
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posExiste(esquerda) && temInimigo(esquerda) && getTabuleiro().peca(esquerda) == partida.getPodeEnPassant()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posExiste(direita) && temInimigo(direita) && getTabuleiro().peca(direita) == partida.getPodeEnPassant()) {
					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}

		// peças pretas
		} else {
			p.definirPos(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posExiste(p) && !getTabuleiro().temPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.definirPos(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posExiste(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posExiste(p2) && !getTabuleiro().temPeca(p2) && getContagem() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.definirPos(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posExiste(p) && temInimigo(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.definirPos(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posExiste(p) && temInimigo(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}

			// En Passant
			if (posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posExiste(esquerda) && temInimigo(esquerda) && getTabuleiro().peca(esquerda) == partida.getPodeEnPassant()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posExiste(direita) && temInimigo(direita) && getTabuleiro().peca(direita) == partida.getPodeEnPassant()) {
					matriz[direita.getLinha() + 1][esquerda.getColuna()] = true;
				}
			}
		}
		return matriz;
	}

}