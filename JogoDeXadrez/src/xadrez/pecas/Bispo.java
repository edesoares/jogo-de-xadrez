package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "b";
	}

	@Override
	public boolean[][] movPossiveis() {
		boolean[][]matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);

		// verifica diagonal superior esquerda
		p.definirPos(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posExiste(p) && !getTabuleiro().temPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.definirPos(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posExiste(p) && temInimigo(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verifica diagonal superior esquerda
		p.definirPos(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posExiste(p) && !getTabuleiro().temPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.definirPos(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posExiste(p) && temInimigo(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verifica diagonal inferior esquerda
		p.definirPos(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posExiste(p) && !getTabuleiro().temPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.definirPos(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posExiste(p) && temInimigo(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// verifica diagonal inferior direita
		p.definirPos(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posExiste(p) && !getTabuleiro().temPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.definirPos(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posExiste(p) && temInimigo(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		return matriz;
	}

}