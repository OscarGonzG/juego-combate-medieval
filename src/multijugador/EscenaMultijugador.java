package multijugador;
import java.awt.Point;

import j2d.Juego;
import j2d.mods.multijugador.IReceptorEventosRed;
import j2d.utils.ImagenesUtils;
import juegoCombateMedieval.EscenaCombate;

public class EscenaMultijugador extends EscenaCombate {

	private ChozaMultijugador choza;

	@Override
	public void entraEscena() {
		poneTexturaFondo(ImagenesUtils.creaImagen("resources/terrain/flat/green_middle.png"), 1);

		Juego.anhadeReceptorEventosRed(new IReceptorEventosRed() {
			@Override
			public void jugadorUnido(int numJugador) {
				if (Juego.esCliente()) {
					if	(Juego.gestorMultijugador().idJugador() == numJugador) {
						for (int i = numJugador - 1; i >= 0; i--) {
							CaballeroMultijugador jugadorRemoto =
									new CaballeroMultijugador("caballero" + i,
											EscenaMultijugador.this);
							incluyeObjCentrado(jugadorRemoto,
									new Point(Juego.anchoPixelsX()/2,
											Juego.altoPixelsY()/2));
							controladoRatonRedAnhade(jugadorRemoto.getGuiaRaton(),numJugador);
						}
					}
				}

				if (Juego.esServidor()
						&& numJugador == ServidorCombateMultijugador.NUM_JUGADORES -1
						|| Juego.gestorMultijugador().idJugador() == numJugador) {
					choza = new ChozaMultijugador(EscenaMultijugador.this, "choza");
					incluyeObj(choza, new Point(30, 30));
					if (Juego.esServidor()) {
						choza.iniciaGeneracion();
					}
				}
				
				CaballeroMultijugador caballero = new CaballeroMultijugador("caballero" + numJugador, EscenaMultijugador.this);
				incluyeObjCentrado(caballero, new Point(Juego.anchoPixelsX()/2, Juego.altoPixelsY()/2));

				controladoRatonRedAnhade(caballero.getGuiaRaton(), numJugador);
			}
		});
	}
}
