package logica;
import interfaces.CalculoMulta;

public class Multa {
    private CalculoMulta strategy;
    
    public double calcularMulta(Estadia e){
       return strategy.calcularmulta(e);
    }
}
