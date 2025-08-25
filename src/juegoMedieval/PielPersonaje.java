package juegoMedieval;


import java.util.EnumMap;

import j2d.JObjeto;
import j2d.JObjetoIcono;
import j2d.mods.IVidaControlada;
import j2d.utils.Animacion;
import juegoMedieval.Estado.Accion;
import juegoMedieval.Estado.Direccion;

/**
 * Representa la "piel" de un personaje, es decir, su parte visible. Se encarga
 * de gestionar las animaciones.
 * 
 * Cada animacion se corresponde a uno o varios {@link Estado estados}.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public class PielPersonaje extends JObjetoIcono implements IVidaControlada {
	
	private final EnumMap<Accion, EnumMap<Direccion, Animacion>> animacionesAccion;
	
	/**
	 * Crea una piel de personaje.
	 * @param nombre nombre del objeto.
	 * @param recursos recursos del personaje.
	 */
	public PielPersonaje(String nombre, RecursosPersonaje recursos) {
  		super(nombre, recursos.icono(), 1);
  		animacionesAccion = recursos.animacionesAccion();
	}
	
	/**
	 * Cambia la animacion que se esta reproduciendo.
	 * @param estado el estado correspondiente a la animacion deseada.
	 */
	public void cambiaAnimacion(Estado estado) {
		Animacion animacion;
		if (estado.accion() == Accion.ATACANDO || estado.direccionPrimaria().esHorizontal()) {
			animacion = animacionesAccion.get(estado.accion()).get(estado.direccionPrimaria());
		} else if (estado.direccionSecundaria().esHorizontal()){
			animacion = animacionesAccion.get(estado.accion()).get(estado.direccionSecundaria());
		} else {
			animacion = animacionesAccion.get(estado.accion()).get(Direccion.DERECHA);
		}
		
		animador().reproduce(animacion);
	}

	@Override
	public void pierdeVida(float decrementoVida) {
		escena().incluyeObj(new IndicadorDanho((int) decrementoVida),
				objMaestro().centro().x, objMaestro().centro().y);
	}

	@Override
	public void recuperaVida(float incrementoVida) {
		// metodo innecesario
	}

	@Override
	public void vidaAgotada() {
		((Personaje) objMaestro()).muere();
	}

	@Override
	public void finalizaAnimacionMuerte() {
		JObjeto personaje = objMaestro();
		personaje.escena().eliminaObj(personaje);
	}
	
}
