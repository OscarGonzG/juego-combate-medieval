package multijugador;

import j2d.Juego;
import juegoCombateMedieval.DuendePiromano;
import juegoCombateMedieval.EscenaCombate;

public class DuendeMultijugador extends DuendePiromano {
	
	private SincronizadorPersonaje sincronizadorPersonaje;
	private SincronizadorVida sincronizadorVida;
	
	/**
	 * Crea un duende.
	 * @param escena
	 * @param nombreSincronizador nombre de sus sincronizadores.
	 */
	public DuendeMultijugador(EscenaCombate escena, String nombreSincronizador) {
		super(escena);
		sincronizadorPersonaje = new SincronizadorPersonaje(this, nombreSincronizador);
		sincronizadorVida = new SincronizadorVida(getControladorVida(), nombreSincronizador);
	}

	@Override
	public boolean ataca() {
		if (Juego.esServidor()) {
			return super.ataca();
		} else {
			return atacaSinDanho();			
		}
	}
	
	@Override
	protected void cicloComportamiento() {
		if (Juego.esCliente()) return;
		super.cicloComportamiento();
	}

	/**
	 * {@inheritDoc} Tambien sincroniza a traves de la red variables del
	 * {@code DuendeMultijugador}.
	 */
	@Override
	public void ciclo() {
		super.ciclo();
		sincronizadorPersonaje.ciclo();
		sincronizadorVida.ciclo();
	}
}
