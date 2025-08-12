package juegoMedieval;

import j2d.GuiaObjeto;

/**
 * Duende enemigo que persigue y ataca al jugador.
 * 
 * @author Oscar Gonzalez Garcia
 * @version jun-2025
 */
public class DuendePiromano extends AtacanteMele {
	
	private static final int INDICE_Z = 20;
	private static final String DIR_SPRITES = "resources/torch_goblin_red/"; 
	private static final String RUTA_SONIDO_ATAQUE = "resources/sounds/attacks/torch_attack.wav";
	
	public static final int ALTURA_DUENDE = 80;
	public static final int ANCHURA_DUENDE = 60;
	private static final int VELOCIDAD_BASE = 8;
	
	private static final int SALUD_MAX_BASE = 70;
	private static final int DANHO_BASE = 10;

	private Personaje objetivo;
	
	public DuendePiromano(String nombre) {
		super(nombre, ANCHURA_DUENDE, ALTURA_DUENDE,
				new EstadisticasPersonaje(SALUD_MAX_BASE, DANHO_BASE, VELOCIDAD_BASE),
				new RecursosPersonaje(DIR_SPRITES, RUTA_SONIDO_ATAQUE));
		asignaZ(INDICE_Z);
	}

	/**
	 * {@inheritDoc}. Para el {@link DuendePiromano}, busca a un objetivo que
	 * perseguir, se dirige a el, y lo ataca cuando esta cerca.
	 */
	@Override
	public void ciclo() {
		GuiaObjeto guia = guia();
		objetivo = (Personaje) escena().buscaObj("jugador");
		super.ciclo();
		if (!estaMuerto() && objetivo != null && !objetivo.estaMuerto() && !ataqueEnCurso()) {
			
				
			if (objetivo == null || ((Personaje) objetivo).estaMuerto()) {
					return;
			}
				
			if (objetivo.centro().distance(centro()) > (ALTURA_DUENDE + objetivo.altoY()) / 1.5) {
				guia.dirigeAPunto(objetivo.centro(), VELOCIDAD_BASE);			
			} else {
				guia.finalizaSeguimiento();
				ataca();
			}
		} else {
			guia.finalizaSeguimiento();
		}
	}

	@Override
	public Faccion getFaccion() {
		return Faccion.DUENDES;
	}
}
