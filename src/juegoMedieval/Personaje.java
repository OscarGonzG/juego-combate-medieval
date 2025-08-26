package juegoMedieval;

import j2d.JObjeto;
import j2d.mods.ControladorVida;
import j2d.mods.ITemporizado;
import j2d.mods.IVisualizadorNumerico;
import j2d.mods.Temporizador;
import j2d.mods.Temporizador.TipoCuenta;
import juegoMedieval.Estado.Accion;
import juegoMedieval.Estado.Direccion;

/**
 * Representa un personaje que puede moverse y atacar en 4 direcciones.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public abstract class Personaje extends EntidadFaccion implements ITemporizado {
		
	
	private final Temporizador temporizadorAtaque;
	private final PielPersonaje piel;
	private final EstadisticasPersonaje estadisticas;

	private boolean puedeAtacar = true;
	
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
	protected Personaje(String nombre, int anchoX, int altoY,
					 EstadisticasPersonaje estadisticas,
					 IVisualizadorNumerico barraVida,
					 IVisualizadorNumerico visualizadorCooldownAtaque,
					 RecursosPersonaje recursos) {
		super(nombre, anchoX, altoY, recursos.colorColisionador());
		this.piel = new PielPersonaje(nombre + ".piel", recursos);
		
		this.estadisticas = estadisticas;
		this.temporizadorAtaque = new Temporizador(visualizadorCooldownAtaque, 
				this, RecursosPersonaje.DURACION_ATAQUE_MS, false,
				TipoCuenta.CUENTA_ASCENDENTE);
		
		ControladorVida controladorVida = new ControladorVida(estadisticas.saludBase(), barraVida, piel);
		controladorVida.configuraAccionesMuerte(RecursosPersonaje.ANIMACION_MUERTE);
		setControladorVida(controladorVida);
		
		int despAdornoX = (anchoX - piel.anchoX()) / 2;
		int despAdornoY = (altoY - piel.altoY()) / 2;
		
		adornoAnhade(piel, despAdornoX, despAdornoY);
		estado = new Estado(Direccion.DERECHA, Accion.PARADO);
		
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
		this(nombre, anchoX, altoY, estadisticas, null, null, recursos);
	}
	
	protected JObjeto getVisualizadorRefrescoAtaque() {
		return temporizadorAtaque.objVisualizador();
	}

	/**
	 * Obtiene la direccion en la que mira el personaje.
	 * @return
	 */
	public Direccion getDireccion() {
		return estado.direccionPrimaria();
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
		if (d.equals(estado.direccionPrimaria()) || estaMuerto()) {
			return false;
		}
		cambiaDireccion(d, d);
		return true;
	}
	
	/**
	 * Hace que el personaje mire a la direccion indicada.
	 * @param primaria la direccion primaria a la que pasa a mirar.
	 * @param secundaria la direccion secundaria a la que pasa a mirar.
	 * @return true si ha podido cambiar de direccion, false en caso contrario.
	 */
	public boolean cambiaDireccion(Direccion primaria, Direccion secundaria) {
		if ((primaria.equals(estado.direccionPrimaria()) && secundaria.equals(estado.direccionSecundaria())) || estaMuerto()) {
			return false;
		}

		cambiaEstado(new Estado(primaria, secundaria, estado.accion()));
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

		cambiaEstado(new Estado(estado.direccionPrimaria(), estado.direccionSecundaria(), a));
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
				cambiaAccion(Accion.CAMINANDO);

				Direccion direccionX = velX > 0 ? 
						Direccion.DERECHA : Direccion.IZQUIERDA;
				Direccion direccionY = velY > 0 ? 
						Direccion.ABAJO : Direccion.ARRIBA;

				Direccion direccionPrincipal = Math.abs(velX) >= Math.abs(velY) ?
								direccionX : direccionY;
				Direccion direccionSecundaria;
				if (Math.abs(velX) >= Math.abs(velY)) {
					direccionPrincipal = direccionX;
					direccionSecundaria = direccionY;
				} else {
					direccionPrincipal = direccionY;
					direccionSecundaria = direccionX;
				}
				cambiaDireccion(direccionPrincipal, direccionSecundaria);
			}
		}
	}

	
	/**
	 * Realiza un ataque.
	 * @return true si ha podido iniciar el ataque, false en caso contrario.
	 */
	protected boolean ataca() {
		if (cambiaAccion(Accion.ATACANDO)) {
			puedeAtacar = false;
			return true;
		}
		return false;
	}
	

	/**
	 * Devuelve el danho que hace un ataque del personaje.
	 * @return el danho que hace un ataque del personaje.
	 */
	public int getDanho() {
		return estadisticas.danhoBase();
	}
}
