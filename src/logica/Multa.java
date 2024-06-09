package logica;

import interfaces.CalculoMulta;

public class Multa {

    private CalculoMulta strategy;

    Multa() {
    }

    public double calcularMulta(Estadia e) {
        if (strategy != null) {
            return strategy.calcularmulta(e);

        }
        return 0;
    }
}
