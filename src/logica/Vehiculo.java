package logica;
import java.util.ArrayList;
public class Vehiculo {

	private String patente;

	private TipoVehiculo tipoVehiculo;

	private Propietario propietario;

	private ArrayList<Etiqueta> etiquetas;

    public Vehiculo(String patente, TipoVehiculo tipoVehiculo, Propietario propietario, ArrayList<Etiqueta> etiquetas) {
        this.patente = patente;
        this.tipoVehiculo = tipoVehiculo;
        this.propietario = propietario;
        this.etiquetas = etiquetas;
    }

        
        
    public String getPatente() {
        return patente;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public ArrayList<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

}
