package juegoMedieval;



import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import j2d.JEscena;
import j2d.Juego;
import j2d.utils.ImagenesUtils;

/**
 * Representa una escena del juego de combate medieval.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public class EscenaCombate extends JEscena {
	
	private Set<EntidadFaccion> entidades = new LinkedHashSet<>();
	
	
	@Override
	public void entraEscena() {
		poneTexturaFondo(ImagenesUtils.creaImagen("resources/terrain/flat/green_middle.png"), 1);
		asignaGravedad(1);
		
		incluyeObj(new ChozaDuende(), 50, 50);
		
		Caballero jugador = new Caballero("jugador");
		controladoRatonAnhade(jugador.getGuiaPorRaton());
		incluyeObj(jugador, Juego.anchoPixelsX() / 2, Juego.altoPixelsY() / 2);
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
	
	/**
	 * Registra una entidad para que pueda ser golpeada. <b>No la incluye en la
	 * escena.</b>
	 * @param e la entidad a registrar.
	 */
	public void registraEntidad(EntidadFaccion e) {
		entidades.add(e);
	}
	
	/**
	 * Borra una entidad, haciendo que no pueda ser golpeada. <b>No la elimina
	 * de la escena.</b>
	 * @param e la entidad a borrar.
	 */
	public void borraEntidad(EntidadFaccion e) {
		entidades.remove(e);
	}
}
