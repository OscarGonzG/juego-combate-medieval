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
	private final VariableRed<Integer> duendesRestantes;
	private List<ControladorPersonajeRed> controladoresPersonaje = new ArrayList<>();
	
	/**
	 * Crea una choza de duendes.
	 * @param escena
	 * @param nombreControlador nombre del {@link ControladorEdificioRed}
	 */
	public ChozaMultijugador(EscenaCombate escena, String nombreControlador) {
		super(escena);
		controladorRed = new ControladorEdificioRed(this, nombreControlador);
		duendesRestantes =
				Juego.nuevaVariableRed(Integer.class, nombreControlador + ".duendesGenerados", 4);
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
