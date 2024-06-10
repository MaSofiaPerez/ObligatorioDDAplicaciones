package logica;

import controladores.ControladorParking;
import controladores.ControladorVehiculo;
import controladores.ControladorPropietario;
import controladores.ControladorTipoVehiculo;
import exceptions.NuevoValorDemasiadoAltoException;
import exceptions.NuevoValorNegativoException;
import java.util.ArrayList;
import observer.Observable;
import observer.Observador;

public class Fachada {

    private static final Fachada instancia = new Fachada();
    private ControladorVehiculo controladorVehiculo;
    private ControladorParking controladorParking;
    private ControladorPropietario controladorPropietario;
    private ControladorTipoVehiculo controladorTiposVehiculo;


    private Fachada() {
        this.controladorVehiculo = new ControladorVehiculo();
        this.controladorPropietario = new ControladorPropietario();
        this.controladorParking = new ControladorParking();
        this.controladorTiposVehiculo = new ControladorTipoVehiculo();
    }

    public static Fachada getInstancia() {
        return instancia;
    }

    public ArrayList<Parking> getParkings() {
        return controladorParking.getParkings();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return controladorVehiculo.getVehiculos();
    }
    
    public ArrayList<Propietario> getPropietarios(){
        return controladorPropietario.getPropietarios();
    }
    public ArrayList<TipoVehiculo> getTiposVehiculo(){
       return controladorTiposVehiculo.getTiposVehiculos();
    }
    
    public void agregar(Parking p){
        controladorParking.agregar(p);
    }
    public void agregar(Vehiculo v){
        controladorVehiculo.agregar(v);
    }
    public void agregar(Propietario p){
        controladorPropietario.agregar(p);
    }
    
    public void agregar(TipoVehiculo tipo){
        controladorTiposVehiculo.agregar(tipo);
    }
    
    public int getTotalEstadias(){
        return controladorParking.getTotalEstadias();
    }
    
    public double getSubtotalFacturadoParkings(){
        return controladorParking.getTotalFacturado();
    }

    public ArrayList<Anomalia> getAnomalias() {
        return controladorParking.getAnomalias();
    }

    public void cambiarPrecioUT(Tarifario tarifarioSeleccionado, double nuevoPrecio, Parking parking) throws NuevoValorDemasiadoAltoException, NuevoValorNegativoException{
        controladorParking.cambiarPrecioUT(tarifarioSeleccionado, nuevoPrecio, parking);
    }

    
            
}
