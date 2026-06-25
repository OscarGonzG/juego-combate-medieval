package multijugador;

import java.io.IOException;
import java.net.InetSocketAddress;

import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugadorServidor;

public class ServidorCombateMultijugador {

	public static void main(String[] args) {
		try {
			Juego.asignaGestorMultijugador(new GestorMultijugadorServidor(4, new InetSocketAddress(5000)));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(-1);
		}
		Juego.asignaNombre("Servidor de combate medieval");
		
		Juego.anhadeEscena(new EscenaMultijugador());
		Juego.jugar();
	}

}
