/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import java.util.ArrayList;
import logica.Anomalia;
import logica.Cochera;
import logica.Estadia;
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
    
    public int getTotalEstadias(){
        int ret = 0;
        for(Parking p: parkings){
            ret += p.getTotalEstadias();
        }
        return ret;
    }

    public double getTotalFacturado() {
        int ret = 0;
        for(Parking p: parkings){
            for(Cochera c: p.getCocheras()){
                ret += c.getFacturaEstadias();
            }
        }
        return ret;
    }
    
    public double getSubTotalFacturado(Parking p){
        return p.getSubtotal();
    }

    public ArrayList<Anomalia> getAnomalias() {
        ArrayList<Anomalia> anomalias= new ArrayList();
        for(Parking p:parkings){
            anomalias = p.getAnomalias();
        }
        return anomalias;
    }
    
}
