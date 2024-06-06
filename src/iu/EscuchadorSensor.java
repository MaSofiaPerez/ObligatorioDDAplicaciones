package iu;

public interface EscuchadorSensor {

	public abstract void ingreso(String patente, String codigoCochera);

	public abstract void egreso(String patente, String codigoCochera);

}
