package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMoverSim(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(pos);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movPossiveis() {
		boolean[][]matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0,0);

		// Verifica acima
		p.definirPos(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Verifica abaixo
		p.definirPos(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Verifica esquerda
		p.definirPos(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Verifica direita
		p.definirPos(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Verifica superior esquerdo
		p.definirPos(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Verifica superior direito
		p.definirPos(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Verifica inferior esquerdo
		p.definirPos(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}

		// Verifica inferior direito
		p.definirPos(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posExiste(p) && podeMoverSim(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		return matriz;
	}
}