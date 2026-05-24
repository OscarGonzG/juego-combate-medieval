package multijugador;

import java.awt.Point;

import j2d.Juego;
import j2d.mods.multijugador.VariableRed;
import j2d.utils.Vector2D;
import juegoCombateMedieval.Estado.Accion;
import juegoCombateMedieval.Personaje;

public class ControladorPersonajeRed {

	private final Personaje controlado;
	private final VariableRed<Point> posicion;
	private final VariableRed<Vector2D> velocidad;
	private final VariableRed<Float> salud;
	private final VariableRed<Boolean> atacando;
	
	/**
	 * Crea un ControladorPersonajeRed con el mismo nombre que el personaje.
	 * @param controlado personaje controlado.
	 */
	public ControladorPersonajeRed(Personaje controlado) {
		this(controlado, controlado.nombre());
	}
	
	/**
	 * Crea un ControladorPersonajeRed con un nombre concreto.
	 * @param controlado personaje controlado.
	 * @param nombreControlador nombre del controlador.
	 */
	public ControladorPersonajeRed(Personaje controlado, String nombreControlador) {
		this.controlado = controlado;
		posicion = Juego.nuevaVariableRed(Point.class, nombreControlador + ".pos", controlado.posicion());
		posicion.anhadeSuscriptor(var -> controlado.posiciona(var.valor()));
		
		velocidad = Juego.nuevaVariableRed(Vector2D.class, nombreControlador + ".vel", controlado.velocidad());
		velocidad.anhadeSuscriptor(var -> controlado.asignaVel(var.valor()));
		
		salud = Juego.nuevaVariableRed(Float.class, 
				nombreControlador + ".salud",
				controlado.getControladorVida().vidaRestante());
		salud.anhadeSuscriptor(var -> {
			float vidaRestante = controlado.getControladorVida().vidaRestante();
			controlado.getControladorVida().quitaVida(vidaRestante - var.valor());
		});
		
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
			salud.asignaValor(controlado.getControladorVida().vidaRestante());
			atacando.asignaValor(controlado.getAccion() == Accion.ATACANDO);
		}
	}
}
