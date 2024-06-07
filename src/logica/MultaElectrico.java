package logica;

import interfaces.CalculoMulta;

public class MultaElectrico implements CalculoMulta {

    public double calcularmulta(Estadia estadia) {
        if(estadia.getCochera().esElectrico() && !estadia.getVehiculo().esElectrico()){
            return estadia.calcularSubTotalFacturado() / 50;   
        }
        return 0;
    }

}
