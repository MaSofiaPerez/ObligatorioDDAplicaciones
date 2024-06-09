package logica;
import interfaces.CalculoMulta;

public class Multa {
    private CalculoMulta strategy;
    
    Multa(){}
    
    public double calcularMulta(Estadia e){
       return strategy.calcularmulta(e);
    }
}
