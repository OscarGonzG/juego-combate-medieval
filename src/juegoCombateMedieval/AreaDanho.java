package juegoCombateMedieval;

import java.util.List;

import j2d.JObjetoRectangulo;
import j2d.mods.ITemporizado;
import j2d.mods.Temporizador;
import juegoMedieval.utils.UtilsDepuracion;

/**
 * Aplica danho a todos los enemigos en el area del objeto cuando pase el tiempo
 * indicado desde su inclusion en la escena y despues se elimina de la escena.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public class AreaDanho extends JObjetoRectangulo implements ITemporizado {

	private static final int INDICE_Z = 40;
	private static int contadorAreasDanho = 0;
	private final Temporizador temporizador;

	private final Faccion faccion;
	private final int danho;
	
	/**
	 * Crea un area de danho.
	 * @param anchoX anchura del area de danho.
	 * @param largoY longitud del area de danho.
	 * @param tiempoMs espera entre que se incluye este objeto en la escena y el 
	 * danho es aplicado.
	 * @param danho danho a aplicar.
	 * @param faccion faccion cuyos enemigos van a ser danhados.
	 */
	public AreaDanho(int anchoX, int largoY, int tiempoMs, int danho,
					 Faccion faccion) {
		super("areaDanho" + contadorAreasDanho, anchoX, largoY, UtilsDepuracion.colorAreaDanho());
		contadorAreasDanho++;
		this.faccion = faccion;
		this.danho = danho;
		
		colisionador().desactiva();
		asignaFactorGravedad(0);
		temporizador = new Temporizador(tiempoMs, this);
		temporizador.iniciaCuenta();
		asignaZ(INDICE_Z);
	}

	@Override
	public void finTiempo(Temporizador temporizador) {
		EscenaCombate escena = ((EscenaCombate) escena());
		List<EntidadFaccion> enemigos = escena.buscaEntidadesEnemigasEnArea(area(), faccion);
		for (EntidadFaccion e : enemigos) {
			if (e.estaMuerto()) {
				continue;
			}
			e.getControladorVida().quitaVida(danho);
		}
		escena.eliminaObj(this);
	}
	
}
