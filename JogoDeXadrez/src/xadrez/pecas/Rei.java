package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez{

	private Partida partida;

	public Rei(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMoverSim(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(pos);
		return p == null || p.getCor() != getCor();
	}

	private boolean testarRoqueTorre(Posicao pos) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(pos);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContagem() == 0;
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

		// Verificação do roque
		if (getContagem() == 0 && !partida.getXeque()) {
			// Roque pequeno
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testarRoqueTorre(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() +1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() +2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			// Roque grande
			Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testarRoqueTorre(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}
		return matriz;
	}
}