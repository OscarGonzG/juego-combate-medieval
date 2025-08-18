package juegoMedieval;

import java.awt.Color;
import java.awt.Image;

import j2d.JObjetoIcono;
import j2d.mods.IVidaControlada;
import j2d.utils.Animacion;
import j2d.utils.Diapositiva;
import j2d.utils.ImagenesUtils;
import j2d.utils.Sonido;

/**
 * Representa un edificio de una faccion.
 * 
 * @author Oscar Gonzalez Garcia
 * @version ago-2025
 */
public abstract class Edificio extends EntidadFaccion implements IVidaControlada {
	
	private static final int INDICE_Z = 10;
	private JObjetoIcono iconoEdificio;
	private static final int DURACION_FRAME_MS = 100;
	private final Image imagenDestruido;
	private final Sonido sonidoDestruido;

	public Edificio(String nombre, int anchoX, int altoY, RecursosEdificio recursos, Color colorColisionador) {
		super(nombre, anchoX, altoY, colorColisionador);
		this.imagenDestruido = ImagenesUtils.creaImagen(recursos.directorioSprites() + "destroyed.png");
		this.sonidoDestruido = new Sonido(recursos.rutaSonidoDestruccion());
		
		iconoEdificio = new JObjetoIcono(nombre + ".icono", recursos.directorioSprites() + "standing1.png");
		if (recursos.numSpritesAnimacion() > 1) {
			Diapositiva[] diapositivas = new Diapositiva[recursos.numSpritesAnimacion()];
			
			for (int i = 1; i <= recursos.numSpritesAnimacion(); i++) {
				diapositivas[i - 1] = new Diapositiva(ImagenesUtils.creaImagen(recursos.directorioSprites() + "standing" + i + ".png"), 1, DURACION_FRAME_MS);
			}
			
			iconoEdificio.animador().reproduce(new Animacion(true, diapositivas));
		}
		int despAdornoX = (anchoX - iconoEdificio.anchoX()) / 2;
		int despAdornoY = (altoY - iconoEdificio.altoY()) / 2;
		adornoAnhade(iconoEdificio, despAdornoX, despAdornoY);
		asignaZ(INDICE_Z);
	}
	
	@Override
	public void pierdeVida(float decrementoVida) {
		escena().incluyeObj(new IndicadorDanho((int) decrementoVida),
				centro().x, centro().y);
	}

	@Override
	public void recuperaVida(float incrementoVida) {
		// TODO Auto-generated method stub
	}

	@Override
	public void vidaAgotada() {
		sonidoDestruido.suena();
		iconoEdificio.cambiaImagen(imagenDestruido);
		muere();
	}

	@Override
	public void finalizaAnimacionMuerte() {
		// TODO Auto-generated method stub
	}
}
