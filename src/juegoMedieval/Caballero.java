package juegoMedieval;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import j2d.JObjeto;
import j2d.Juego;
import j2d.mods.GuiaPorRaton;
import j2d.mods.IGuiadoPorRaton;
import j2d.mods.JObjetoVisNumBarra;
import j2d.mods.Temporizador;
import juegoMedieval.utils.UtilsDepuracion;

/**
 * Caballero controlado por el jugador.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public class Caballero extends AtacanteMele implements IGuiadoPorRaton {
	
	private static final String DIR_SPRITES = "resources/knight_blue/";
	private static final String RUTA_SONIDO_ATAQUE = "resources/sounds/attacks/sword_attack.wav";
	private static final String[] RUTAS_SONIDOS_PASO = {"resources/sounds/walking/dirt_chain_walk3.wav", "resources/sounds/walking/dirt_chain_walk2.wav"};
	private static final int INDICE_Z = 30;	
	
	private GuiaPorRaton guiaRaton;
	
	private static final int ALTURA_JUGADOR = 80;
	private static final int ANCHURA_JUGADOR = 60;
	
	public static final int VELOCIDAD_BASE = 12;
	
	public static final int SALUD_MAX_BASE = 100;
	public static final int DANHO_BASE = 40;
	
	public static final int ALTURA_BARRAS_ESTADO = 5;
	
	private final JObjeto barraVida; 
	private final JObjeto barraRefescoAtaque;
	
	
	public Caballero(String nombre) {
		super(nombre, ANCHURA_JUGADOR, ALTURA_JUGADOR,
				new EstadisticasPersonaje(SALUD_MAX_BASE, DANHO_BASE, VELOCIDAD_BASE),
				new JObjetoVisNumBarra(ANCHURA_JUGADOR, ALTURA_BARRAS_ESTADO, SALUD_MAX_BASE, Color.RED, Color.BLACK),
				new JObjetoVisNumBarra(ANCHURA_JUGADOR, ALTURA_BARRAS_ESTADO, 600, Color.YELLOW,  Color.YELLOW),
				new RecursosPersonaje(DIR_SPRITES, RUTA_SONIDO_ATAQUE, RUTAS_SONIDOS_PASO),
				UtilsDepuracion.colorColisionadorCaballero());
		asignaZ(INDICE_Z);
		barraVida = getVisualizadorVida();
		barraRefescoAtaque = getVisualizadorRefrescoAtaque();
		adornoAnhade(barraRefescoAtaque, 0, 0);
		adornoAnhade(barraVida, 0, (int) (- 1.5 * ALTURA_BARRAS_ESTADO));
		guiaRaton = new GuiaPorRaton(this, VELOCIDAD_BASE, 20);
		asignaZ(INDICE_Z);
	}
	
	public GuiaPorRaton getGuiaPorRaton() {
		return guiaRaton;
	}
	

	@Override
	public void pulsadoBotonCentral(Point p) {
		// metodo innecesario
	}

	@Override
	public void pulsadoBotonDerecho(Point p) {
		ataca();
		guiaRaton.ratonBotonSoltado(new MouseEvent(Juego.ventana(), 0, 0, 0, 0, 0, 0, 0, 0, false, 1)); /// XXX temporal
		escena().controladoRatonElimina(guiaRaton);
	}

	@Override
	public Faccion getFaccion() {
		return Faccion.CABALLEROS;
	}
	
	@Override
	public void muere() {
		super.muere();
		escena().controladoRatonElimina(guiaRaton);
		guiaRaton.ratonBotonSoltado(new MouseEvent(Juego.ventana(), 0, 0, 0, 0, 0, 0, 0, 0, false, 1)); /// XXX temporal
		adornoElimina(barraVida);
		adornoElimina(barraRefescoAtaque);
	}
	
	/**
	 * {@inheritDoc} Registra el {@link j2d.mods.GuiaPorRaton GuiaPorRaton} de
	 * {@link Caballero} como objeto controlado por raton en la escena tras
	 * haber sido eliminado al iniciar el ataque para evitar el movimiento.
	 */
	@Override
	public void finTiempo(Temporizador temporizador) {
		super.finTiempo(temporizador);
		if (!estaMuerto()) {
			// Si ataca justo antes de morir, no debemos volver a anhadir la guia
			escena().controladoRatonAnhade(guiaRaton);			
		}
	}
}
