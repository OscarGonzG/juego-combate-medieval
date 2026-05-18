package multijugador;
import java.awt.Point;

import j2d.Juego;
import j2d.mods.multijugador.IReceptorEventosRed;
import j2d.utils.ImagenesUtils;
import juegoCombateMedieval.EscenaCombate;

public class EscenaMultijugador extends EscenaCombate {
	@Override
	public void entraEscena() {
		poneTexturaFondo(ImagenesUtils.creaImagen("resources/terrain/flat/green_middle.png"), 1);

		Juego.gestorMultijugador().anhadeReceptorEventos(new IReceptorEventosRed() {
			@Override
			public void jugadorUnido(int numJugador) {
				if (Juego.esCliente() &&
						Juego.gestorMultijugador().idJugador() == numJugador) {
					for (int i = numJugador - 1; i >= 0; i--) {
						CaballeroMultijugador jugadorRemoto = new CaballeroMultijugador("caballero" + i, EscenaMultijugador.this);
						incluyeObjCentrado(jugadorRemoto, new Point(Juego.anchoPixelsX()/2, Juego.altoPixelsY()/2));

						controladoRatonRedAnhade(jugadorRemoto.getGuiaRaton(), numJugador);
						jugadorRemoto.inicializaRed();
					}
				}
				
				CaballeroMultijugador caballero = new CaballeroMultijugador("caballero" + numJugador, EscenaMultijugador.this);
				incluyeObjCentrado(caballero, new Point(Juego.anchoPixelsX()/2, Juego.altoPixelsY()/2));

				controladoRatonRedAnhade(caballero.getGuiaRaton(), numJugador);
				caballero.inicializaRed();
			}
		});
	}
}
