package juegoMedieval;



import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import j2d.JEscena;
import j2d.JObjeto;
import j2d.Juego;
import j2d.utils.ImagenesUtils;

public class EscenaMedieval extends JEscena {
	
	private int duendesCreados = 0;
	private List<DuendePiromano> duendes = new ArrayList<>();
	
	private Caballero jugador;
	
	@Override
	public void entraEscena() {
		poneTexturaFondo(ImagenesUtils.creaImagen("resources/terrain/flat/green_middle.png"), 1);
		asignaGravedad(1);
		
		generaDuendePiromano(new Point(100, 100));
		generaDuendePiromano(new Point(200, 100));
		generaDuendePiromano(new Point(300, 100));
		
		jugador = new Caballero("jugador");
		controladoRatonAnhade(jugador.getGuiaPorRaton());
		incluyeObj(jugador, Juego.anchoPixelsX() / 2, Juego.altoPixelsY() / 2);
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
		duendes.add(d);
	}
	
	/**
	 * Devuelve una lista con todos los personajes que no pertenezcan a la
	 * faccion indicada presentes en el area dada.
	 * @param area area en el que buscar personajes.
	 * @param faccion faccion cuyos enemigos van a buscarse.
	 * @return lista con todos los personajes de la faccion en el area dada.
	 */
	public List<Personaje> buscaEntidadesEnemigasEnArea(Rectangle area, Faccion faccion) {
		List<Personaje> entidades = new ArrayList<>();
		switch (faccion) {
		case CABALLEROS -> {
			for (Personaje e : duendes) {
				if (e.area().intersects(area)) {
					entidades.add(e);
				}
			}
		}
		case DUENDES -> {
			if (jugador.area().intersects(area)) {
				entidades.add(jugador);			
			}
		}
		}
		
		return entidades;
	}
	
	@Override
	public synchronized void eliminaObj(JObjeto obj) throws ObjetoNoEnEscena {
		if (obj instanceof DuendePiromano duendePiromano) {
			duendes.remove(duendePiromano);
		}
		super.eliminaObj(obj);
	}
}
