package multijugador;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JOptionPane;

import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugador;
import j2d.mods.multijugador.GestorMultijugadorServidor;

/**
 * Lanza el cliente del juego de combate multijugador.
 *
 * @author Oscar Gonzalez Garcia
 * @version jun-2026
 */
public class ServidorCombateMultijugador {

	protected static final int NUM_JUGADORES = 3;

	public final static InetSocketAddress dirMulticast() {
		InetAddress ipMulticast = null;
		try {
			ipMulticast = InetAddress.getByName("224.0.0.1");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return new InetSocketAddress(ipMulticast, 8000);
	}

	public static final SocketAddress dirServidor() {
		SocketAddress dirServidor = null;
		while (dirServidor == null) {
			String stringDirServidor = JOptionPane.showInputDialog(null,
					"Introduce HOST:PUERTO del servidor",
					"Indica el servidor", JOptionPane.PLAIN_MESSAGE);

			if (stringDirServidor == null) {
				System.exit(-1);
			}

			String[] strings = stringDirServidor.split(":");

			if (strings.length != 2) {
				System.exit(-1);
			}

			try {
				int puerto = Integer.parseInt(strings[1]);
				InetAddress ipServidor = InetAddress.getByName(strings[0]);
				dirServidor = new InetSocketAddress(ipServidor, puerto);
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "Host \"" + strings[0] +
						"\" desconocido", "ERROR", JOptionPane.ERROR_MESSAGE);
			} catch (IllegalArgumentException e ) {
				JOptionPane.showMessageDialog(null, "Puerto \"" + strings[1] +
						"\" no válido", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		return dirServidor;
	}

	public static NetworkInterface eligeInterfazRed() {
		Enumeration<NetworkInterface> interfaces = null;
		JOptionPane panelOpciones = new JOptionPane();

		try {
			interfaces =
					NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		List<NetworkInterface> listaInterfaces = Collections.list(interfaces);
		panelOpciones.setSelectionValues(listaInterfaces.stream()
				.map(iface -> iface.getDisplayName()).toArray());
		panelOpciones.createDialog("Interfaz de red para multicast")
				.setVisible(true);
		NetworkInterface elegida = listaInterfaces.stream()
				.filter(iface ->
					iface.getDisplayName().equals(panelOpciones
							.getInputValue())).findFirst().get();
		return elegida;
	}

	public static void main(String[] args) {
		try {
			GestorMultijugador g = new GestorMultijugadorServidor(NUM_JUGADORES, new InetSocketAddress(5000));
			g.habilitaMulticast(dirMulticast(), eligeInterfazRed());
			Juego.asignaGestorMultijugador(g);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Juego.asignaNombre("Servidor de combate medieval");
		
		Juego.anhadeEscena(new EscenaMultijugador());
		Juego.jugar();
	}

}
