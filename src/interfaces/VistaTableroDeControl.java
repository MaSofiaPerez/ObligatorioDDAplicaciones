/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.time.LocalDateTime;
import java.util.ArrayList;
import logica.Anomalia;
import logica.Parking;

/**
 *
 * @author sofia
 */
public interface VistaTableroDeControl {

    void abrirCartelera();

    void mostrarTotalEstadias(int total);

    void mostrarTotalFacturado(double total);

    void actualizarTablaParkings(ArrayList<Parking> parkings);
    
    void actualizarTablaAnomalias(ArrayList<Anomalia> anomalias);

    void cerrarTablero();

    void abrirListaPrecios();

    void verAnomalias();


}
