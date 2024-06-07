package logica;

import java.util.ArrayList;
import java.util.Date;

public class Estadia {

    private Date fechaYHoraEntrada;

    private Date fechaYHoraSalida;

    private double totalFacturado;

    private Vehiculo vehiculo;

    private Cochera cochera;

    private ArrayList<Anomalia> anomalias;

    private ArrayList<Estadia> estadiasfinalizadas;

    private ArrayList<Multa> multas;

    public Estadia(Vehiculo vehiculo, Cochera cochera) {
        this.vehiculo = vehiculo;
        this.cochera = cochera;
        this.fechaYHoraEntrada = new Date();
        this.multas = new ArrayList();
        this.totalFacturado = 0;
    }

    public Date getFechaYHoraEntrada() {
        return fechaYHoraEntrada;
    }

    public void setFechaYHoraEntrada(Date nueva) {
        this.fechaYHoraEntrada = nueva;
    }

    public Date getFechaYHoraSalida() {
        return fechaYHoraSalida;
    }

    public void setFechaYHoraSalida(Date nueva) {
        this.fechaYHoraSalida = nueva;
    }

    public double getTotalFacturado() {
        return totalFacturado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo v) {
        this.vehiculo = v;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public ArrayList<Anomalia> getAnomalias() {
        return anomalias;
    }

    public ArrayList<Multa> getMultas() {
        return multas;
    }

    public void procesarEgreso() {
        TipoVehiculo tipoVehiculo = vehiculo.getTipoVehiculo();
        Parking p = cochera.getParking();
        ArrayList<Tarifario> tarifarios = p.getTarifarios();
        double tarifario = 0;
        for (Tarifario t : tarifarios) {
            if (t.getTipoVehiculo() == tipoVehiculo) {
                tarifario = t.getValorHora();
            }
        }
        double tiempoestadiaMinutos = CalcularTiempoEstadiaEnMinutos();
        double montoFacturado = calcularTotalFacturado(tiempoestadiaMinutos, tarifario);
        this.totalFacturado = montoFacturado;
        cochera.setEstadiaActual(null);
        debitarCuentaCorrientePropietario(montoFacturado);
    }

    public void setFechaYHoraEntrada() {
        fechaYHoraEntrada = new Date();
    }

    public void agregarMulta() {

        for (Etiqueta eCochera : cochera.getEtiquetas()) {
            for (Etiqueta eVehiculo : vehiculo.getEtiquetas()) {
                if (eCochera != eVehiculo) {
                    multas.add(new Multa());
                }
            }
        }

    }

    public double calcularMultas() {
        double total = 0;
        for (Multa m : multas) {
            total += m.calcularMulta(this);
        }
        return total;
    }

    public double CalcularTiempoEstadiaEnMinutos() {
        long diferenciaTiempo = fechaYHoraSalida.getTime() - fechaYHoraEntrada.getTime();
        return diferenciaTiempo / (60 * 1000);
    }

    private void debitarCuentaCorrientePropietario(double montoFacturado) {
        vehiculo.getPropietario().pagarEstadia(montoFacturado);
    }

    private double calcularTotalFacturado(double tiempoEstadia, double tarifa) {
        return (tarifa * tiempoEstadia * cochera.getParking().getFactorDeDemanda()) + calcularMultas();
    }

    public double calcularSubTotalFacturado() {
        double tiempo = CalcularTiempoEstadiaEnMinutos();
        double tarifa = cochera.getParking().getTarifario(vehiculo.getTipoVehiculo());
        return tarifa * tiempo * cochera.getParking().getFactorDeDemanda();
    }

    public void agregarAnomalia(Anomalia anomalia) {
        anomalias.add(anomalia);
    }

}
