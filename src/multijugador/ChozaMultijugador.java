package multijugador;

import java.util.ArrayList;
import java.util.List;

import j2d.Juego;
import j2d.mods.Temporizador;
import j2d.mods.multijugador.VariableRed;
import juegoCombateMedieval.ChozaDuende;
import juegoCombateMedieval.EscenaCombate;
import juegoCombateMedieval.Personaje;

public class ChozaMultijugador extends ChozaDuende {

	private ControladorEdificioRed controladorRed;
	private VariableRed<Integer> duendesRestantes =
			Juego.nuevaVariableRed(Integer.class, nombre() + ".duendesGenerados", 4);
	private List<ControladorPersonajeRed> controladoresPersonaje = new ArrayList<>();
	
	/**
	 * Crea una choza de duendes.
	 * @param escena
	 * @param nombreControlador nombre del {@link ControladorEdificioRed}
	 */
	public ChozaMultijugador(EscenaCombate escena, String nombreControlador) {
		super(escena);
		controladorRed = new ControladorEdificioRed(this, nombre());
		duendesRestantes.anhadeSuscriptor(var -> {
			// el if evita que la llegada del valor inicial genere un duende
			if (getNumDuendesDentro() != var.valor()) {
				generaDuende();
			}
		});
	}
	
	@Override
	protected Personaje creaDuende() {
		DuendeMultijugador duende = new DuendeMultijugador(
				(EscenaCombate)escena(),
				"duende" + getNumDuendesDentro());
		controladoresPersonaje.add(new ControladorPersonajeRed(duende,
				nombre() + getNumDuendesDentro()));
		return duende;
	}

	@Override
	public void finTiempo(Temporizador temporizador) {
		super.finTiempo(temporizador);
		duendesRestantes.asignaValor(getNumDuendesDentro());
	}

	@Override
	public void ciclo() {
		controladoresPersonaje.forEach(c -> c.ciclo());
		controladorRed.ciclo();
	}
}
