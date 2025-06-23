package juegoMedieval;



import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import j2d.JEscena;
import j2d.JObjeto;
import j2d.Juego;
import j2d.utils.ImagenesUtils;

/**
 * Representa una escena del juego de combate medieval.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public class EscenaMedieval extends JEscena {
	
	private int duendesCreados = 0;
	private int chozasCreadas = 0;
	private List<EntidadFaccion> entidades = new ArrayList<>();
	
	
	@Override
	public void entraEscena() {
		poneTexturaFondo(ImagenesUtils.creaImagen("resources/terrain/flat/green_middle.png"), 1);
		asignaGravedad(1);
		
		generaChozaDuende(new Point((int) (ChozaDuende.ANCHO_CHOZA * 1.5), altoY() / 3));
		
		Caballero jugador = new Caballero("jugador");
		controladoRatonAnhade(jugador.getGuiaPorRaton());
		incluyeObj(jugador, Juego.anchoPixelsX() / 2, Juego.altoPixelsY() / 2);
		
		entidades.add(jugador);
	}
	
	/**
	 * Genera una choza duende en la posicion indicada.
	 * @param puntoAparicion el punto sobre el cual aparecera el centro de la
	 * base de la choza.
	 */
	public void generaChozaDuende(Point puntoAparicion) {
		chozasCreadas++;
		ChozaDuende c = new ChozaDuende("choza" + chozasCreadas);
		incluyeObjCentrado(c, puntoAparicion.x, puntoAparicion.y - c.altoY() / 2);
		entidades.add(c);
	}
	
	
	/**
	 * Genera un duende piromano en la posicion indicada.
	 * @param puntoAparicion el punto sobre el cual el duende aparecera
	 * centrado.
	 */
	public void generaDuendePiromano(Point puntoAparicion) {
		duendesCreados++;
		DuendePiromano d = new DuendePiromano("duende" + duendesCreados);
		incluyeObjCentrado(d, puntoAparicion.x, puntoAparicion.y);
		entidades.add(d);
	}
	
	/**
	 * Devuelve una lista con todos los personajes que no pertenezcan a la
	 * faccion indicada presentes en el area dada.
	 * @param area area en el que buscar personajes.
	 * @param faccion faccion cuyos enemigos van a buscarse.
	 * @return lista con todos los personajes de la faccion en el area dada.
	 */
	public List<EntidadFaccion> buscaEntidadesEnemigasEnArea(Rectangle area, Faccion faccion) {
		List<EntidadFaccion> entidadesEncontradas = new ArrayList<>();
		
		for (EntidadFaccion e : entidades) {
			if (e.getFaccion() != faccion && e.area().intersects(area)) {
				entidadesEncontradas.add(e);
			}
		}
		
		return entidadesEncontradas;
	}
	
	@Override
	public synchronized void eliminaObj(JObjeto obj) throws ObjetoNoEnEscena {
		if (obj instanceof EntidadFaccion entidad) {
			entidades.remove(entidad);
		}
		super.eliminaObj(obj);
	}
}
