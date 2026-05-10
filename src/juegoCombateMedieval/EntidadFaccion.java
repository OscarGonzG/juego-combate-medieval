package juegoCombateMedieval;

import java.awt.Color;

import j2d.JObjeto;
import j2d.JObjetoRectangulo;
import j2d.mods.ControladorVida;

/**
 * Representa a una entidad que pertenece a una {@link Faccion}.
 * 
 * @author Oscar Gonzalez Garcia
 * @version may-2026
 */
public abstract class EntidadFaccion extends JObjetoRectangulo {

	private ControladorVida controladorVida = null;
	private final EscenaCombate escena;
	
	protected EntidadFaccion(String nombre, int anchoX, int altoY, Color colorColisionador, EscenaCombate escena) {
		super(nombre, anchoX, altoY, colorColisionador);
		this.escena = escena;
		escena.registraEntidad(this);
		asignaFactorGravedad(0);
	}
	
	/**
	 * Asigna el controlador de vida de la entidad. Es necasario llamarlo para
	 * inicializar el objeto correctamente.
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

	protected JObjeto getVisualizadorVida() {
		return controladorVida.objVisualizador();
	}
	
	/**
	 * Mata al personaje.
	 */
	public void muere() {
		colisionador().desactiva();
		asignaVel(0, 0);
		escena.borraEntidad(this);
	}
	
	/**
	 * Indica si el personaje esta muerto.
	 * @return true si esta muerto, false en caso contrario.
	 */
	public boolean estaMuerto() {
		return controladorVida.numVidasRestantes() <= 0;
	}
}
