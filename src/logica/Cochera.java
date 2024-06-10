package logica;

import java.util.ArrayList;
import simuladortransito.Estacionable;

public class Cochera implements Estacionable {

    private String codigo;

    private Estadia estadiaActual;

    private ArrayList<Etiqueta> etiquetas;

    private ArrayList<Estadia> estadias;

    private Parking parking;

    public Cochera(String codigo) {
        this.codigo = codigo;
        this.etiquetas = new ArrayList();
        this.estadiaActual = null;
        this.estadias = new ArrayList();
    }

    public void estacionarVehiculo(Estadia estadia) {
        this.estadiaActual = estadia;
        estadias.add(estadia);
    }

    public boolean estaLibre() {
        return estadiaActual == null;
    }

    public void desocupar() {
        estadiaActual = null;
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

    void setParking(Parking parking) {
        this.parking = parking;
    }

    private boolean tieneEtiqueta(String descripcion) {
        for (Etiqueta e : etiquetas) {
            if (e.getDescripcion().equals(descripcion)) {
                return true;
            }
        }
        return false;
    }

    public void addEtiqueta(Etiqueta e) {
        etiquetas.add(e);
    }

  /*  public double getFacturaEstadias() {
        int ret = 0;
        for (Estadia e : estadias) {
            ret += e.calcularSubTotalFacturado();
        }
        return ret;
    }*/

    @Override
    public boolean esDiscapacitado() {
        return tieneEtiqueta("Discapacitado");
    }

    @Override
    public boolean esElectrico() {
        return tieneEtiqueta("Electrico");
    }

    @Override
    public boolean esEmpleado() {
        return tieneEtiqueta("Empleado");
    }

    public double getSubtotal() {
        double ret = 0;
        for (Estadia e : estadias) {
            if (e.getFechaYHoraSalida() != null) {
                ret += e.calcularSubTotalFacturado();
            }
        }
        return ret;
    }

    public double getSubtotalMultas() {
        double ret = 0;
        for (Estadia e : estadias) {
            if (e.getFechaYHoraSalida() != null) {
                ret += e.calcularMultas();
            }
        }
        return ret;
    }

    public ArrayList<Anomalia> getAnomalias() {
        ArrayList<Anomalia> anomalias = new ArrayList();
        for (Estadia e : estadias) {
            if (e.getAnomalias().size() > 0) {
                anomalias.addAll(e.getAnomalias());
            }
        }

        return anomalias;
    }
    
   
}
