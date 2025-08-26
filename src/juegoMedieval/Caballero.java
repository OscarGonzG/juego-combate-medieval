package juegoMedieval;

import java.awt.Color;
import java.awt.Point;

import j2d.JObjeto;
import j2d.mods.GuiaPorRaton;
import j2d.mods.IGuiadoPorRaton;
import j2d.mods.JObjetoVisNumBarra;
import j2d.mods.Temporizador;
import juegoMedieval.utils.UtilsDepuracion;

/**
 * Caballero controlado por el jugador.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public class Caballero extends AtacanteMele implements IGuiadoPorRaton {
	
	private static final String DIR_SPRITES = "resources/knight_blue/";
	private static final String RUTA_SONIDO_ATAQUE = "resources/sounds/attacks/sword_attack.wav";
	private static final String[] RUTAS_SONIDOS_PASO = {"resources/sounds/walking/dirt_chain_walk3.wav", "resources/sounds/walking/dirt_chain_walk2.wav"};
	
	private static final RecursosPersonaje RECURSOS = new RecursosPersonaje(
			DIR_SPRITES, RUTA_SONIDO_ATAQUE, RUTAS_SONIDOS_PASO,
			UtilsDepuracion.colorColisionadorCaballero());
	
	private static final int INDICE_Z = 30;	
	
	private GuiaPorRaton guiaRaton;
	
	private static final int ALTURA = 80;
	private static final int ANCHURA = 60;
	
	private static final int VELOCIDAD_BASE = 12;
	private static final int DIST_RATON_PARADA = 20;
	
	private static final int SALUD_BASE = 100;
	private static final int DANHO_BASE = 40;
	
	private static final int ALTURA_BARRAS_ESTADO = 5;
	
	private final JObjeto barraVida; 
	private final JObjeto barraRefescoAtaque;
	
	
	public Caballero(String nombre) {
		super(nombre, ANCHURA, ALTURA,
			new EstadisticasPersonaje(SALUD_BASE, DANHO_BASE, VELOCIDAD_BASE),
			new JObjetoVisNumBarra(ANCHURA, ALTURA_BARRAS_ESTADO,
						SALUD_BASE, Color.RED, Color.BLACK),
			new JObjetoVisNumBarra(ANCHURA, ALTURA_BARRAS_ESTADO,
					RecursosPersonaje.DURACION_ATAQUE_MS, Color.YELLOW, Color.YELLOW),
			RECURSOS);
		asignaZ(INDICE_Z);
		barraVida = getVisualizadorVida();
		barraRefescoAtaque = getVisualizadorRefrescoAtaque();
		adornoAnhade(barraRefescoAtaque, 0, 0);
		adornoAnhade(barraVida, 0, (int) (- 1.5 * ALTURA_BARRAS_ESTADO));
		guiaRaton = new GuiaPorRaton(this, VELOCIDAD_BASE, DIST_RATON_PARADA);
		asignaZ(INDICE_Z);
	}
	

	@Override
	public void pulsadoBotonCentral(Point p) {
		// metodo innecesario
	}

	@Override
	public void pulsadoBotonDerecho(Point p) {
		ataca();
		guiaRaton.pausaGuia();
	}

	@Override
	public Faccion getFaccion() {
		return Faccion.CABALLEROS;
	}
	
	@Override
	public void muere() {
		super.muere();
		escena().controladoRatonElimina(guiaRaton);
		guiaRaton.pausaGuia();
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
			guiaRaton.reanudaGuia();		
		}
	}
	
	@Override
	public void objetoIncluido() {
		super.objetoIncluido();
		escena().controladoRatonAnhade(guiaRaton);
	}
}
