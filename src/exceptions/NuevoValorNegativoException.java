/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author sofia
 */
public class NuevoValorNegativoException extends Exception {



    public NuevoValorNegativoException() {
        super("Valor inválido. El precio debe ser igual o mayor a cero.");
    }

    
}
