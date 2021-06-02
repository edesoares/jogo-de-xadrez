package app;

import java.util.Scanner;

import xadrez.Jogada;
import xadrez.Partida;
import xadrez.PecaXadrez;

public class Application {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		Partida partida = new Partida();
		
		while (true) {
			UI.imprimeTab(partida.getPecas());
			System.out.println();
			System.out.print("Peça: ");
			Jogada origem = UI.lerJogada(input);
			System.out.println();
			System.out.print("Jogada: ");
			Jogada jogada = UI.lerJogada(input);
			PecaXadrez capturada = partida.moverPeca(origem, jogada);
		}
	}
}