package multijugador;

import j2d.Juego;
import j2d.mods.ControladorVida;
import j2d.mods.multijugador.VariableRed;

/**
 * Sincroniza un {@link ControladorVida} en un entorno multijugador. Crea una
 * variable de red denominada {@code nombreSincronizador + ".vida"}.
 *
 * @author Oscar Gonzalez Garcia
 * @version jun-2026
 */
public class SincronizadorVida {
	
	private final ControladorVida controlador;
	private final VariableRed<Float> salud;
	
	/**
	 * Construye un sincronizador de vida.
	 * @param controlado objeto controlado.
	 * @param nombre nombre del sincronizador.
	 */
	public SincronizadorVida(ControladorVida controlado, String nombre) {
		this.controlador = controlado;
		
		salud = Juego.nuevaVariableRed(Float.class,
				nombre + ".vida",
				controlado.vidaRestante());
		salud.anhadeSuscriptor(var -> {
			float vidaRestante = controlado.vidaRestante();
			controlado.quitaVida(vidaRestante - var.valor());
		});
	}

	public void ciclo() {
		if (Juego.esServidor()) {
			salud.asignaValor(controlador.vidaRestante());			
		}
	}
}
