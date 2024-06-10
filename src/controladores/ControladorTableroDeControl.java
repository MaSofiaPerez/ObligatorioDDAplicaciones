/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import interfaces.VistaTableroDeControl;
import java.util.ArrayList;
import logica.Estadia;
import logica.Fachada;
import logica.Parking;
import observer.Observable;
import observer.Observador;
import logica.Eventos;

/**
 *
 * @author sofia
 */
public class ControladorTableroDeControl implements Observador {

    private final VistaTableroDeControl vista;
    private final Fachada modelo;
    private ArrayList<Parking> parkings;
    private boolean monitorearAnomalias;

    public ControladorTableroDeControl(VistaTableroDeControl vista) {
        this.vista = vista;
        this.modelo = Fachada.getInstancia();
        this.parkings = modelo.getParkings();
        this.monitorearAnomalias = false;
        for (Parking p : parkings) {
            p.agregar(this);
        }
        inicializarVista();

    }

    private void inicializarVista() {
        vista.mostrarTotalEstadias(modelo.getTotalEstadias());
        vista.mostrarTotalFacturado(modelo.getTotalFacturadoParkings());
        vista.actualizarTablaParkings(modelo.getParkings());
        if (monitorearAnomalias) {
            vista.actualizarTablaAnomalias(modelo.getAnomalias());

        }

    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        if (Eventos.INGRESO_VEHICULO.equals(evento) || Eventos.EGRESO_VEHICULO.equals(evento)) {
            inicializarVista();
        }
        if (monitorearAnomalias && Eventos.INGRESO_ANOMALIA.equals(evento)) {
            vista.actualizarTablaAnomalias(modelo.getAnomalias());
        }
    }

    public void iniciarMonitoreoAnomalias() {
        monitorearAnomalias = true;
    }

    public void detenerMonitoreoAnomalias() {
        monitorearAnomalias = false;
    }

    public Parking getParkingSeleccionado(String parking) {
        for (Parking p : parkings) {
            if (p.getNombre().equals(parking)) {
                return p;
            }
        }
        return null;
    }

}
