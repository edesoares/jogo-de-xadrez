package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez{

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "c";
	}

	private boolean podeMoverSim(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(pos);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movPossiveis() {
		boolean[][]matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0,0);

		p.definirPos(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.definirPos(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.definirPos(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.definirPos(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.definirPos(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
 
		p.definirPos(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.definirPos(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		p.definirPos(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		return matriz;
	}
}