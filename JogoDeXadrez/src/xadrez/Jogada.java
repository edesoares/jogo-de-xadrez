package xadrez;

import tabuleiro.Posicao;

public class Jogada {

	private char coluna;
	private int linha;

	public Jogada(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezException("Erro ao instanciar posição. Valor inválido.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	protected Posicao lerPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}

	protected static Jogada computarJogada (Posicao pos) {
		return new Jogada((char)('a' + pos.getColuna()), 8 - pos.getLinha());
	}

	@Override
	public String toString() {
		return "" + coluna + linha;
	}

}