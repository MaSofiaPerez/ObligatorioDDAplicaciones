package logica;

import interfaces.CalculoMulta;

public class MultaEmpleadoInterno implements CalculoMulta {

    private static final double costoPor10UT = 1;

    public MultaEmpleadoInterno() {
    }

    @Override
    public double calcularmulta(Estadia estadia) {
        double tiempo = estadia.CalcularTiempoEstadia();
        return (tiempo / 10) * costoPor10UT;

    }

}
