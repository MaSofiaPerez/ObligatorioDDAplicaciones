package logica;
import java.util.ArrayList;
import simuladortransito.Estacionable;

public class Cochera implements Estacionable{

	private String codigo;

	private Estadia estadiaActual;

	private ArrayList<Etiqueta> etiquetas;
        
	private ArrayList<Estadia> estadias;
	private Parking parking;

    public Cochera(String codigo, ArrayList<Etiqueta> etiquetas, Parking parking) {
        this.codigo = codigo;
        this.etiquetas = etiquetas;
        this.parking = parking;
    }

    public Cochera() {
    }
	 
        
        
        
	public void estacionarVehiculo(Estadia estadia) {
          this.estadiaActual = estadia;
	}
     public boolean tieneEtiqueta(String etiqueta) {
     for(Etiqueta e:etiquetas){
     if(e.getDescripcion().equals(etiqueta))
         return true;
     }
     return false;
     }

        
	public boolean estaLibre() {
		return estadiaActual == null;
	}

    public void setEstadiaActual(Estadia estadiaActual) {
        this.estadiaActual = estadiaActual;
    }

	public void desocupar() {
           estadias.add(estadiaActual);
	       setEstadiaActual(null);
	}

    public String getCodigo() {
        return codigo;
    }

    public Estadia getEstadiaActual() {
        return estadiaActual;
    }

    public ArrayList<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public ArrayList<Estadia> getEstadias() {
        return estadias;
    }

    public Parking getParking() {
        return parking;
    }

    @Override
    public boolean esDiscapacitado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean esElectrico() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean esEmpleado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
