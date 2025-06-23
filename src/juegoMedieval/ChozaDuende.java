package juegoMedieval;

import java.awt.Point;

import j2d.mods.ControladorVida;
import j2d.mods.ITemporizado;
import j2d.mods.Temporizador;
import juegoMedieval.utils.UtilsDepuracion;

public class ChozaDuende extends Edificio implements ITemporizado {
	
	public static int ANCHO_CHOZA = 100;
	public static int ALTO_CHOZA = 135;
	
	public static final int SALUD_BASE = 100;
	public static final int TIEMPO_GENERACION_MS = 8_000;
	public static final int TIEMPO_PRIMERA_GENERACION_MS = 1_000;
	public static final int MAX_DUENDES_TOTAL = 5;
	
	private static int duendesGenerados = 0;
	
	private Temporizador generadorDuendes = new Temporizador(TIEMPO_PRIMERA_GENERACION_MS, this);
	
	private static final RecursosEdificio RECURSOS = new RecursosEdificio("resources/goblin_hut/", 1, "resources/sounds/building/wood_collapse.wav");

	public ChozaDuende(String nombre) {
		super(nombre, ANCHO_CHOZA, ALTO_CHOZA, RECURSOS, UtilsDepuracion.colorColisionadorDuende());
		
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
		EscenaMedieval escena = (EscenaMedieval) escena();
		Point puntoAparicion = posicion();
		puntoAparicion.translate(ANCHO_CHOZA + DuendePiromano.ANCHURA_DUENDE / 2, 0);
		escena.generaDuendePiromano(puntoAparicion);
		duendesGenerados++;
		
		if (duendesGenerados < MAX_DUENDES_TOTAL) {
			temporizador.iniciaCuenta(TIEMPO_GENERACION_MS);
		}
	}
}
