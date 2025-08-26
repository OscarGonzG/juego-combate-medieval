package juegoMedieval;

import java.awt.Color;
import java.awt.Image;

import j2d.utils.Animacion;
import j2d.utils.Diapositiva;
import j2d.utils.ImagenesUtils;
import j2d.utils.Sonido;

/**
 * Guarda las rutas a los ficheros graficos y de audio de un edificio.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public class RecursosEdificio {
	
	private static final int DURACION_FRAME_MS = 100;
	
	private final Animacion animacion;
	private final Image icono;
	private final Image iconoDestruido;
	private final Sonido sonidoDestruccion;
	private final Color colorColisionador;

	/**
	 * Crea un objeto de recursos de un edificio.
	 * @param directorioSprites directorio donde se encuentran los sprites del
	 * edificio.
	 * @param numSpritesAnimacion numero de sprites que componen la animacion
	 * del edificio o 1 si no hay animacion.
	 * @param rutaSonidoDestruccion ruta al fichero que contiene el sonido de
	 * destruccion del edificio.
	 * @param colorColisionador color del colisionador o {@code null} para que
	 * sea transparente.
	 */
	public RecursosEdificio (String directorioSprites, int numSpritesAnimacion,
			String rutaSonidoDestruccion, Color colorColisionador) {
		this.colorColisionador = colorColisionador;
		sonidoDestruccion = new Sonido(rutaSonidoDestruccion);
		icono = ImagenesUtils.creaImagen(directorioSprites + "standing1.png");
		iconoDestruido = ImagenesUtils.creaImagen(directorioSprites + "destroyed.png");
		if (numSpritesAnimacion > 1) {
			Diapositiva[] diapositivas = new Diapositiva[numSpritesAnimacion];
			diapositivas[0] = new Diapositiva(icono, 1, DURACION_FRAME_MS);
			for (int i = 1; i <= numSpritesAnimacion; i++) {
				diapositivas[i - 1] = new Diapositiva(ImagenesUtils.creaImagen(directorioSprites + "standing" + i + ".png"), 1, DURACION_FRAME_MS);
			}
			
			animacion = new Animacion(true, diapositivas);
		} else {
			animacion = null;
		}
	}

	/**
	 * Devuelve la animacion del edificio o {@code null} si no la tiene.
	 * @return la animacion del edificio o {@code null} si no la tiene.
	 */
	public Animacion animacion() {
		return animacion;
	}

	/**
	 * Devuelve el icono principal del edificio.
	 * @return el icono principal del edificio.
	 */
	public Image icono() {
		return icono;
	}

	/**
	 * Devuelve el icono del edificio destruido.
	 * @return el icono del edificio destruido.
	 */
	public Image iconoDestruido() {
		return iconoDestruido;
	}

	/**
	 * Devuelve el sonido de destruccion del edificio.
	 * @return el sonido de destruccion del edificio.
	 */
	public Sonido sonidoDestruccion() {
		return sonidoDestruccion;
	}
	
	/**
	 * Devuelve el color del colisionador del edificio o {@code null} si el
	 * colisionador debe ser transparente.
	 * @return el color del colisionador del edificio o {@code null} si el
	 * colisionador debe ser transparente.
	 */
	public Color colorColisionador() {
		return colorColisionador;
	}
	
}
