package multijugador;

import j2d.Juego;
import juegoCombateMedieval.Caballero;
import juegoCombateMedieval.EscenaCombate;
import juegoCombateMedieval.Faccion;

public class CaballeroMultijugador extends Caballero {

	public CaballeroMultijugador(String nombre, EscenaCombate escena) {
		super(nombre, escena);
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
}
