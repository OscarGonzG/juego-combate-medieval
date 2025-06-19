package juegoMedieval.utils;

import java.awt.Color;

/**
 * Clase de utilidad que sirve para mostrar u ocultar informacion de depuracion
 * durante el juego.
 */
public final class UtilsDepuracion {
	private UtilsDepuracion() {}
	
	private static boolean modoDepuracion = false;
	
	/**
	 * Activa el modo depuracion durante el resto de la partida. Debe llamarse
	 * antes de cargar la escena.
	 */
	public static void activaModoDepuracion() {
		modoDepuracion = true;
	}
	
	public static Color colorColisionadorCaballero() {
		return modoDepuracion ? Color.BLUE : null;
	}
	
	public static Color colorColisionadorDuende() {
		return modoDepuracion ? Color.GREEN : null;
	}
	
	public static Color colorAreaDanho() {
		return modoDepuracion ? new Color(255, 20, 20, 112) : null;
	}
}
