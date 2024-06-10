package logica;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Estadia {

    private LocalDateTime fechaYHoraEntrada;

    private LocalDateTime fechaYHoraSalida;

    private double totalFacturado;

    private Vehiculo vehiculo;

    private Cochera cochera;

    private ArrayList<Anomalia> anomalias;

    private ArrayList<Multa> multas;

    public Estadia(Vehiculo vehiculo, Cochera cochera) {
        this.vehiculo = vehiculo;
        this.cochera = cochera;
        this.fechaYHoraEntrada = LocalDateTime.now();
        this.fechaYHoraSalida = null;
        this.multas = new ArrayList();
        this.anomalias = new ArrayList();
        this.totalFacturado = 0;
    }

    public LocalDateTime getFechaYHoraEntrada() {
        return fechaYHoraEntrada;
    }

    public void setFechaYHoraEntrada(LocalDateTime nueva) {
        this.fechaYHoraEntrada = nueva;
    }

    public LocalDateTime getFechaYHoraSalida() {
        return fechaYHoraSalida;
    }

    public void setFechaYHoraSalida(LocalDateTime nueva) {
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

    public void setFechaYHoraEntrada() {
        fechaYHoraEntrada = LocalDateTime.now();
    }

    public void procesarEgreso() {
        TipoVehiculo tipoVehiculo = vehiculo.getTipoVehiculo();
        Parking p = cochera.getParking();
        ArrayList<Tarifario> tarifarios = p.getTarifarios();
        double tarifario = 0;
        for (int i = 0; i < tarifarios.size(); i++) {
            Tarifario t = tarifarios.get(i);
            if (t.getTipoVehiculo() == tipoVehiculo) {
                tarifario = t.getValorHora();
            }
        }
        agregarMulta();
        double tiempoestadiaMinutos = CalcularTiempoEstadia();
        double montoFacturado = calcularTotalFacturado(tiempoestadiaMinutos, tarifario);
        this.totalFacturado = montoFacturado;
        cochera.desocupar();
        debitarCuentaCorrientePropietario(montoFacturado);

    }

    public void agregarMulta() {

        Set<Etiqueta> etiquetasVehiculo = new HashSet<>(vehiculo.getEtiquetas());
        Set<Etiqueta> etiquetasCochera = new HashSet<>(cochera.getEtiquetas());

        // Obtener etiquetas que están en el vehículo pero no en la cochera
        etiquetasCochera.removeAll(etiquetasVehiculo);

        // Agregar una multa por cada etiqueta faltante en la cochera
        for (Etiqueta etiqueta : etiquetasCochera) {
            switch (etiqueta.getDescripcion()) {
                case "Discapacitado":
                    multas.add(new Multa(new MultaDiscapacitado()));
                    break;
                case "Electrico":
                    multas.add(new Multa(new MultaElectrico()));
                    break;
                case "Empleado":
                    multas.add(new Multa(new MultaEmpleadoInterno()));
                    break;
            }
        }

    }

    public double calcularMultas() {
        double total = 0;
        if (!multas.isEmpty()) {
            for (Multa m : multas) {
                total += m.calcularMulta(this);
            }
        }

        return total;
    }

    public double CalcularTiempoEstadia() {
        return ChronoUnit.SECONDS.between(fechaYHoraEntrada, fechaYHoraSalida);

    }

    private void debitarCuentaCorrientePropietario(double montoFacturado) {
        vehiculo.getPropietario().pagarEstadia(montoFacturado);
    }

    private double calcularTotalFacturado(double tiempoEstadia, double tarifa) {
        return (tarifa * tiempoEstadia * cochera.getParking().getFactorDeDemanda()) + calcularMultas();
    }

    public double calcularSubTotalFacturado() {
        if (fechaYHoraSalida != null) {
            double tiempo = CalcularTiempoEstadia();
            double tarifa = cochera.getParking().getTarifario(vehiculo.getTipoVehiculo());
            return tarifa * tiempo * cochera.getParking().getFactorDeDemanda();
        } else {
            return 0;
        }

    }

    public boolean agregarAnomalia(Anomalia anomalia) {
        boolean agregarOK = false;
        if (anomalias.add(anomalia)) {
            agregarOK = true;
            anomalia.setEstadia(this);
        }
        return agregarOK;

    }

}
