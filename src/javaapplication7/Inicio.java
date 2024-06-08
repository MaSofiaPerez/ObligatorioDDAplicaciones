/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication7;


import simuladortransito.SimuladorTransito;
import iu.DatosPrueba;
import iu.TableroControl;
/**
 *
 * @author sofia
 */
public class Inicio {
    

    public static void main(String[] args) {
        DatosPrueba.iniciar();
        new TableroControl().setVisible(true);
    }
    
}
