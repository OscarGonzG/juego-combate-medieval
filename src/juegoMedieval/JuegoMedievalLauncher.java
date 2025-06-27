package juegoMedieval;

import j2d.Juego;
import juegoMedieval.utils.UtilsDepuracion;

/**
 * Launcher del juego medieval.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public class JuegoMedievalLauncher {
	public static void main(String[] args) {
		UtilsDepuracion.activaModoDepuracion();
		EscenaCombate escena = new EscenaCombate();
		Juego.anhadeEscena(escena);
		Juego.jugar();
	}
}
