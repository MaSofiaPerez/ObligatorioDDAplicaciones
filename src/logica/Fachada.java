package logica;
import controladores.ControladorVehiculo;
import java.util.ArrayList;

public class Fachada {

        private static final Fachada instancia = new Fachada();
	private ControladorVehiculo controladorVehiculo;
        private ArrayList<Parking> parkings;

        private Fachada(){
            this.controladorVehiculo = new ControladorVehiculo();
            this.parkings = new ArrayList();
        }
        
        public static Fachada getInstancia() {
        return instancia;
    }
        public ArrayList<Parking> getParkings(){
            return parkings;
        }
	
	public ArrayList<Vehiculo> getVehiculos() {
         return controladorVehiculo.getVehiculos();
	}

	
	public Vehiculo getVehiculo(String patente) {
		return  controladorVehiculo.getVehiculo(patente);
	}

}
