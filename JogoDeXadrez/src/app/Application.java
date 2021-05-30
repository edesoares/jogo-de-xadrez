package app;

import xadrez.Partida;

public class Application {

	public static void main(String[] args) {

		Partida partida = new Partida();
		UI.imprimeTab(partida.getPecas());
	}

}