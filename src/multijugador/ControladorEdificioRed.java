package multijugador;

import j2d.Juego;
import j2d.mods.multijugador.VariableRed;
import juegoCombateMedieval.Edificio;

public class ControladorEdificioRed {
	
	private final Edificio controlado;
	private final VariableRed<Float> salud;
	
	public ControladorEdificioRed(Edificio controlado, String nombreControlador) {
		this.controlado = controlado;
		
		salud = Juego.nuevaVariableRed(Float.class,
				nombreControlador + ".salud",
				controlado.getControladorVida().vidaRestante());
		salud.anhadeSuscriptor(var -> {
			float vidaRestante = controlado.getControladorVida().vidaRestante();
			controlado.getControladorVida().quitaVida(vidaRestante - var.valor());
		});
	}

	public void ciclo() {
		if (Juego.esServidor() && controlado.escena() != null) {
			salud.asignaValor(controlado.getControladorVida().vidaRestante());			
		}
	}
}
