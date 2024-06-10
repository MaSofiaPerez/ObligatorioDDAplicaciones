package logica;

import interfaces.CalculoMulta;

public class MultaElectrico implements CalculoMulta {

    public MultaElectrico() {

    }

    @Override
    public double calcularmulta(Estadia estadia) {
        return estadia.calcularSubTotalFacturado() / 50;
    }
}
   