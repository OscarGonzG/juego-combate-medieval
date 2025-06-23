package juegoMedieval;

import java.awt.Color;
import java.util.Random;

import j2d.JObjetoTexto;
import j2d.mods.ITemporizado;
import j2d.mods.Temporizador;

/**
 * Indicador de danho que muestra un valor y se lanza automaticamente en una
 * direccion aleatoria tras su inclusion en la escena.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public class IndicadorDanho extends JObjetoTexto implements ITemporizado {
	private static final int DURACION_MS = 600;
	private static final int VEL_X = 5;
	private static final int VEL_Y = -20;
	private static Random rand = new Random();
	
	private Temporizador temporizador;

	public IndicadorDanho(int danho) {
		super("-" + Integer.toString(danho), Color.RED);
		temporizador = new Temporizador(DURACION_MS, this);
		temporizador.iniciaCuenta();
		
		
		int velX = rand.nextBoolean() ? VEL_X : -VEL_X;
		this.asignaVel(velX, VEL_Y);
		asignaFactorGravedad(3);
		colisionador().desactiva();
	}

	@Override
	public void finTiempo(Temporizador temporizador) {
		escena().eliminaObj(this);
	}
	
}
