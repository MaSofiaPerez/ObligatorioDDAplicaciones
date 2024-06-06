/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication7;

import java.util.ArrayList;
import logica.Cochera;
import logica.Fachada;
import logica.Parking;
import logica.Vehiculo;
import simuladortransito.Estacionable;
import simuladortransito.FlujoIngreso;
import simuladortransito.Periodo;
import simuladortransito.Sensor;
import simuladortransito.SimuladorTransito;
import simuladortransito.Transitable;
import logica.SensorParking;

/**
 *
 * @author sofia
 */
public class JavaApplication7 {
    
    private static SimuladorTransito simulador;
    
    

    /**privat 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //1. Configurar simulador
        ArrayList<Transitable> vehiculos = generarVehiculos(10);
        ArrayList<Estacionable> cocheras = generarCocheras(5);
        
        simulador = SimuladorTransito.getInstancia();
        simulador.addTransitables(vehiculos);
        simulador.addEstacionables(cocheras);
        //2. Programarlo
        try{
            simulador.programar(new FlujoIngreso("Ingreso matutino", new Periodo(0, 5), 3));
            //3. Ejecutarlo
            Sensor sensorParking = new SensorParking();
            simulador.iniciar(sensorParking);
        }catch(Exception e){}        
        
        
    }
    
    private static ArrayList<Transitable> generarVehiculos(int cantidad){
        Fachada fachada = Fachada.getInstancia();
        ArrayList<Vehiculo> vehiculos = fachada.getVehiculos();
        ArrayList<Transitable> ret = new ArrayList<>();
        for(int i = 0; i < cantidad && i < vehiculos.size(); i++){
            ret.add(ret.get(i));
        }
        return ret;    
    }
    
    private static ArrayList<Estacionable> generarCocheras(int cantidad){
        Parking parking = new Parking();
        ArrayList<Cochera> cocheras = parking.getCocheras();        
        ArrayList<Estacionable> ret = new ArrayList<>();
        for(int i = 0; i < cantidad && i < cocheras.size(); i++){
            ret.add(cocheras.get(i));
        }
        return ret; 
    }
    
}
