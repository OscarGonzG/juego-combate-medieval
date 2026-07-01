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
	 * @param sincronizado {@link ControladorVida} sincronizado.
	 * @param nombre nombre del sincronizador.
	 */
	public SincronizadorVida(ControladorVida sincronizado, String nombre) {
		this.controlador = sincronizado;
		
		salud = Juego.nuevaVariableRed(Float.class,
				nombre + ".vida",
				sincronizado.vidaRestante());
		salud.anhadeSuscriptor(var -> {
			float vidaRestante = sincronizado.vidaRestante();
			if (sincronizado.vidaRestante() != var.valor()) {
				sincronizado.quitaVida(vidaRestante - var.valor());
			}
		});
	}

	public void ciclo() {
		if (Juego.esServidor()) {
			salud.asignaValor(controlador.vidaRestante());			
		}
	}
}
