package multijugador;

import java.awt.Point;

import j2d.Juego;
import j2d.mods.multijugador.VariableRed;
import j2d.utils.Vector2D;
import juegoCombateMedieval.Estado.Accion;
import juegoCombateMedieval.Personaje;

/**
 * Sincroniza la posicion y velocidad de un {@link Personaje} y permite que este 
 * ataque. Crea variables de red con nombres derivados de anhadir los sufijos
 * ".pos", ".vel" y ".atacando" al nombre del sincronizador.
 *
 * @author Oscar Gonzalez Garcia
 * @version jun-2026
 */
public class SincronizadorPersonaje {

	private final Personaje controlado;
	private final VariableRed<Point> posicion;
	private final VariableRed<Vector2D> velocidad;
	private final VariableRed<Boolean> atacando;
	
	/**
	 * Crea un ControladorPersonajeRed con el mismo nombre que el personaje.
	 * @param controlado personaje controlado.
	 */
	public SincronizadorPersonaje(Personaje controlado) {
		this(controlado, controlado.nombre());
	}
	
	/**
	 * Crea un ControladorPersonajeRed con un nombre concreto.
	 * @param controlado personaje controlado.
	 * @param nombreControlador nombre del controlador.
	 */
	public SincronizadorPersonaje(Personaje controlado, String nombreControlador) {
		this.controlado = controlado;
		posicion = Juego.nuevaVariableRed(Point.class, nombreControlador + ".pos", controlado.posicion());
		posicion.anhadeSuscriptor(var -> controlado.posiciona(var.valor()));
		
		velocidad = Juego.nuevaVariableRed(Vector2D.class, nombreControlador + ".vel", controlado.velocidad());
		velocidad.anhadeSuscriptor(var -> controlado.asignaVel(var.valor()));
		
		atacando = Juego.nuevaVariableRed(Boolean.class, nombreControlador + ".atacando", false);
		atacando.anhadeSuscriptor(var -> {
			if (var.valor() == true) {
				controlado.ataca();
			}
		});
	}
	
	public void ciclo() {
		if (Juego.esServidor() && controlado.escena() != null) {
			posicion.asignaValor(controlado.posicion());
			velocidad.asignaValor(controlado.velocidad());
			atacando.asignaValor(controlado.getAccion() == Accion.ATACANDO);
		}
	}
}
