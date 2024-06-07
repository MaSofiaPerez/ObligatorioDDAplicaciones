package logica;

import interfaces.EscuchadorSensor;
import java.util.Date;
import java.util.ArrayList;

public class Parking {

    //TODO: agregar estado de tendencia
    //TODO: revisar el calculo de factor de estaria, manejar UT
    private double factorDeDemanda = 1;

    private String nombre;

    private String direccion;

    private Date ultimaActualizacion;

    private int ingresosVehiculo;

    private int egresosVehiculo;

    private ArrayList<Tarifario> tarifarios;

    private ArrayList<Cochera> cocheras;

    public Parking(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cocheras = new ArrayList();
        this.tarifarios = new ArrayList();
    }

    public double getFactorDeDemanda() {
        return factorDeDemanda;
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
    
    public void setCocheras(ArrayList<Cochera> cocheras){
        this.cocheras = cocheras;
        for(Cochera c: cocheras){
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

    private double calcularTiempoDesdeUltimaActualizacion() {
        if (ultimaActualizacion == null) {
            return 0;
        } else {
            Date fechaActual = new Date();
            long difTiempo = fechaActual.getTime() - ultimaActualizacion.getTime();
            return difTiempo / (1000 * 60);
        }
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

        double minutos = calcularTiempoDesdeUltimaActualizacion();

        if (minutos == 10) {
            int difIngresoEgreso = ingresosVehiculo - egresosVehiculo;
            int capacidad = cocheras.size();
            int ocupado = obtenerCocherasOcupadas();
            int ocupacionRelativa = capacidad / ocupado;

            if (difIngresoEgreso <= capacidad * 0.1) {
                factorDeDemanda -= 0.01 * minutos;
                if (factorDeDemanda < 0.025) {
                    factorDeDemanda = 0.025;
                }
            } else if (difIngresoEgreso > 0 && difIngresoEgreso > capacidad / 10) {
                if (ocupacionRelativa < capacidad * 0.33) {
                    factorDeDemanda += 0.05 * minutos;
                } else if (ocupacionRelativa >= capacidad * 0.33 && ocupacionRelativa <= capacidad * 0.66) {
                    factorDeDemanda += 0.1 * minutos;
                } else if (ocupacionRelativa > capacidad * 0.66) {
                    factorDeDemanda += 0.15 * minutos;
                }
                if (factorDeDemanda > 10) {
                    factorDeDemanda = 10;
                }
            } else if (difIngresoEgreso < 0 && difIngresoEgreso <= capacidad * 0.1) {
                if (factorDeDemanda > 1) {
                    factorDeDemanda = 1;
                } else {
                    factorDeDemanda -= 0.05 * minutos;
                    if (factorDeDemanda < 0.25) {
                        factorDeDemanda = 0.25;
                    }
                }
            }
        }
        ultimaActualizacion = new Date();
        ingresosVehiculo = 0;
        egresosVehiculo = 0;
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
        ingresosVehiculo += 1;
        actualizarFactorDemanda();
        agregarEstadia(vehiculo, cochera);
    }

    public void egreso(Vehiculo vehiculo, Cochera cochera) {
        egresosVehiculo += 1;
        actualizarFactorDemanda();
        cerrarEstadia(vehiculo, cochera);
    }

    public void cerrarEstadia(Vehiculo vehiculo, Cochera cochera) {
        Estadia e = cochera.getEstadiaActual();
        if (!cochera.estaLibre()) {
            if (e.getVehiculo().getPatente().equals(vehiculo.getPatente())) {
                e.setFechaYHoraSalida(new Date());
                e.procesarEgreso();
            } else {
                Anomalia anomaliaTransportador1 = new Anomalia("TRANSPORTADOR1", new Date(), e);
                e.agregarAnomalia(anomaliaTransportador1);
                e.setVehiculo(vehiculo);
                Anomalia anomaliaTransportador2 = new Anomalia("TRANSPORTADOR2", new Date(), e);
                e.agregarAnomalia(anomaliaTransportador2);
            }
        } else {
            Date fechaActual = new Date();
            e.setFechaYHoraEntrada(fechaActual);
            e.setFechaYHoraSalida(fechaActual);
            Anomalia anomaliaMistery = new Anomalia("MISTERY", new Date(), e);
            e.agregarAnomalia(anomaliaMistery);
        }
    }
}
