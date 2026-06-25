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

	private SincronizadorVida sincronizadorVida;
	private final VariableRed<Integer> duendesRestantes;
	private List<SincronizadorPersonaje> controladoresPersonaje = new ArrayList<>();
	
	/**
	 * Crea una choza de duendes.
	 * @param escena
	 * @param nombreSincronizador nombre del {@link SincronizadorVida}
	 */
	public ChozaMultijugador(EscenaCombate escena, String nombreSincronizador) {
		super(escena);
		sincronizadorVida = new SincronizadorVida(getControladorVida(), nombreSincronizador);
		duendesRestantes =
				Juego.nuevaVariableRed(Integer.class, nombreSincronizador + ".duendesGenerados", 4);
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
		controladoresPersonaje.add(new SincronizadorPersonaje(duende,
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
		sincronizadorVida.ciclo();
	}
}
