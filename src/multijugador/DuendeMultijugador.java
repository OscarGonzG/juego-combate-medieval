package multijugador;

import j2d.Juego;
import juegoCombateMedieval.DuendePiromano;
import juegoCombateMedieval.EscenaCombate;

public class DuendeMultijugador extends DuendePiromano {
	
	
	/**
	 * Crea un duende 
	 * @param escena
	 * @param nombreChoza
	 */
	public DuendeMultijugador(EscenaCombate escena) {
		super(escena);
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
}
