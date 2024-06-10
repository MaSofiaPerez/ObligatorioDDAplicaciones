/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author sofia
 */
public class NuevoValorDemasiadoAltoException extends Exception{

    public NuevoValorDemasiadoAltoException(double doblePromedio) {
        super("Valor demasiado alto. El sistema no permite dispersión de precios por encima del 100%. Ingrese un valor menor a " + doblePromedio);
    } 

    
}
