package multijugador;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugadorCliente;

/**
 * Lanza el cliente del juego de combate multijugador.
 * 
 * @version jun-2026
 */
public class JuegoCombateMultijugador {

	public static void main(String[] args) {
		try {
			Juego.asignaGestorMultijugador(new GestorMultijugadorCliente(null,  new InetSocketAddress(InetAddress.getLocalHost(), 5000)));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(-1);
		}
		
		Juego.asignaNombre("Juego de combate medieval");
		
		Juego.anhadeEscena(new EscenaMultijugador());
		Juego.jugar();
	}
}
