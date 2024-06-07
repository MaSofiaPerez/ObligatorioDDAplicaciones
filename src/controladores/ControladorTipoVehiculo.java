/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import java.util.ArrayList;
import logica.TipoVehiculo;

/**
 *
 * @author sofia
 */
public class ControladorTipoVehiculo {
    private ArrayList<TipoVehiculo> tiposVehiculos;
    
    public ControladorTipoVehiculo(){
        this.tiposVehiculos = new ArrayList();
    }
    public ArrayList<TipoVehiculo> getTiposVehiculos(){
        return tiposVehiculos;
    }
    
    public void agregar(TipoVehiculo tipo){
        tiposVehiculos.add(tipo);
    }
}
