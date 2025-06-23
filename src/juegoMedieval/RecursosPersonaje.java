package juegoMedieval;

/**
 * Guarda las rutas a los ficheros de recursos graficos y de audio de un personaje.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public record RecursosPersonaje(String directorioSprites, String rutaSonidoAtaque, String[] rutasSonidosPaso) {
	
	/**
	 * Crea un objeto de recursos.
	 * @param directorioSprites ruta al directorio en el que se encuentran los
	 * sprites de animaciones del personaje.
	 * @param rutaSonidoAtaque ruta al fichero que contiene el sonido de ataque.
	 * @param rutasSonidosPaso rutas de los 2 ficheros que contienen sonidos de
	 * paso.
	 */
	public RecursosPersonaje(String directorioSprites, String rutaSonidoAtaque, String[] rutasSonidosPaso) {
		this.directorioSprites = directorioSprites;
		this.rutaSonidoAtaque = rutaSonidoAtaque;
		this.rutasSonidosPaso = rutasSonidosPaso;
		
		if (rutasSonidosPaso != null && rutasSonidosPaso.length != 2) {
			throw new IllegalArgumentException("Solo se aceptan 2 sonidos de paso");
		}
	}
	
	/**
	 * Crea un objeto de recursos sin sonidos de paso.
	 * @param directorioSprites
	 * @param rutaSonidoAtaque
	 */
	public RecursosPersonaje(String directorioSprites, String rutaSonidoAtaque) {
		this(directorioSprites, rutaSonidoAtaque, null);
	}
}
