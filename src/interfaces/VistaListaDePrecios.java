/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.util.ArrayList;
import logica.Tarifario;

/**
 *
 * @author sofia
 */
public interface VistaListaDePrecios {

    void mostrarMensajeDeError(String message);
    void mostrarNombreParking(String nombre);
    public void actualizarTablaTarifario(ArrayList<Tarifario> tarifarios);
    
}
