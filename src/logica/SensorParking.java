package logica;

import interfaces.EscuchadorSensor;
import java.util.ArrayList;
import simuladortransito.Estacionable;
import simuladortransito.Sensor;
import simuladortransito.Transitable;

public class SensorParking implements Sensor {

    Parking parking;
    
    public SensorParking(Parking p){
        
        this.parking = p;
    }

    @Override
    public void ingreso(Transitable transitable, Estacionable estacionable) {
        Vehiculo v = (Vehiculo) transitable;
        Cochera c = (Cochera) estacionable;
        parking.agregarEstadia(v,c);
    }

    @Override
    public void egreso(Transitable transitable, Estacionable estacionable) {
        Vehiculo v = (Vehiculo) transitable;
        Cochera c = (Cochera) estacionable;
        parking.cerrarEstadia(v, c);
    }

}
