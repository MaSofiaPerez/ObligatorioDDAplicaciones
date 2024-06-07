package logica;

import interfaces.EscuchadorSensor;
import java.util.ArrayList;
import simuladortransito.Estacionable;
import simuladortransito.Sensor;
import simuladortransito.Transitable;

public class SensorParking implements Sensor {

    Parking parking;

    public void avisarIngreso(Vehiculo v, Cochera c) {
        parking.agregarEstadia(v, c);
    }

    public void avisarEgreso(Vehiculo v, Cochera c) {
        parking.cerrarEstadia(v, c);
    }

    @Override
    public void ingreso(Transitable transitable, Estacionable estacionable) {
        Vehiculo v = (Vehiculo) transitable;
        Cochera c = (Cochera) estacionable;
        avisarIngreso(v, c);
    }

    @Override
    public void egreso(Transitable transitable, Estacionable estacionable) {
        Vehiculo v = (Vehiculo) transitable;
        Cochera c = (Cochera) estacionable;
        avisarEgreso(v, c);
    }

}
