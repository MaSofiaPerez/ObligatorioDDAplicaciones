package logica;

import interfaces.CalculoMulta;

public class MultaDiscapacitado implements CalculoMulta {

    private static final double costoDiscapacitados = 250;

    public MultaDiscapacitado() {

    }

    public double calcularmulta(Estadia estadia) {
        return costoDiscapacitados;

    }
}
