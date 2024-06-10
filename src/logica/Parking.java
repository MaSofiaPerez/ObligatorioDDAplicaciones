package logica;

import exceptions.NuevoValorNegativoException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import observer.Observable;

public class Parking extends Observable {

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

    public String getNombre() {
        return nombre;
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

    public int getTotalEstadias() {
        int total = 0;
        for (Cochera c : cocheras) {
            for (Estadia e : c.getEstadias()) {
                total++;
            }
        }
        return total;
    }

    public void actualizarFactorDemanda() {
        LocalDateTime ahora = LocalDateTime.now();
        double diferenciaUT = calcularDiferenciaUT(ultimaActualizacion, ahora);

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

    private double calcularDiferenciaUT(LocalDateTime inicio, LocalDateTime fin) {
        
         return ChronoUnit.SECONDS.between(inicio, fin);
    }

    public void agregarEstadia(Vehiculo vehiculo, Cochera cochera) {
        if (cochera.estaLibre()) {
            Estadia e = new Estadia(vehiculo, cochera);
            cochera.estacionarVehiculo(e);
        } else {
            Estadia estadiaAnomala = cochera.getEstadiaActual();
            Anomalia anomalia = new Anomalia("HOUDINI", new Date(), estadiaAnomala);
            if (estadiaAnomala.agregarAnomalia(anomalia)) {
                avisar(Eventos.INGRESO_ANOMALIA);
            }
        }
        actualizarFactorDemanda();

        avisar(Eventos.INGRESO_VEHICULO);

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
                if (e.agregarAnomalia(anomaliaTransportador2)) {
                    avisar(Eventos.INGRESO_ANOMALIA);
                }
            }
        } else {
            LocalDateTime fechaActual = LocalDateTime.now();
            e.setFechaYHoraEntrada(fechaActual);
            e.setFechaYHoraSalida(fechaActual);
            Anomalia anomaliaMistery = new Anomalia("MISTERY", new Date(), e);
            if (e.agregarAnomalia(anomaliaMistery)) {
                avisar(Eventos.INGRESO_ANOMALIA);

            };
        }
        actualizarFactorDemanda();
        avisar(Eventos.EGRESO_VEHICULO);

    }

    public double getSubtotal() {
        double ret = 0;
        for (Cochera c : cocheras) {
            if (c.getSubtotal() != 0) {
                ret += c.getSubtotal();

            }
        }
        return ret;
    }

    public int getCocheras(boolean estaLibre) {
        int ocupadas = 0;
        int libres = 0;
        for (Cochera c : cocheras) {
            if (c.estaLibre()) {
                libres++;
            } else {
                ocupadas++;
            }
        }
        if (estaLibre) {
            return libres;
        } else {
            return ocupadas;
        }
    }

    public Map<String, Integer> getDisponbilidadPorEtiqueta() {
        Map<String, Integer> cocherasLibresPorEtiqueta = new HashMap<>();

        // Iterar sobre cada cochera
        for (Cochera cochera : cocheras) {
            // Verificar si la cochera está disponible
            if (cochera.estaLibre()) {
                // Iterar sobre las etiquetas de la cochera
                for (Etiqueta etiqueta : cochera.getEtiquetas()) {
                    // Obtener la descripción de la etiqueta
                    String descripcionEtiqueta = etiqueta.getDescripcion();
                    // Incrementar el contador de cocheras libres para esta etiqueta
                    cocherasLibresPorEtiqueta.put(descripcionEtiqueta,
                            cocherasLibresPorEtiqueta.getOrDefault(descripcionEtiqueta, 0) + 1);
                }
            }
        }

        return cocherasLibresPorEtiqueta;

    }

    public double getSubtotalMultas() {
        int ret = 0;
        for (Cochera c : cocheras) {
            c.getSubtotalMultas();
        }
        return ret;
    }

    public ArrayList<Anomalia> getAnomalias() {
        ArrayList<Anomalia> anomalias = new ArrayList();
        for (Cochera c : cocheras) {
            if (!c.getAnomalias().isEmpty()) {
                anomalias = c.getAnomalias();
            }
        }
        return anomalias;
    }

    public void cambiarPrecioUT(Tarifario tarifarioSeleccionado, double nuevoPrecio) {
        for (Tarifario t : tarifarios) {
            if (t.getTipoVehiculo().equals(tarifarioSeleccionado.getTipoVehiculo())) {
                t.setValorHora(nuevoPrecio);
            }
        }

    }

}
