/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import interfaces.VistaTableroDeControl;
import java.util.ArrayList;
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
    
    public ControladorTableroDeControl(VistaTableroDeControl vista){
        this.vista = vista;
        this.modelo = Fachada.getInstancia();
        this.parkings = modelo.getParkings();
        for(Parking p: parkings){
            p.agregar(this);
        }        
        inicializarVista();
        
    }

    private void inicializarVista() {
        vista.mostrarTotalEstadias(modelo.getTotalEstadias());
        vista.mostrarTotalFacturado(modelo.getSubtotalFacturadoParkings());
        vista.actualizarTablaParkings(modelo.getParkings());
    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        if(Eventos.INGRESO_VEHICULO.equals(evento) || Eventos.EGRESO_VEHICULO.equals(evento)){
            inicializarVista();
        }
    }
    
    
}
