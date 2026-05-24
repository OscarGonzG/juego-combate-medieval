package multijugador;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugadorCliente;
import j2d.mods.multijugador.ISerializador;
import j2d.utils.Vector2D;

public class JuegoCombateMultijugador {
	
	public static final void inicializaSerializadores() {
		Juego.gestorMultijugador().registroSerializables().registraTipo(Vector2D.class,
				new ISerializador<Vector2D>() {
					@Override
					public void serializar(ByteBuffer buf, Vector2D obj) {
						buf.putFloat(obj.x());
						buf.putFloat(obj.y());
					}

					@Override
					public Vector2D deserializar(ByteBuffer buf) {
						return new Vector2D(buf.getFloat(), buf.getFloat());
					}
		});
	}

	public static void main(String[] args) {
		try {
			Juego.asignaGestorMultijugador(new GestorMultijugadorCliente(null,  new InetSocketAddress(InetAddress.getLocalHost(), 5000)));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(-1);
		}
		
		Juego.asignaNombre("Juego de combate medieval");
		inicializaSerializadores();
		
		Juego.anhadeEscena(new EscenaMultijugador());
		Juego.jugar();
	}
}
