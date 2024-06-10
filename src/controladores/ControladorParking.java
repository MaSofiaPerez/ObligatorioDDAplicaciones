/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import exceptions.NuevoValorDemasiadoAltoException;
import exceptions.NuevoValorNegativoException;
import java.util.ArrayList;
import logica.Anomalia;
import logica.Cochera;
import logica.Parking;
import logica.Tarifario;
import logica.TipoVehiculo;
import logica.Vehiculo;

/**
 *
 * @author sofia
 */
public class ControladorParking {

    private ArrayList<Parking> parkings;

    public ControladorParking() {
        this.parkings = new ArrayList();
    }

    public ArrayList<Parking> getParkings() {
        return parkings;
    }

    public void agregar(Parking p) {
        parkings.add(p);
    }

    public int getTotalEstadias() {
        int ret = 0;
        for (Parking p : parkings) {
            ret += p.getTotalEstadias();
        }
        return ret;
    }

    public double getTotalFacturado() {
        double ret = 0;
        
        for(Parking p: parkings){
           ret +=  p.getTotalFacturado();
        }
        return ret;
    }
   
    public double getSubTotalFacturado(Parking p) {
        return p.getSubtotal();
    }

    public ArrayList<Anomalia> getAnomalias() {
        ArrayList<Anomalia> anomalias = new ArrayList();
        for (Parking p : parkings) {
            anomalias.addAll(p.getAnomalias());
        }
        return anomalias;
    }

    private double calcularPromedioTarifarios(TipoVehiculo tipo) {
        double total = 0;
        int cantTarifas = 0;
        for (Parking p : parkings) {
            for (Tarifario t : p.getTarifarios()) {
                if (t.getTipoVehiculo().equals(tipo)) {
                    total += t.getValorHora();
                    cantTarifas++;
                }
            }
        }
        if (cantTarifas > 0) {
            return total / cantTarifas;
        } else {
            return 0;
        }

    }

    public void cambiarPrecioUT(Tarifario tarifarioSeleccionado, double nuevoPrecio, Parking parking) throws NuevoValorDemasiadoAltoException, NuevoValorNegativoException {
        double doblePromedio = 2 * calcularPromedioTarifarios(tarifarioSeleccionado.getTipoVehiculo());
        
        if (nuevoPrecio < doblePromedio && nuevoPrecio > 0) {
            for (Parking p : parkings) {
                p.cambiarPrecioUT(tarifarioSeleccionado, nuevoPrecio);
            }
        } else if(nuevoPrecio >= doblePromedio) {
            throw new NuevoValorDemasiadoAltoException(doblePromedio);
        }else if(nuevoPrecio < 0){
            throw new NuevoValorNegativoException();
        }
    }

    public void ingresar(String nroCochera, Vehiculo v) {
        for(Parking p: parkings){
           for(Cochera c:p.getCocheras()){
               if(c.getCodigo().equals(nroCochera)){
                   p.agregarEstadia(v, c);
               }
           }
        }
    }
   

    public void egresar(String nroCochera, Vehiculo v) {
         for(Parking p: parkings){
           for(Cochera c:p.getCocheras()){
               if(c.getCodigo().equals(nroCochera)){
                   p.cerrarEstadia(v, c);
               }
           }
        }
    }

}
