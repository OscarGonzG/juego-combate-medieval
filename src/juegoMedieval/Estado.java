package juegoMedieval;

/**
 * Representa un estado del personaje, compuesto por la direccion a la que esta
 * mirando y la accion que realiza.
 * 
 * La direccion a su vez se descompone en direccion primaria y secundaria para
 * indicar direcciones diagonales. La direccion secundaria puede ser igual que
 * la primaria, implicando alineacion a un eje, pero nunca opuesta.
 */
public record Estado(Direccion direccionPrimaria, Direccion direccionSecundaria, Accion accion) {
	public enum Direccion {
		ARRIBA,
		ABAJO,
		IZQUIERDA,
		DERECHA;
		
		private Direccion direccionOpuesta;
		private boolean esHorizontal;
		
		static {
			ARRIBA.direccionOpuesta = ABAJO;
			ABAJO.direccionOpuesta = ARRIBA;
			IZQUIERDA.direccionOpuesta = DERECHA;
			DERECHA.direccionOpuesta = IZQUIERDA;
			
			ARRIBA.esHorizontal = false;
			ABAJO.esHorizontal = false;
			IZQUIERDA.esHorizontal = true;
			DERECHA.esHorizontal = true;
		}
		
		/**
		 * Devuelve la direccion opuesta.
		 * @return la direccion opuesta.
		 */
		public Direccion direccionOpuesta() {
			return direccionOpuesta;
		}
		
		/**
		 * Indica si esta direccion es horizontal.
		 * @return true si es horizontal, false en caso contrario.
		 */
		public boolean esHorizontal() {
			return esHorizontal;
		}
		
	}

	public enum Accion {
		PARADO,
		CAMINANDO,
		ATACANDO
	}
	
	/**
	 * Crea un estado.
	 * @param direccionPrimaria direccion primaria a la que se esta mirando.
	 * @param direccionSecundaria direccion secundaria no opuesta a la primaria.
	 * @param accion accion que se esta ejecutando.
	 */
	public Estado(Direccion direccionPrimaria, Direccion direccionSecundaria, Accion accion) {
		if (direccionPrimaria.direccionOpuesta().equals(direccionSecundaria)) {
			throw new IllegalArgumentException("La direccion secundaria no puede ser opuesta a la primaria");
		}
		this.direccionPrimaria = direccionPrimaria;
		this.direccionSecundaria = direccionSecundaria;
		this.accion = accion;
	}
	
	/**
	 * Crea un estado con una direccion alineada al eje.
	 * @param direccion direccion a la que se esta mirando. Se utilizara para
	 * inicializar tanto la direccion primaria como la secundaria.
	 * @param accion accion que se esta ejecutando.
	 */
	public Estado(Direccion direccion, Accion accion) {
		this(direccion, direccion, accion);
	}
}
