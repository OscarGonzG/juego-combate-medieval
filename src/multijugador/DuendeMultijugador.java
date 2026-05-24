package multijugador;

import j2d.Juego;
import juegoCombateMedieval.DuendePiromano;
import juegoCombateMedieval.EscenaCombate;

public class DuendeMultijugador extends DuendePiromano {
	
	private ControladorPersonajeRed controladorRed;
	
	/**
	 * Crea un duende.
	 * @param escena
	 * @param nombreControlador nombre de su {@link ControladorPersonajeRed}.
	 */
	public DuendeMultijugador(EscenaCombate escena, String nombreControlador) {
		super(escena);
		controladorRed = new ControladorPersonajeRed(this, nombreControlador);
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
		controladorRed.ciclo();
	}
}
