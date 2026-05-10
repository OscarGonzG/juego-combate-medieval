package juegoCombateMedieval;


import java.util.EnumMap;

import j2d.JObjetoIcono;
import j2d.mods.IVidaControlada;
import j2d.utils.Animacion;
import juegoCombateMedieval.Estado.Accion;
import juegoCombateMedieval.Estado.Direccion;

/**
 * Representa la "piel" de un personaje, es decir, su parte visible. Se encarga
 * de gestionar las animaciones.
 * 
 * Cada animacion se corresponde a uno o varios {@link Estado estados}.
 * 
 * @author Oscar Gonzalez Garcia
 * @version may-2026
 */
public class PielPersonaje extends JObjetoIcono implements IVidaControlada {
	
	private final EnumMap<Accion, EnumMap<Direccion, Animacion>> animacionesAccion;
	private final Personaje personaje;
	
	/**
	 * Crea una piel de personaje.
	 * @param nombre nombre del objeto.
	 * @param personaje personaje al que pertenece esta piel.
	 */
	public PielPersonaje(String nombre, Personaje personaje) {
  		super(nombre, personaje.getRecursosPersonaje().icono(), 1);
  		this.personaje = personaje;
  		animacionesAccion = personaje.getRecursosPersonaje().animacionesAccion();
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
		personaje.escena().incluyeObj(new IndicadorDanho((int) decrementoVida),
				personaje.centro().x, personaje.centro().y);
	}

	@Override
	public void recuperaVida(float incrementoVida) {
		// metodo innecesario
	}

	@Override
	public void vidaAgotada() {
		personaje.muere();
	}

	@Override
	public void finalizaAnimacionMuerte() {
		personaje.escena().eliminaObj(personaje);
	}
	
}
