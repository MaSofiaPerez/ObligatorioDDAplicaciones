package logica;
import controladores.ControladorVehiculo;
import java.util.ArrayList;

public class Fachada {

	private ControladorVehiculo controladorVehiculo;

	
	public ArrayList<Vehiculo> getVehiculos() {
         return controladorVehiculo.getVehiculos();
	}

	
	public Vehiculo getVehiculo(String patente) {
		return  controladorVehiculo.getVehiculo(patente);
	}

}
