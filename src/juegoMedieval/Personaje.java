package juegoMedieval;

import j2d.JObjetoRectangulo;
import j2d.mods.ControladorVida;
import j2d.mods.ITemporizado;
import j2d.mods.IVisualizadorNumerico;
import j2d.mods.Temporizador;
import j2d.mods.Temporizador.TipoCuenta;
import juegoMedieval.Estado.Accion;
import juegoMedieval.Estado.Direccion;
import juegoMedieval.utils.UtilsDepuracion;

/**
 * Representa un personaje que puede moverse y atacar en 4 direcciones.
 */
public abstract class Personaje extends JObjetoRectangulo implements ITemporizado {
		
	
	private final Temporizador temporizadorAtaque;
	private final PielPersonaje piel;
	private final ControladorVida controladorVida;
	private final EstadisticasPersonaje estadisticas;

	private boolean puedeAtacar = true;
	private boolean muerto = false;
	
	/**
	 * {@inheritDoc} Para el objeto Personaje, recarga la habilidad de ataque.
	 */
	@Override
	public void finTiempo(Temporizador temporizador) {
		puedeAtacar  = true;
	}
	
	private Estado estado;

	/**
	 * Crea un personaje con visualizadores numericos del tiempo de recarga del
	 * ataque y de la vida del personaje.
	 * @param nombre nombre del personaje
	 * @param anchoX anchura del personaje
	 * @param altoY altura del personaje
	 * @param estadisticas las estadisticas base del personaje.
	 * @param barraVida visualizador numerico para la salud del personaje
	 * @param visualizadorCooldownAtaque visualizador numerico para el tiempo
	 * restante hasta que pueda volver a atacar
	 * @param recursos los recursos graficos y de sonido del personaje
	 */
	public Personaje(String nombre, int anchoX, int altoY,
					 EstadisticasPersonaje estadisticas,
					 IVisualizadorNumerico barraVida,
					 IVisualizadorNumerico visualizadorCooldownAtaque,
					 RecursosPersonaje recursos) {
		super(nombre, anchoX, altoY, UtilsDepuracion.colorColisionadorCaballero());
		this.piel = new PielPersonaje(nombre + ".piel", recursos);
		
		this.estadisticas = estadisticas;
		this.temporizadorAtaque = new Temporizador(visualizadorCooldownAtaque, 
				this, PielPersonaje.DURACION_ATAQUE_MS, false,
				TipoCuenta.CUENTA_ASCENDENTE);
		
		this.controladorVida = new ControladorVida(estadisticas.saludBase(), barraVida, piel);
		controladorVida.configuraAccionesMuerte(PielPersonaje.ANIMACION_MUERTE);
		
		int despAdornoX = (anchoX - piel.anchoX()) / 2;
		int despAdornoY = (altoY - piel.altoY()) / 2;
		
		adornoAnhade(piel, despAdornoX, despAdornoY);
		estado = Estado.PARADO_DERECHA;
		asignaFactorGravedad(0);
		
		piel.cambiaAnimacion(estado);
	}
	
	/**
	 * Crea un personaje.
	 * @param nombre nombre del personaje.
	 * @param anchoX anchura del personaje.
	 * @param altoY altura del personaje.
	 * @param estadisticas las estadisticas base del personaje.
	 * @param recursos los recursos graficos y de sonido del personaje.
	 */
	protected Personaje(String nombre, int anchoX, int altoY,
						EstadisticasPersonaje estadisticas,
						RecursosPersonaje recursos) {
		super(nombre, anchoX, altoY, UtilsDepuracion.colorColisionadorDuende());
		this.piel = new PielPersonaje(nombre + ".piel", recursos);
		this.estadisticas = estadisticas;
		this.temporizadorAtaque = new Temporizador(PielPersonaje.DURACION_ATAQUE_MS,
												   this);
		this.controladorVida = new ControladorVida(estadisticas.saludBase(),
												   piel);
		controladorVida.configuraAccionesMuerte(PielPersonaje.ANIMACION_MUERTE);
		int despAdornoX = (-piel.anchoX() + anchoX) / 2;
		int despAdornoY = (-piel.altoY() + altoY) / 2;

		adornoAnhade(piel, despAdornoX, despAdornoY);
		estado = Estado.PARADO_DERECHA;
		asignaFactorGravedad(0);

		piel.cambiaAnimacion(estado);
	}

	/**
	 * Obtiene la direccion en la que mira el personaje.
	 * @return
	 */
	public Direccion getDireccion() {
		return estado.direccion();
	}
	
	/**
	 * Obtiene la accion que esta realizando el personaje.
	 * @return
	 */
	public Accion getAccion() {
		return estado.accion();
	}
	
	/**
	 * Hace que el personaje mire a la direccion indicada.
	 * @param d la direccion a la que pasa a mirar.
	 * @return true si ha podido cambiar de direccion, false en caso contrario.
	 */
	public boolean cambiaDireccion(Direccion d) {
		if (d.equals(estado.direccion()) || estaMuerto()) {
			return false;
		}
		cambiaEstado(new Estado(d, estado.accion()));
		return true;
	}
	
	/**
	 * Hace que el personaje comience a ejecutar una accion.
	 * @param a la accion a ejecutar.
	 * @return true si es posible ejecutar la accion, false en caso contrario.
	 */
	public boolean cambiaAccion(Accion a) {
		if (a.equals(estado.accion()) || ataqueEnCurso() || estaMuerto()) {
			return false;
		}
		
		cambiaEstado(new Estado(estado.direccion(), a));
		if (a.equals(Accion.ATACANDO)) {
			temporizadorAtaque.iniciaCuenta();
		}
		return true;
	}
	
	public boolean ataqueEnCurso() {
		return !puedeAtacar;
	}

	private void cambiaEstado(Estado nuevoEstado) {
		piel.cambiaAnimacion(nuevoEstado);
		estado = nuevoEstado;
	}
	
	/**
	 * Se encarga de cambiar la accion ejecutada y la direccion a la que apunta
	 * el personaje en funcion de su velocidad.
	 */
	@Override
	public void ciclo() {
		if (!estaMuerto()) {
			float velX = velX();
			float velY = velY();		
			
			if (velX == 0 && velY == 0) {
				cambiaAccion(Accion.PARADO);
			} else {
				cambiaAccion(Accion.EN_MOVIMIENTO);

				Direccion direccionX = velX > 0 ? 
						Direccion.DERECHA : Direccion.IZQUIERDA;
				Direccion direccionY = velY > 0 ? 
						Direccion.ABAJO : Direccion.ARRIBA;

				Direccion direccionPrincipal = Math.abs(velX) >= Math.abs(velY) ?
								direccionX : direccionY;
				cambiaDireccion(direccionPrincipal);
			}
		}
	}
	/**
	 * Realiza un ataque.
	 * @return true si ha podido iniciar el ataque, false en caso contrario.
	 */
	public boolean ataca() {
		if (cambiaAccion(Accion.ATACANDO)) {
			puedeAtacar = false;
			return true;
		}
		return false;
	}
	
	/**
	 * Obtiene el controlador de vida del personaje.
	 * @return el controlador de vida del personaje.
	 */
	public ControladorVida getControladorVida() {
		return controladorVida;
	}
	
	/**
	 * Devuelve el danho que hace un ataque del personaje.
	 * @return el danho que hace un ataque del personaje.
	 */
	public int getDanho() {
		return estadisticas.danhoBase();
	}
	
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
	
	/**
	 * Devuelve la faccion a la que pertenece el personaje.
	 * @return la faccion a la que pertenece el personaje.
	 */
	public abstract Faccion getFaccion();
}
