package tabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar tabuleiro!! Numero incorreto de linhas e colunas.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca (int linha, int coluna) {
		if (!posExiste(linha, coluna)) {
			throw new TabuleiroException("Posição inválida! Não existe.");
		}
		return pecas[linha][coluna];
	}

	public Peca peca (Posicao posicao) {
		if (!posExiste(posicao)) {
			throw new TabuleiroException("Posição inválida! Não existe.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public void colocarPeca (Peca peca, Posicao pos) {
		if (temPeca(pos)) {
			throw new TabuleiroException("Já existe uma peça na posição "+pos);
		}
		pecas[pos.getLinha()][pos.getColuna()] = peca;
		peca.posicao = pos;
	}

	public boolean posExiste (int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >=0 && coluna < colunas;
	}

	public boolean posExiste (Posicao pos) {
		return posExiste(pos.getLinha(), pos.getColuna());
	}

	public boolean temPeca (Posicao pos) {
		if (!posExiste(pos)) {
			throw new TabuleiroException("Posição inválida! Não existe.");
		}
		return peca(pos) != null;
	}
}