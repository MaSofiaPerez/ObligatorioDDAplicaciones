package logica;

public class Tarifario {

	/**
	 * 0.05 motos
	 * 0.1 resto
	 */
	private double valorHora;

	private TipoVehiculo tipoVehiculo;

    public Tarifario(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public double getValorHora() {
        return valorHora;
    }

   
        
    

}
