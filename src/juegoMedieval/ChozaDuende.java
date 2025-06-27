package juegoMedieval;

import j2d.mods.ControladorVida;
import j2d.mods.ITemporizado;
import j2d.mods.Temporizador;
import juegoMedieval.utils.UtilsDepuracion;

/**
 * Choza que genera duendes periodicamente.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public class ChozaDuende extends Edificio implements ITemporizado {
	
	public static int ANCHO_CHOZA = 100;
	public static int ALTO_CHOZA = 135;
	
	public static final int SALUD_BASE = 100;
	public static final int TIEMPO_GENERACION_MS = 8_000;
	public static final int TIEMPO_PRIMERA_GENERACION_MS = 1_000;
	public static final int MAX_DUENDES_TOTAL = 5;

	private static int chozasCreadas = 0; // Todas las chozas creadas
	
	private int duendesGenerados = 0; // Duendes generados por esta choza
	
	private Temporizador generadorDuendes = new Temporizador(TIEMPO_PRIMERA_GENERACION_MS, this);
	
	private static final RecursosEdificio RECURSOS = new RecursosEdificio("resources/goblin_hut/", 1, "resources/sounds/building/wood_collapse.wav");

	public ChozaDuende() {
		super("choza" + (chozasCreadas + 1), ANCHO_CHOZA, ALTO_CHOZA, RECURSOS, UtilsDepuracion.colorColisionadorDuende());
		chozasCreadas++;
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
		escena.incluyeObj(new DuendePiromano("duende" + duendesGenerados + "-" + nombre()), x() + ANCHO_CHOZA, y() + ALTO_CHOZA - DuendePiromano.ALTURA_DUENDE);
		duendesGenerados++;
		
		if (duendesGenerados < MAX_DUENDES_TOTAL) {
			temporizador.iniciaCuenta(TIEMPO_GENERACION_MS);
		}
	}
}
