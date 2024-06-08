package logica;

import interfaces.EscuchadorSensor;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ArrayList;

public class Parking {

    //TODO: agregar estado de tendencia
    //TODO: revisar el calculo de factor de estaria, manejar UT
    private double factorDeDemanda;

    private String nombre;

    private String direccion;

    private LocalDateTime ultimaActualizacion;

    private static final String TENDENCIA_ESTABLE = "Estable";

    private static final String TENDENCIA_POSITIVA = "Positiva";

    private static final String TENDENCIA_NEGATIVA = "Negativa";

    private String estadoTendencia;

    private ArrayList<Tarifario> tarifarios;

    private ArrayList<Cochera> cocheras;

    public Parking(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.factorDeDemanda = 1;
        this.estadoTendencia = TENDENCIA_ESTABLE;
        this.cocheras = new ArrayList();
        this.tarifarios = new ArrayList();
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public double getFactorDeDemanda() {
        return factorDeDemanda;
    }

    public String getEstadoTendencia() {
        return estadoTendencia;
    }

    public ArrayList<Tarifario> getTarifarios() {
        return tarifarios;
    }

    public void setTarifarios(ArrayList<Tarifario> tarifarios) {
        this.tarifarios = tarifarios;
    }

    public ArrayList<Cochera> getCocheras() {
        return cocheras;
    }

    public void setCocheras(ArrayList<Cochera> cocheras) {
        this.cocheras = cocheras;
        for (Cochera c : cocheras) {
            c.setParking(this);
        }
    }

    public double getTarifario(TipoVehiculo tipo) {
        for (Tarifario f : tarifarios) {
            if (f.getTipoVehiculo().equals(tipo)) {
                return f.getValorHora();
            }
        }
        return -1;
    }

    private int obtenerCocherasOcupadas() {
        int ocupado = 0;
        for (Cochera c : cocheras) {
            if (!c.estaLibre()) {
                ocupado += 1;
            }
        }
        return ocupado;
    }

    public void actualizarFactorDemanda() {
        LocalDateTime ahora = LocalDateTime.now();
        int diferenciaUT = calcularDiferenciaUT(ultimaActualizacion, ahora);

        if (diferenciaUT >= 10) {
            int ingresos = 0;
            int egresos = 0;
            int capacidad = cocheras.size();

            for (Cochera c : cocheras) {
                ingresos += c.getEstadias().stream()
                        .filter(e -> e.getFechaYHoraEntrada().isAfter(ultimaActualizacion.minus(10, ChronoUnit.MINUTES)))
                        .count();
                egresos += c.getEstadias().stream()
                        .filter(e -> e.getFechaYHoraSalida() != null && e.getFechaYHoraSalida().isAfter(ultimaActualizacion.minus(10, ChronoUnit.MINUTES)))
                        .count();

            }

            double diferencia = ingresos - egresos;
            double ocupacion = (double) cocheras.stream().filter(Cochera::estaLibre).count() / capacidad;

            if (Math.abs(diferencia) <= 0.1 * capacidad) {
                estadoTendencia = TENDENCIA_ESTABLE;
                factorDeDemanda = Math.max(factorDeDemanda - 0.01 * diferenciaUT, 0.25);
            } else if (diferencia > 0.1 * capacidad) {
                estadoTendencia = TENDENCIA_POSITIVA;
                if (ocupacion < 0.33) {
                    factorDeDemanda = Math.min(factorDeDemanda + 0.05 * diferenciaUT, 10);
                } else if (ocupacion < 0.66) {
                    factorDeDemanda = Math.min(factorDeDemanda + 0.1 * diferenciaUT, 10);
                } else {
                    factorDeDemanda = Math.min(factorDeDemanda + 0.15 * diferenciaUT, 10);
                }
            } else if (diferencia < -0.1 * capacidad) {
                estadoTendencia = TENDENCIA_NEGATIVA;
                if (factorDeDemanda > 1) {
                    factorDeDemanda = 1;
                } else {
                    factorDeDemanda = Math.max(factorDeDemanda - 0.05 * diferenciaUT, 0.025);
                }
            }
            ultimaActualizacion = ahora;
        }

    }
    
    private int calcularDiferenciaUT(LocalDateTime inicio, LocalDateTime fin){
        return (int) ChronoUnit.MINUTES.between(inicio, fin);
    }

    public void agregarEstadia(Vehiculo vehiculo, Cochera cochera) {
        if (cochera.estaLibre()) {
            Estadia e = new Estadia(vehiculo, cochera);
            cochera.estacionarVehiculo(e);
        } else {
            Estadia estadiaAnomala = cochera.getEstadiaActual();
            Anomalia anomalia = new Anomalia("HOUDINI", new Date(), estadiaAnomala);
            estadiaAnomala.agregarAnomalia(anomalia);
        }
    }

    public void ingreso(Vehiculo vehiculo, Cochera cochera) {
        actualizarFactorDemanda();
        agregarEstadia(vehiculo, cochera);
    }

    public void egreso(Vehiculo vehiculo, Cochera cochera) {
        actualizarFactorDemanda();
        cerrarEstadia(vehiculo, cochera);
    }

    public void cerrarEstadia(Vehiculo vehiculo, Cochera cochera) {
        Estadia e = cochera.getEstadiaActual();
        if (!cochera.estaLibre()) {
            if (e.getVehiculo().getPatente().equals(vehiculo.getPatente())) {
                e.setFechaYHoraSalida(LocalDateTime.now());
                e.procesarEgreso();
            } else {
                Anomalia anomaliaTransportador1 = new Anomalia("TRANSPORTADOR1", new Date(), e);
                e.agregarAnomalia(anomaliaTransportador1);
                e.setVehiculo(vehiculo);
                Anomalia anomaliaTransportador2 = new Anomalia("TRANSPORTADOR2", new Date(), e);
                e.agregarAnomalia(anomaliaTransportador2);
            }
        } else {
            LocalDateTime fechaActual = LocalDateTime.now();
            e.setFechaYHoraEntrada(fechaActual);
            e.setFechaYHoraSalida(fechaActual);
            Anomalia anomaliaMistery = new Anomalia("MISTERY", new Date(), e);
            e.agregarAnomalia(anomaliaMistery);
        }
    }

    private int calcularDiferenciaUT(LocalDateTime ultimaActualizacion, LocalDateTime ahora) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
