package juegoMedieval;

import java.awt.Rectangle;

import j2d.mods.IVisualizadorNumerico;

/**
 * Representa un personaje que combate cuerpo a cuerpo.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public abstract class AtacanteMele extends Personaje {
	
	private static final int ALCANCE_ATAQUE_VERTICAL = 90;
	private static final int ALCANCE_ATAQUE_HORIZONTAL = 90;
	private static final int ANCHO_ATAQUE = 120;

	protected AtacanteMele(String nombre, int anchoX, int altoY,
			EstadisticasPersonaje estadisticas, RecursosPersonaje recursos) {
		this(nombre, anchoX, altoY, estadisticas, null, null, recursos);
	}
	
	protected AtacanteMele(String nombre, int anchoX, int altoY,
						EstadisticasPersonaje estadisticas,
						IVisualizadorNumerico barraVida,
						IVisualizadorNumerico visualizadorCooldownAtaque,
						RecursosPersonaje recursos) {
		super(nombre, anchoX, altoY, estadisticas, barraVida,
				visualizadorCooldownAtaque, recursos);
	}

	@Override
	protected boolean ataca() {
		if (!super.ataca()) {
			return false;
		}
		Rectangle areaDanho = switch(getDireccion()) {
		case ABAJO -> new Rectangle(centro().x - ANCHO_ATAQUE / 2, centro().y, ANCHO_ATAQUE, ALCANCE_ATAQUE_VERTICAL);
		case ARRIBA -> new Rectangle(centro().x - ANCHO_ATAQUE / 2, centro().y - ALCANCE_ATAQUE_VERTICAL, ANCHO_ATAQUE, ALCANCE_ATAQUE_VERTICAL);
		case DERECHA -> new Rectangle(centro().x, centro().y - ANCHO_ATAQUE / 2, ALCANCE_ATAQUE_HORIZONTAL, ANCHO_ATAQUE);
		case IZQUIERDA -> new Rectangle(centro().x - ALCANCE_ATAQUE_HORIZONTAL, centro().y - ANCHO_ATAQUE / 2, ALCANCE_ATAQUE_HORIZONTAL, ANCHO_ATAQUE);
		};

		escena().incluyeObj(new AreaDanho(areaDanho.width, areaDanho.height, (int) (RecursosPersonaje.DURACION_ATAQUE_MS * 0.8f), getDanho(), getFaccion()), areaDanho.x, areaDanho.y);
		return true;
	}
	
}
