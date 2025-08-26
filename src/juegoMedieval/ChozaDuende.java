package juegoMedieval;

import j2d.mods.ControladorVida;
import j2d.mods.ITemporizado;
import j2d.mods.Temporizador;
import juegoMedieval.utils.UtilsDepuracion;

/**
 * Choza que genera duendes periodicamente.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public class ChozaDuende extends Edificio implements ITemporizado {

	private static final String DIR_SPRITE = "resources/goblin_hut/";
	private static final String RUTA_SONIDO_DESTRUCCION =
			"resources/sounds/building/wood_collapse.wav";
	private static final RecursosEdificio RECURSOS =
			new RecursosEdificio(DIR_SPRITE, 1, RUTA_SONIDO_DESTRUCCION,
					UtilsDepuracion.colorColisionadorDuende());

	public static int ANCHURA = 100;
	public static int ALTURA = 135;
	
	public static final int SALUD_BASE = 100;
	public static final int TIEMPO_GENERACION_MS = 8_000;
	public static final int TIEMPO_PRIMERA_GENERACION_MS = 1_000;
	
	private int duendesDentro = 4; // Duendes restantes en esta choza
	
	private Temporizador generadorDuendes = 
			new Temporizador(TIEMPO_PRIMERA_GENERACION_MS, this);
	
	public ChozaDuende() {
		super(null, ANCHURA, ALTURA, RECURSOS);
		setControladorVida(new ControladorVida(SALUD_BASE, this));
		generadorDuendes.iniciaCuenta();
	}

	@Override
	public Faccion getFaccion() {
		return Faccion.DUENDES;
	}

	@Override
	public void finTiempo(Temporizador temporizador) {
		if (estaMuerto()) {
			return;
		}
		EscenaCombate escena = (EscenaCombate) escena();
		escena.incluyeObj(new DuendePiromano(),
				x() + ANCHURA, y() + ALTURA - DuendePiromano.ALTURA);
		duendesDentro--;
		
		if (duendesDentro > 0) {
			temporizador.iniciaCuenta(TIEMPO_GENERACION_MS);
		}
	}
}
