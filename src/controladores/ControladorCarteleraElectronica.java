/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import interfaces.VistaCarteleraElectronica;
import logica.Eventos;
import logica.Parking;
import observer.Observable;
import observer.Observador;

/**
 *
 * @author sofia
 */
public class ControladorCarteleraElectronica implements Observador {
    private VistaCarteleraElectronica vista;
    private Parking modelo;
    
    public ControladorCarteleraElectronica(VistaCarteleraElectronica v, Parking p){
        this.vista = v;
        this.modelo = p;
        modelo.agregar(this);
        inicializarVista();
    }
            

    
    @Override
    public void actualizar(Observable origen, Object evento) {
        if(Eventos.INGRESO_VEHICULO.equals(evento) || Eventos.EGRESO_VEHICULO.equals(evento)){
            inicializarVista();
        }
    }

    private void inicializarVista() {
        vista.mostrarNombreParking(modelo.getNombre());
        vista.mostrarCantCocherasDisponibles(modelo.getCocheras(true));
        vista.actualizarTablaDisponibilidad(modelo.getDisponbilidadPorEtiqueta());
        vista.cargarTablaTarifario(modelo.getTarifarios());
    }
    
}
