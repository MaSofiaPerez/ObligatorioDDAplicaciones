/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import java.util.ArrayList;
import logica.Propietario;

/**
 *
 * @author sofia
 */
public class ControladorPropietario {
    ArrayList<Propietario> propietarios;
    
    public ControladorPropietario(){
        this.propietarios = new ArrayList();
    }
    
    public ArrayList<Propietario> getPropietarios(){
        return propietarios;
    }
    
    public void agregar(Propietario p){
        propietarios.add(p);
    }
    
}
