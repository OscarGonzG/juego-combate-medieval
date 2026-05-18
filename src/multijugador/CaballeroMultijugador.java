package multijugador;

import java.awt.Point;

import j2d.JEscena.ObjetoNoEnEscena;
import j2d.Juego;
import j2d.mods.multijugador.VariableRed;
import j2d.utils.Vector2D;
import juegoCombateMedieval.Caballero;
import juegoCombateMedieval.EscenaCombate;
import juegoCombateMedieval.Faccion;
import juegoCombateMedieval.Estado.Accion;

public class CaballeroMultijugador extends Caballero {
	
	private VariableRed<Point> posicion;
	private VariableRed<Vector2D> velocidad;
	private VariableRed<Boolean> atacando;

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

	public void inicializaRed() {
		if (escena() == null) {
			throw new ObjetoNoEnEscena(nombre());
		}
		posicion = Juego.nuevaVariableRed(Point.class, nombre() + ".pos", posicion());
		posicion.anhadeSuscriptor(var -> posiciona(var.valor()));
		
		velocidad = Juego.nuevaVariableRed(Vector2D.class, nombre() + ".vel", velocidad());
		velocidad.anhadeSuscriptor(var -> asignaVel(var.valor()));
		
		atacando = Juego.nuevaVariableRed(Boolean.class, nombre() + ".atacando", false);
		atacando.anhadeSuscriptor(var -> {
			if (var.valor() == true) {
				ataca();
			}
		});
	}
	
	@Override
	public Faccion getFaccion() {
		return super.getFaccion();
	}

	@Override
	public void ciclo() {
		super.ciclo();
		if (Juego.esServidor()) {
			posicion.asignaValor(posicion());
			velocidad.asignaValor(velocidad());
			atacando.asignaValor(getAccion() == Accion.ATACANDO);
		}
	}
}
