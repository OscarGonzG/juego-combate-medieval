package juegoCombateMedieval;

import java.awt.Color;
import java.awt.Image;
import java.util.EnumMap;

import j2d.utils.Animacion;
import j2d.utils.Diapositiva;
import j2d.utils.ImagenesUtils;
import j2d.utils.Sonido;
import juegoCombateMedieval.Estado.Accion;
import juegoCombateMedieval.Estado.Direccion;

/**
 * Guarda los recursos graficos y de audio de un personaje.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public class RecursosPersonaje {
	
	public static final Animacion ANIMACION_MUERTE = cargaAnimacionMuerte();
	private static final String DIR_ANIMACIONES_MUERTE = "resources/death/";
	
	private static final int FRAMES_ANIMACION_ACCION = 6;
	private static final int FRAMES_ANIMACION_MUERTE = 7;
	private static final int DURACION_FRAME_MS = 100;
	public static final int DURACION_ATAQUE_MS = DURACION_FRAME_MS * FRAMES_ANIMACION_ACCION;
	

	private final EnumMap<Accion, EnumMap<Direccion, Animacion>> animacionesAccion = new EnumMap<>(Accion.class);
	private final Color colorColisionador;
	
	/**
	 * Crea un objeto de recursos.
	 * @param dirSprites ruta al directorio en el que se encuentran los
	 * sprites de animaciones del personaje.
	 * @param rutaSonidoAtaque ruta al fichero que contiene el sonido de ataque.
	 * @param rutasSonidosPaso rutas de los 2 ficheros que contienen sonidos de
	 * paso.
	 * @param colorColisionador color del colisionador o {@code null} para que
	 * sea transparente.
	 */
	public RecursosPersonaje(String dirSprites, String rutaSonidoAtaque,
			String[] rutasSonidosPaso, Color colorColisionador) {
		this.colorColisionador = colorColisionador;
		// Crea las animaciones
		Diapositiva[] paradoDerecha = new Diapositiva[FRAMES_ANIMACION_ACCION];
		Diapositiva[] paradoIzquierda = new Diapositiva[FRAMES_ANIMACION_ACCION];
		Diapositiva[] caminandoDerecha = new Diapositiva[FRAMES_ANIMACION_ACCION];
		Diapositiva[] caminandoIzquierda = new Diapositiva[FRAMES_ANIMACION_ACCION];
		Diapositiva[] atacandoDerecha = new Diapositiva[FRAMES_ANIMACION_ACCION];
		Diapositiva[] atacandoIzquierda = new Diapositiva[FRAMES_ANIMACION_ACCION];
		Diapositiva[] atacandoAbajo = new Diapositiva[FRAMES_ANIMACION_ACCION];
		Diapositiva[] atacandoArriba = new Diapositiva[FRAMES_ANIMACION_ACCION];
		for (int i = 0; i < FRAMES_ANIMACION_ACCION; i++) {
			Sonido sonidoPaso = null;
			Sonido sonidoAtaque = null;
			if (i == 2) {
				if (rutaSonidoAtaque != null)
					sonidoAtaque = new Sonido(rutaSonidoAtaque);
			} else if (i == 1) {
				if (rutasSonidosPaso != null)
					sonidoPaso = new Sonido(rutasSonidosPaso[0]);
			} else if (i == 4) {
				if (rutasSonidosPaso != null)
					sonidoPaso = new Sonido(rutasSonidosPaso[1]);
			}
			paradoDerecha[i] = new Diapositiva(ImagenesUtils.creaImagen(dirSprites + "idle" + (i + 1) + ".png"), 1, DURACION_FRAME_MS);
			caminandoDerecha[i] = new Diapositiva(ImagenesUtils.creaImagen(dirSprites + "walking" + (i + 1) + ".png"), 1, DURACION_FRAME_MS, sonidoPaso);
			
			
			paradoIzquierda[i] = new Diapositiva(ImagenesUtils.espejoIzqDer(paradoDerecha[i].imagen), 1, DURACION_FRAME_MS);
			caminandoIzquierda[i] = new Diapositiva(ImagenesUtils.espejoIzqDer(caminandoDerecha[i].imagen), 1, DURACION_FRAME_MS, sonidoPaso);
			
			atacandoDerecha[i] = new Diapositiva(ImagenesUtils.creaImagen(dirSprites + "attacking_horizontal_a" + (i + 1) + ".png"), 1, DURACION_FRAME_MS, sonidoAtaque);
			atacandoAbajo[i] = new Diapositiva(ImagenesUtils.creaImagen(dirSprites + "attacking_down_a" + (i + 1) + ".png"), 1, DURACION_FRAME_MS, sonidoAtaque);
			atacandoArriba[i] = new Diapositiva(ImagenesUtils.creaImagen(dirSprites + "attacking_up_a" + (i + 1) + ".png"), 1, DURACION_FRAME_MS, sonidoAtaque);
			atacandoIzquierda[i] = new Diapositiva(ImagenesUtils.espejoIzqDer(atacandoDerecha[i].imagen), 1, DURACION_FRAME_MS, sonidoAtaque);
		}
		// PARADO
		EnumMap<Direccion, Animacion> animacionesParado = new EnumMap<Direccion, Animacion>(Direccion.class);
		animacionesAccion.put(Accion.PARADO, animacionesParado);
		
		animacionesParado.put(Direccion.DERECHA, new Animacion(true, paradoDerecha));
		animacionesParado.put(Direccion.IZQUIERDA, new Animacion(true, paradoIzquierda));
		
		// CAMINANDO
		EnumMap<Direccion, Animacion> animacionesCaminando = new EnumMap<Direccion, Animacion>(Direccion.class);
		animacionesAccion.put(Accion.CAMINANDO, animacionesCaminando);
		animacionesCaminando.put(Direccion.DERECHA, new Animacion(true, caminandoDerecha));
		animacionesCaminando.put(Direccion.IZQUIERDA, new Animacion(true, caminandoIzquierda));
		
		// ATACANDO
		EnumMap<Direccion, Animacion> animacionesAtacando = new EnumMap<Direccion, Animacion>(Direccion.class);
		animacionesAccion.put(Accion.ATACANDO, animacionesAtacando);
		animacionesAtacando.put(Direccion.DERECHA, new Animacion(false, atacandoDerecha));
		animacionesAtacando.put(Direccion.ABAJO, new Animacion(false, atacandoAbajo));
		animacionesAtacando.put(Direccion.ARRIBA, new Animacion(false, atacandoArriba));
		animacionesAtacando.put(Direccion.IZQUIERDA, new Animacion(false, atacandoIzquierda));
		
		
		if (rutasSonidosPaso != null && rutasSonidosPaso.length != 2) {
			throw new IllegalArgumentException("Solo se aceptan 2 sonidos de paso");
		}
	}
	
	/**
	 * Crea un objeto de recursos sin sonidos de paso.
	 * @param directorioSprites
	 * @param rutaSonidoAtaque
	 */
	public RecursosPersonaje(String directorioSprites, String rutaSonidoAtaque, Color colorColisionador) {
		this(directorioSprites, rutaSonidoAtaque, null, colorColisionador);
	}
	
	public Image icono() {
		return animacionesAccion.get(Accion.PARADO).get(Direccion.DERECHA).get(0).imagen;
	}
	
	public EnumMap<Accion, EnumMap<Direccion, Animacion>> animacionesAccion() {
		return animacionesAccion;
	}
	
	private static Animacion cargaAnimacionMuerte() {
		Diapositiva[] muerte = new Diapositiva[2 * RecursosPersonaje.FRAMES_ANIMACION_MUERTE];
		for (int i = 0; i < RecursosPersonaje.FRAMES_ANIMACION_MUERTE - 1; i++) {
			muerte[i] = new Diapositiva(ImagenesUtils.creaImagen(RecursosPersonaje.DIR_ANIMACIONES_MUERTE + "death" + (i + 1) + ".png"), 1, RecursosPersonaje.DURACION_FRAME_MS);
		}
		muerte[RecursosPersonaje.FRAMES_ANIMACION_MUERTE - 1] = new Diapositiva(
				ImagenesUtils.creaImagen(RecursosPersonaje.DIR_ANIMACIONES_MUERTE + "death" + (RecursosPersonaje.FRAMES_ANIMACION_MUERTE) + ".png"), 1, RecursosPersonaje.DURACION_FRAME_MS * 10);
		
		for (int i = 0; i < RecursosPersonaje.FRAMES_ANIMACION_MUERTE; i++) {
			muerte[RecursosPersonaje.FRAMES_ANIMACION_MUERTE + i] = new Diapositiva(ImagenesUtils.creaImagen(RecursosPersonaje.DIR_ANIMACIONES_MUERTE + "despawn" + (i + 1) + ".png"), 1, RecursosPersonaje.DURACION_FRAME_MS);
		}
		
		return new Animacion(muerte);
	}
	
	/**
	 * Devuelve el color del colisionador del personaje o {@code null} si el
	 * colisionador debe ser transparente.
	 * @return el color del colisionador del personaje o {@code null} si el
	 * colisionador debe ser transparente.
	 */
	public Color colorColisionador() {
		return colorColisionador;
	}
}
