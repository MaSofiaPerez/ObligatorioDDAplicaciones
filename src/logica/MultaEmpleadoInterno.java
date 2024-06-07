package logica;

import interfaces.CalculoMulta;

public class MultaEmpleadoInterno implements CalculoMulta {
    
    private static final double costoPor10UT = 1;
    
    @Override
    public double calcularmulta(Estadia estadia) {
        if (estadia.getCochera().esEmpleado() && !estadia.getVehiculo().esEmpleado()) {
            double tiempo = estadia.CalcularTiempoEstadiaEnMinutos();
            return (tiempo / 10) * costoPor10UT;
        }
        return 0;
    }

}
