/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import java.util.ArrayList;
import logica.Parking;

/**
 *
 * @author sofia
 */
public class ControladorParking {
    private ArrayList<Parking> parkings;
    
    public ControladorParking(){
        this.parkings = new ArrayList();
    }

    public ArrayList<Parking> getParkings() {
        return parkings;
    }
    
    public void agregar(Parking p){
        parkings.add(p);
    }
    
    
}
