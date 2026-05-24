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
	
	private VariableRed<Integer> duendesRestantes =
			Juego.nuevaVariableRed(Integer.class, nombre() + ".duendesGenerados", 4);
	private List<ControladorPersonajeRed> controladoresPersonaje = new ArrayList<>();
	
	@Override
	protected Personaje creaDuende() {
		DuendeMultijugador duende = new DuendeMultijugador((EscenaCombate)escena());
		controladoresPersonaje.add(new ControladorPersonajeRed(duende, nombre() + getNumDuendesDentro()));
		System.out.println(nombre() + getNumDuendesDentro());
		return duende;
	}

	public ChozaMultijugador(EscenaCombate escena) {
		super(escena);
		duendesRestantes.anhadeSuscriptor(var -> {
			// el if evita que la llegada del valor inicial genere un duende
			if (getNumDuendesDentro() != var.valor()) {
				generaDuende();
			}
		});
	}
	
	@Override
	public void finTiempo(Temporizador temporizador) {
		super.finTiempo(temporizador);
		duendesRestantes.asignaValor(getNumDuendesDentro());
	}

	@Override
	public void ciclo() {
		controladoresPersonaje.forEach(c -> c.ciclo());
	}
}
