package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	private int turno;
	private Cor vezDeQuem;
	private Tabuleiro tabuleiro;
	private boolean xeque;

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

	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j <tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public boolean [][] movPossiveis(Jogada origem){
		Posicao pos = origem.lerPosicao();
		validarPosOrigem(pos);
		return tabuleiro.peca(pos).movPossiveis();
	}

	public PecaXadrez moverPeca (Jogada origem, Jogada destino) {
		Posicao origemDaPeca = origem.lerPosicao();
		Posicao destinoDaPeca = destino.lerPosicao();
		validarPosOrigem(origemDaPeca);
		validarPosDestino(origemDaPeca, destinoDaPeca);
		Peca capturada = execJogada(origemDaPeca, destinoDaPeca);

		if (taEmXeque(vezDeQuem)) {
			desfazJogada(origemDaPeca, destinoDaPeca, capturada);
			throw new XadrezException("Não pode se colocar em cheque");
		}

		xeque = (taEmXeque(oponente(vezDeQuem))) ? true : false;

		proxJogada();
		return (PecaXadrez)capturada;
	}

	private Peca execJogada(Posicao posOrigem, Posicao posDestino) {
		Peca p = tabuleiro.removePeca(posOrigem);
		Peca capturada = tabuleiro.removePeca(posDestino);
		tabuleiro.colocarPeca(p, posDestino);
		if (capturada != null) {
			pecasEmJogo.remove(capturada);
			pecasCapturadas.add(capturada);
		}
		return capturada;
	}

	private void desfazJogada(Posicao origem, Posicao destino, Peca capturada) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, origem);
		if (capturada != null) {
			tabuleiro.colocarPeca(capturada, destino);
			pecasCapturadas.remove(capturada);
			pecasEmJogo.add(capturada);
		}
	}

	private void validarPosOrigem (Posicao pos) {
		if (!tabuleiro.temPeca(pos)) {
			throw new XadrezException("Não existe peça nessa posição");
		}
		if (vezDeQuem != ((PecaXadrez)tabuleiro.peca(pos)).getCor()) {
			throw new XadrezException("A peça escolhida não é sua");
		}
		if (!tabuleiro.peca(pos).temComoMexerEssaPeca()) {
			throw new XadrezException("Não há movimentos possíveis.");
		}
	}

	private void validarPosDestino (Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).podeMover(destino)) {
			throw new XadrezException("Não é possível mover essa peça pra essa posição");
		}
	}

	private void proxJogada() {
		turno ++;
		vezDeQuem = (vezDeQuem == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}

	private Cor oponente (Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}

	private PecaXadrez rei (Cor cor) {
		List<Peca> lista = pecasEmJogo.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Erro: Não existe rei da cor " + cor);
	}

	private boolean taEmXeque (Cor cor) {
		Posicao posicaoDoRei = rei(cor).getJogada().lerPosicao();
		List<Peca> pecasInimigas = pecasEmJogo.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasInimigas) {
			boolean[][]matriz = p.movPossiveis();
			if (matriz[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private void colocaNovaPeca (char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new Jogada(coluna, linha).lerPosicao());
		pecasEmJogo.add(peca);
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