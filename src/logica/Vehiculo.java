package logica;
import java.util.ArrayList;
import simuladortransito.Transitable;


public class Vehiculo implements Transitable {

	private String patente;

	private TipoVehiculo tipoVehiculo;

	private Propietario propietario;

	private ArrayList<Etiqueta> etiquetas;

    public Vehiculo(String patente, TipoVehiculo tipoVehiculo, Propietario propietario) {
        this.patente = patente;
        this.tipoVehiculo = tipoVehiculo;
        this.propietario = propietario;
        propietario.agregarVehiculo(this);
        this.etiquetas = new ArrayList();
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
    
    public void addEtiqueta(Etiqueta e){
        etiquetas.add(e);
    }
    
     private boolean tieneEtiqueta(String descripcion) {
        for (Etiqueta e : etiquetas) {
            if (e.getDescripcion().equals(descripcion)) {
                return true;
            }
        }
        return false;
    }
    

    @Override
    public boolean esDiscapacitado() {
        
        return tieneEtiqueta("Discapacitado");
    }

    @Override
    public boolean esElectrico() {
        return tieneEtiqueta("Electrico");
    }

    @Override
    public boolean esEmpleado() {
        return tieneEtiqueta("Empleado");
    }

}
