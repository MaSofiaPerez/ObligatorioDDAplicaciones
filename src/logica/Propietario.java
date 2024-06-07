package logica;

import java.util.ArrayList;

public class Propietario {

    private int cedula;

    private String nombreCompleto;

    private CuentaCorriente cuentaCorriente;
    
    private ArrayList<Vehiculo> vehiculos;

    public Propietario(int cedula, String nombreCompleto, CuentaCorriente cc) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.cuentaCorriente = cc;
        this.vehiculos = new ArrayList();
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public ArrayList<Vehiculo> getVehiculos(){
        return vehiculos;
    }
    
    public void agregarVehiculo(Vehiculo v){
        vehiculos.add(v);
    }


    public void pagarEstadia(double monto) {
        cuentaCorriente.restar(monto);
    }

}
