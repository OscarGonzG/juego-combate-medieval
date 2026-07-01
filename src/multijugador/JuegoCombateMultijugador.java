package multijugador;

import java.io.IOException;
import java.net.SocketAddress;

import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugador;
import j2d.mods.multijugador.GestorMultijugadorCliente;

/**
 * Lanza el cliente del juego de combate multijugador.
 *
 * @author Oscar Gonzalez Garcia
 * @version jun-2026
 */
public class JuegoCombateMultijugador {

	public static void main(String[] args) {
		try {
			SocketAddress dirServidor = ServidorCombateMultijugador.dirServidor();
			System.out.println(dirServidor);
			GestorMultijugador g =
					new GestorMultijugadorCliente(null,  dirServidor);
			g.habilitaMulticast(ServidorCombateMultijugador.dirMulticast(),
					ServidorCombateMultijugador.eligeInterfazRed());
			Juego.asignaGestorMultijugador(g);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		Juego.asignaNombre("Juego de combate medieval");
		
		Juego.anhadeEscena(new EscenaMultijugador());
		Juego.jugar();
	}
}
