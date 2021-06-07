package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "p";
	}

	@Override
	public boolean[][] movPossiveis() {
		boolean[][]matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
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
		}
		return matriz;
	}

}