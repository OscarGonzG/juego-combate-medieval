package juegoMedieval;


import java.util.HashMap;
import java.util.Map;

import j2d.JObjeto;
import j2d.JObjetoIcono;
import j2d.mods.IVidaControlada;
import j2d.utils.Animacion;
import j2d.utils.Diapositiva;
import j2d.utils.ImagenesUtils;
import j2d.utils.Sonido;

/**
 * Representa la "piel" de un personaje, es decir, su parte visible. Se encarga
 * de gestionar animaciones y sonidos.
 * 
 * Cada animacion se corresponde a uno o varios {@link Estado estados}.
 */
public class PielPersonaje extends JObjetoIcono implements IVidaControlada {
	
private static final String DIR_ANIMACIONES_MUERTE = "resources/death/";
	
	public static final Animacion ANIMACION_MUERTE = cargaAnimacionMuerte();
	
	private Map<Estado, Animacion> animacionesAccion = new HashMap<Estado, Animacion>();
	
	private static final int DURACION_FRAME_MS = 100;
	private static final int FRAMES_ANIMACION_ACCION = 6;
	private static final int FRAMES_ANIMACION_MUERTE = 7;
	
	protected static final int DURACION_ATAQUE_MS = PielPersonaje.DURACION_FRAME_MS * PielPersonaje.FRAMES_ANIMACION_ACCION;
	
	/**
	 * Crea una piel de personaje.
	 * @param nombre nombre del objeto.
	 * @param recursos recursos del personaje.
	 */
	public PielPersonaje(String nombre, RecursosPersonaje recursos) {
  		super(nombre, recursos.directorioSprites() + "idle1.png");
  		inicializaAnimacionesAccion(recursos);
	}
	
	private static Animacion cargaAnimacionMuerte() {
		Diapositiva[] muerte = new Diapositiva[2 * FRAMES_ANIMACION_MUERTE];
		for (int i = 0; i < FRAMES_ANIMACION_MUERTE - 1; i++) {
			muerte[i] = new Diapositiva(ImagenesUtils.creaImagen(DIR_ANIMACIONES_MUERTE + "death" + (i + 1) + ".png"), 1, DURACION_FRAME_MS);
		}
		muerte[FRAMES_ANIMACION_MUERTE - 1] = new Diapositiva(
				ImagenesUtils.creaImagen(DIR_ANIMACIONES_MUERTE + "death" + (FRAMES_ANIMACION_MUERTE) + ".png"), 1, DURACION_FRAME_MS * 10);
		
		for (int i = 0; i < FRAMES_ANIMACION_MUERTE; i++) {
			muerte[FRAMES_ANIMACION_MUERTE + i] = new Diapositiva(ImagenesUtils.creaImagen(DIR_ANIMACIONES_MUERTE + "despawn" + (i + 1) + ".png"), 1, DURACION_FRAME_MS);
		}

		return new Animacion(muerte);
	}
	
	private void inicializaAnimacionesAccion(RecursosPersonaje recursos) {
		String dirSprites = recursos.directorioSprites();
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
				if (recursos.rutaSonidoAtaque() != null)
					sonidoAtaque = new Sonido(recursos.rutaSonidoAtaque());
			} else if (i == 1) {
				if (recursos.rutasSonidosPaso() != null)
					sonidoPaso = new Sonido(recursos.rutasSonidosPaso()[0]);
			} else if (i == 4) {
				if (recursos.rutasSonidosPaso() != null)
					sonidoPaso = new Sonido(recursos.rutasSonidosPaso()[1]);
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
		animacionesAccion.put(Estado.PARADO_DERECHA, new Animacion(true, paradoDerecha));
		animacionesAccion.put(Estado.PARADO_ABAJO, new Animacion(true, paradoDerecha));
		animacionesAccion.put(Estado.PARADO_ARRIBA, new Animacion(true, paradoDerecha));
		animacionesAccion.put(Estado.PARADO_IZQUIERDA, new Animacion(true, paradoIzquierda));
		
		// CAMINANDO
		animacionesAccion.put(Estado.MOVIENDO_DERECHA, new Animacion(true, caminandoDerecha));
		animacionesAccion.put(Estado.MOVIENDO_ABAJO, new Animacion(true, caminandoDerecha));
		animacionesAccion.put(Estado.MOVIENDO_ARRIBA, new Animacion(true, caminandoDerecha));
		animacionesAccion.put(Estado.MOVIENDO_IZQUIERDA, new Animacion(true, caminandoIzquierda));
		
		// ATACANDO		
		animacionesAccion.put(Estado.ATACANDO_DERECHA, new Animacion(false, atacandoDerecha));
		animacionesAccion.put(Estado.ATACANDO_ABAJO, new Animacion(false, atacandoAbajo));
		animacionesAccion.put(Estado.ATACANDO_ARRIBA, new Animacion(false, atacandoArriba));
		animacionesAccion.put(Estado.ATACANDO_IZQUIERDA, new Animacion(false, atacandoIzquierda));
	}
	
	/**
	 * Cambia la animacion que se esta reproduciendo.
	 * @param estado el estado correspondiente a la animacion deseada.
	 */
	public void cambiaAnimacion(Estado estado) {
		animador().reproduce(animacionesAccion.get(estado));
	}

	@Override
	public void pierdeVida(float decrementoVida) {
		objMaestro().escena().incluyeObj(new IndicadorDanho((int) decrementoVida),
				objMaestro().centro().x, objMaestro().centro().y);	/// FIXME arreglar escena() para adornos
	}

	@Override
	public void recuperaVida(float incrementoVida) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vidaAgotada() {
		((Personaje) objMaestro()).muere();
	}

	@Override
	public void finalizaAnimacionMuerte() {
		JObjeto personaje = objMaestro();
		personaje.escena().eliminaObj(personaje);
	}
	
}
