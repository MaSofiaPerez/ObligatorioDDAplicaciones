package logica;

public class Tarifario {

    private double valorHora;

    private TipoVehiculo tipoVehiculo;

    public Tarifario(TipoVehiculo tipoVehiculo, double valorHora) {
        this.tipoVehiculo = tipoVehiculo;
        this.valorHora = valorHora;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public double getValorHora() {
        return valorHora;
    }

}
