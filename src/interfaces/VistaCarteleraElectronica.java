/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.util.ArrayList;
import java.util.Map;
import logica.Cochera;
import logica.Tarifario;

/**
 *
 * @author sofia
 */
public interface VistaCarteleraElectronica {
    

    void mostrarNombreParking(String nombre);

    void actualizarTablaDisponibilidad( Map<String, Integer> disponibilidad);

    void cargarTablaTarifario(ArrayList<Tarifario> tarifarios);

    void mostrarCantCocherasDisponibles(int cocheras);

}
