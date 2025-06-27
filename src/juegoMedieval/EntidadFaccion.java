package juegoMedieval;

import java.awt.Color;

import j2d.JEscena;
import j2d.JObjetoRectangulo;
import j2d.mods.ControladorVida;

/**
 * Representa a una entidad que pertenece a una {@link Faccion}.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public abstract class EntidadFaccion extends JObjetoRectangulo {

	private ControladorVida controladorVida = null;
	private boolean muerto = false;
	
	public EntidadFaccion(String nombre, int anchoX, int altoY, Color colorColisionador) {
		super(nombre, anchoX, altoY, colorColisionador);
		asignaFactorGravedad(0);
	}
	
	/**
	 * 
	 */
	protected void setControladorVida(ControladorVida controladorVida) {
		if (this.controladorVida != null) {
			throw new IllegalStateException("El controlador de vida ya ha sido inicializado");
		}
		
		this.controladorVida = controladorVida;
	}
	
	/**
	 * Obtiene el controlador de vida de la entidad.
	 * @return el controlador de vida de la entidad.
	 */
	public ControladorVida getControladorVida() {
		return controladorVida;
	}
	
	
	/**
	 * Devuelve la faccion a la que pertenece la entidad.
	 * @return la faccion a la que pertenece la entidad.
	 */
	public abstract Faccion getFaccion();

	/**
	 * Mata al personaje.
	 */
	public void muere() {
		muerto = true;
		colisionador().desactiva();
		asignaVel(0, 0);
	}
	
	/**
	 * Indica si el personaje esta muerto.
	 * @return true si esta muerto, false en caso contrario.
	 */
	public boolean estaMuerto() {
		return muerto;
	}
	
	@Override
	public void objetoIncluido() {
		if (escena() instanceof EscenaCombate escenaCombate) {
			escenaCombate.registraEntidad(this);
		}
	}
	
	@Override
	public void objetoEliminado(JEscena escena) {
		if (escena instanceof EscenaCombate escenaCombate) {
			escenaCombate.borraEntidad(this);
		}
	}
}
