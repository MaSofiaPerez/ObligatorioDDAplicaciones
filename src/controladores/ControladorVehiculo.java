package controladores;

import java.util.ArrayList;
import logica.Vehiculo;

public class ControladorVehiculo {

    private ArrayList<Vehiculo> vehiculos;
    
    public ControladorVehiculo(){
        this.vehiculos = new ArrayList();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    public void agregar(Vehiculo v){
        vehiculos.add(v);
    }

    public Vehiculo getVehiculo(String patente) {
        for(Vehiculo v: vehiculos){
            if(v.getPatente().equals(patente)){
                return v;
            }
        }
        return null;
    }
    


}
