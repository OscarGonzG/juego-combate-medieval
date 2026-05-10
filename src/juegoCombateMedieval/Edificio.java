package juegoCombateMedieval;

import j2d.JObjetoIcono;
import j2d.mods.IVidaControlada;
import j2d.utils.Animacion;

/**
 * Representa un edificio de una faccion.
 * 
 * @author Oscar Gonzalez Garcia
 * @version may-2026
 */
public abstract class Edificio extends EntidadFaccion implements IVidaControlada {

	private JObjetoIcono iconoEdificio;
	private final RecursosEdificio recursos;

	/**
	 * Crea un edificio.
	 * @param nombre nombre del edificio.
	 * @param anchoX anchura del aedificio.
	 * @param altoY altura del edificio.
	 * @param recursos recursos graficos y de audio del edificio.
	 * @param colorColisionador color del colisionador.
	 */
	protected Edificio(String nombre, int anchoX, int altoY, RecursosEdificio recursos, EscenaCombate escena) {
		super(nombre, anchoX, altoY, recursos.colorColisionador(), escena);
		this.recursos = recursos;
		iconoEdificio = new JObjetoIcono(nombre + ".icono", recursos.icono(), 1);
		Animacion animacion = recursos.animacion();
		if (animacion != null) {
			iconoEdificio.animador().reproduce(animacion);
		}
		
		int despAdornoX = (anchoX - iconoEdificio.anchoX()) / 2;
		int despAdornoY = (altoY - iconoEdificio.altoY()) / 2;
		adornoAnhade(iconoEdificio, despAdornoX, despAdornoY);
	}
	
	@Override
	public void pierdeVida(float decrementoVida) {
		escena().incluyeObj(new IndicadorDanho((int) decrementoVida),
				centro().x, centro().y);
	}

	@Override
	public void recuperaVida(float incrementoVida) {
		// metodo innecesario
	}

	@Override
	public void vidaAgotada() {
		recursos.sonidoDestruccion().suena();
		iconoEdificio.cambiaImagen(recursos.iconoDestruido());
		iconoEdificio.animador().finalizaAnimacion();
		muere();
	}

	@Override
	public void finalizaAnimacionMuerte() {
		// metodo innecesario
	}
}
