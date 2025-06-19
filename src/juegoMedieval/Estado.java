package juegoMedieval;


/**
 * Representa un estado del personaje, compuesto por la direccion a la que esta
 * mirando y la accion que realiza.
 */
public record Estado(Direccion direccion, Accion accion) {
	public enum Direccion {
		ARRIBA,
		ABAJO,
		IZQUIERDA,
		DERECHA
	}

	public enum Accion {
		PARADO,
		EN_MOVIMIENTO,
		ATACANDO
	}
	public static final Estado PARADO_DERECHA = new Estado(Direccion.DERECHA, Accion.PARADO);
	public static final Estado PARADO_IZQUIERDA = new Estado(Direccion.IZQUIERDA, Accion.PARADO);
	public static final Estado PARADO_ARRIBA = new Estado(Direccion.ARRIBA, Accion.PARADO);
	public static final Estado PARADO_ABAJO = new Estado(Direccion.ABAJO, Accion.PARADO);
	
	public static final Estado MOVIENDO_DERECHA = new Estado(Direccion.DERECHA, Accion.EN_MOVIMIENTO);
	public static final Estado MOVIENDO_IZQUIERDA = new Estado(Direccion.IZQUIERDA, Accion.EN_MOVIMIENTO);
	public static final Estado MOVIENDO_ARRIBA = new Estado(Direccion.ARRIBA, Accion.EN_MOVIMIENTO);
	public static final Estado MOVIENDO_ABAJO = new Estado(Direccion.ABAJO, Accion.EN_MOVIMIENTO);
	
	public static final Estado ATACANDO_DERECHA = new Estado(Direccion.DERECHA, Accion.ATACANDO);
	public static final Estado ATACANDO_IZQUIERDA = new Estado(Direccion.IZQUIERDA, Accion.ATACANDO);
	public static final Estado ATACANDO_ARRIBA = new Estado(Direccion.ARRIBA, Accion.ATACANDO);
	public static final Estado ATACANDO_ABAJO = new Estado(Direccion.ABAJO, Accion.ATACANDO);
}
