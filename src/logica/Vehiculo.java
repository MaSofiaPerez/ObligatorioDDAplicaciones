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
        
    public Vehiculo(String patente){
        this.patente = patente;
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
    

    @Override
    public boolean esDiscapacitado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean esElectrico() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean esEmpleado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
