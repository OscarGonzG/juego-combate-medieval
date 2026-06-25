package multijugador;

import j2d.Juego;
import juegoCombateMedieval.Caballero;
import juegoCombateMedieval.EscenaCombate;
import juegoCombateMedieval.Faccion;

public class CaballeroMultijugador extends Caballero {

	private SincronizadorPersonaje sincronizadorPersonaje;
	private SincronizadorVida sincronizadorVida;

	public CaballeroMultijugador(String nombre, EscenaCombate escena) {
		super(nombre, escena);
		this.sincronizadorPersonaje = new SincronizadorPersonaje(this);
		this.sincronizadorVida = new SincronizadorVida(getControladorVida(), nombre);
		// Permitir el control local de la guia hace que el caballero se
		// teletransporte ligeramente hacia atras cuando empiezan a llegar
		// actualizaciones de posicion del servidor
		// Ademas, causa que la animacion de ataque se pueda reproducir en el
		// cliente aunque el ataque no se realice en el servidor por diferencias
		// en CaballeroMultijugador.estado
		escena.controladoRatonElimina(getGuiaRaton());
	}
	
	@Override
	public Faccion getFaccion() {
		return super.getFaccion();
	}

	@Override
	public boolean ataca() {
		if (Juego.esCliente()) {
			return atacaSinDanho();			
		} else {
			return super.ataca();
		}
	}

	/**
	 * {@inheritDoc} Tambien sincroniza a traves de la red variables del
	 * {@code CaballeroMultijugador}.
	 */
	@Override
	public void ciclo() {
		super.ciclo();
		sincronizadorPersonaje.ciclo();
		sincronizadorVida.ciclo();
	}
}
