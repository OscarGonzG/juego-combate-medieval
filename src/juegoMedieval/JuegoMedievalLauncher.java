package juegoMedieval;

import j2d.Juego;
import juegoMedieval.utils.UtilsDepuracion;

public class JuegoMedievalLauncher {
	public static void main(String[] args) {
		UtilsDepuracion.activaModoDepuracion();
		EscenaMedieval escena = new EscenaMedieval();
		Juego.anhadeEscena(escena);
		Juego.jugar();
	}
}
