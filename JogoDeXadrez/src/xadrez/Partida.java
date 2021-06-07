package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	private int turno;
	private Cor vezDeQuem;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private PecaXadrez podeEnPassant;

	private List<Peca> pecasEmJogo = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		vezDeQuem = Cor.BRANCA;
		xeque = false;
		iniciarPecas();
	}

	public int getTurno() {
		return turno;
	}

	public Cor getVezDeQuem() {
		return vezDeQuem;
	}

	public boolean getXeque() {
		return xeque;
	}

	public boolean getXequeMate() {
		return xequeMate;
	}

	public PecaXadrez getPodeEnPassant() {
		return podeEnPassant;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] movPossiveis(Jogada origem) {
		Posicao pos = origem.lerPosicao();
		validarPosOrigem(pos);
		return tabuleiro.peca(pos).movPossiveis();
	}

	public PecaXadrez moverPeca(Jogada origem, Jogada destino) {
		Posicao origemDaPeca = origem.lerPosicao();
		Posicao destinoDaPeca = destino.lerPosicao();
		validarPosOrigem(origemDaPeca);
		validarPosDestino(origemDaPeca, destinoDaPeca);
		Peca capturada = execJogada(origemDaPeca, destinoDaPeca);

		if (taEmXeque(vezDeQuem)) {
			desfazJogada(origemDaPeca, destinoDaPeca, capturada);
			throw new XadrezException("Não pode se colocar em cheque");
		}

		PecaXadrez pecaQueMoveu = (PecaXadrez)tabuleiro.peca(destinoDaPeca);
		
		xeque = (taEmXeque(oponente(vezDeQuem))) ? true : false;

		if (taEmXequeMate(oponente(vezDeQuem))) {
			xequeMate = true;
		} else {
			proxJogada();
		}

		// En Passant
		if (pecaQueMoveu instanceof Peao && (destinoDaPeca.getLinha() == origemDaPeca.getLinha() - 2 || destinoDaPeca.getLinha() == origemDaPeca.getLinha() + 2)) {
			podeEnPassant = pecaQueMoveu;
		} else {
			podeEnPassant = null;
		}
		return (PecaXadrez) capturada;
	}

	private Peca execJogada(Posicao posOrigem, Posicao posDestino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(posOrigem);
		p.incrContagem();
		Peca capturada = tabuleiro.removePeca(posDestino);
		tabuleiro.colocarPeca(p, posDestino);
		if (capturada != null) {
			pecasEmJogo.remove(capturada);
			pecasCapturadas.add(capturada);
		}

		// Roque pequeno
		if (p instanceof Rei && posDestino.getColuna() == posOrigem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(posOrigem.getLinha(), posOrigem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(posOrigem.getLinha(), posOrigem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.incrContagem();
		}

		// Roque grande
		if (p instanceof Rei && posDestino.getColuna() == posOrigem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(posOrigem.getLinha(), posOrigem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(posOrigem.getLinha(), posOrigem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemTorre);
			tabuleiro.colocarPeca(torre, destinoTorre);
			torre.incrContagem();
		}

		// En Passant
		if (p instanceof Peao) {
			if (posOrigem.getColuna() != posDestino.getColuna() && capturada == null) {
				Posicao posPeao;
				if (p.getCor() == Cor.BRANCA) {
					posPeao = new Posicao(posDestino.getLinha() + 1, posDestino.getColuna());
				} else {
					posPeao = new Posicao(posDestino.getLinha() - 1, posDestino.getColuna());
				}
				capturada = tabuleiro.removePeca(posPeao);
				pecasCapturadas.add(capturada);
				pecasEmJogo.remove(capturada);
			}
		}
		return capturada;
	}

	private void desfazJogada(Posicao posOrigem, Posicao posDestino, Peca capturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(posDestino);
		p.decrContagem();
		tabuleiro.colocarPeca(p, posOrigem);
		if (capturada != null) {
			tabuleiro.colocarPeca(capturada, posDestino);
			pecasCapturadas.remove(capturada);
			pecasEmJogo.add(capturada);
		}

		// Desfazer Roque pequeno
		if (p instanceof Rei && posDestino.getColuna() == posOrigem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(posOrigem.getLinha(), posOrigem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(posOrigem.getLinha(), posOrigem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.decrContagem();
		}

		// Desfazer Roque grande
		if (p instanceof Rei && posDestino.getColuna() == posOrigem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(posOrigem.getLinha(), posOrigem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(posOrigem.getLinha(), posOrigem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(destinoTorre);
			tabuleiro.colocarPeca(torre, origemTorre);
			torre.decrContagem();
		}

		// En Passant
		if (p instanceof Peao) {
			if (posOrigem.getColuna() != posDestino.getColuna() && capturada == podeEnPassant) {
				PecaXadrez peao = (PecaXadrez)tabuleiro.removePeca(posDestino);
				Posicao posPeao;
				if (p.getCor() == Cor.BRANCA) {
					posPeao = new Posicao(3, posDestino.getColuna());
				} else {
					posPeao = new Posicao(4, posDestino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posPeao);
			}
		}
	}

	private void validarPosOrigem(Posicao pos) {
		if (!tabuleiro.temPeca(pos)) {
			throw new XadrezException("Não existe peça nessa posição");
		}
		if (vezDeQuem != ((PecaXadrez) tabuleiro.peca(pos)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua");
		}
		if (!tabuleiro.peca(pos).temComoMexerEssaPeca()) {
			throw new XadrezException("Não há movimentos possíveis.");
		}
	}

	private void validarPosDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).podeMover(destino)) {
			throw new XadrezException("Não é possível mover essa peça pra essa posição");
		}
	}

	private void proxJogada() {
		turno++;
		vezDeQuem = (vezDeQuem == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}

	private PecaXadrez rei(Cor cor) {
		List<Peca> lista = pecasEmJogo.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Erro: Não existe rei da cor " + cor);
	}

	private boolean taEmXeque(Cor cor) {
		Posicao posicaoDoRei = rei(cor).getJogada().lerPosicao();
		List<Peca> pecasInimigas = pecasEmJogo.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasInimigas) {
			boolean[][] matriz = p.movPossiveis();
			if (matriz[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean taEmXequeMate(Cor cor) {
		if (!taEmXeque(cor)) {
			return false;
		}
		List<Peca> lista = pecasEmJogo.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] matriz = p.movPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((PecaXadrez) p).getJogada().lerPosicao();
						Posicao destino = new Posicao(i, j);
						Peca capturada = execJogada(origem, destino);
						boolean testarXeque = taEmXeque(cor);
						desfazJogada(origem, destino, capturada);
						if (!testarXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new Jogada(coluna, linha).lerPosicao());
		pecasEmJogo.add(peca);
	}

	private void iniciarPecas() {
		colocaNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA, this));
		colocaNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocaNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocaNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocaNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocaNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocaNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocaNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocaNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocaNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA, this));

		colocaNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocaNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
		colocaNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETA));
		colocaNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA, this));
		colocaNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
		colocaNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocaNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));
		colocaNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocaNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA, this));
	}
}