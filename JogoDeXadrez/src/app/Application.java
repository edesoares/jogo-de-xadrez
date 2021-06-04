package app;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.Jogada;
import xadrez.Partida;
import xadrez.PecaXadrez;
import xadrez.XadrezException;

public class Application {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		Partida partida = new Partida();
		
		while (true) {
			try {
				UI.limpaTela();
				UI.imprimeTab(partida.getPecas());
				System.out.println();
				System.out.print("Peça: ");
				Jogada origem = UI.lerJogada(input);

				boolean [][] movPossiveis = partida.movPossiveis(origem);
				UI.limpaTela();
				UI.imprimeTab(partida.getPecas(), movPossiveis);

				System.out.println();
				System.out.print("Jogada: ");
				Jogada jogada = UI.lerJogada(input);
				PecaXadrez capturada = partida.moverPeca(origem, jogada);
			}
			catch (XadrezException e) {
				System.out.println(e.getMessage());
				input.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				input.nextLine();
			}
		}
	}
}