package controladores;

import java.util.ArrayList;
import logica.Vehiculo;

public class ControladorVehiculo {

    private ArrayList<Vehiculo> vehiculos;

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public Vehiculo getVehiculo(String patente) {
        for (Vehiculo v : vehiculos) {
            if (v.getPatente().equals(patente)) {
                return v;
            }
        }
        return null;
    }

}
