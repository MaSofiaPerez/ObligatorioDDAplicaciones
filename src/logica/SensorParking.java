package logica;
import interfaces.EscuchadorSensor;
import java.util.ArrayList;
import simuladortransito.Estacionable;
import simuladortransito.Sensor;
import simuladortransito.Transitable;


public class SensorParking implements Sensor{

	private ArrayList<EscuchadorSensor> escuchadoresSensor;
         
	/**
	 * for(EscuchadorSensor escuchador: escuchadores){
	 * e.ingreso(vehiculo, cochera)
	 * }
	 */
	public void avisarIngreso(String patente, String codigoCochera) {

	}

	public void avisarEgreso(String patente, String codigoCochera) {

	}

	public void add(EscuchadorSensor escuchador) {

	}

	public void remove(EscuchadorSensor escuchador) {

	}

    @Override
    public void ingreso(Transitable transitable, Estacionable estacionable) {
        
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void egreso(Transitable transitable, Estacionable estacionable) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
