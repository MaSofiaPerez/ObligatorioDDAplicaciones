package logica;

import interfaces.CalculoMulta;

public class MultaDiscapacitado implements CalculoMulta {

    private static final double costoDiscapacitados = 250;
    
    public double calcularmulta(Estadia estadia) {
        if (estadia.getCochera().esDiscapacitado() && !estadia.getVehiculo().esDiscapacitado()) {
            return costoDiscapacitados;
        }
        return 0;
    }

}
