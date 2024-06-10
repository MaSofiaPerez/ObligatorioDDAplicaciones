/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;


import exceptions.NuevoValorDemasiadoAltoException;
import exceptions.NuevoValorNegativoException;
import interfaces.VistaListaDePrecios;
import java.util.ArrayList;
import logica.Fachada;
import logica.Parking;
import logica.Tarifario;


/**
 *
 * @author sofia
 */
public class ControladorListaDePrecios{

    private Parking parking;
    private final Fachada modelo;
    private final VistaListaDePrecios vista;

    public ControladorListaDePrecios(VistaListaDePrecios vista, Parking p) {
        this.vista = vista;
        this.modelo = Fachada.getInstancia();
        this.parking = p;
        inicializarVista();

    }

    private void inicializarVista() {
       vista.mostrarNombreParking(parking.getNombre());
       vista.actualizarTablaTarifario(parking.getTarifarios());
    }

    public Tarifario getTarifarioSeleccionado(String tarifario) {
        ArrayList<Tarifario> tarifarios = parking.getTarifarios();
        for (Tarifario t : tarifarios) {
            if (t.getTipoVehiculo().getTipo().equals(tarifario)) {
                return t;
            }
        }
        return null;
    }

    public void guardarCambioDePrecioUT(Tarifario tarifarioSeleccionado, double nuevoPrecio) {
        try {
            modelo.cambiarPrecioUT(tarifarioSeleccionado, nuevoPrecio, parking);
            inicializarVista();
        } catch (NuevoValorDemasiadoAltoException | NuevoValorNegativoException ex) {
            vista.mostrarMensajeDeError(ex.getMessage());
        }
    }

   
    

}
