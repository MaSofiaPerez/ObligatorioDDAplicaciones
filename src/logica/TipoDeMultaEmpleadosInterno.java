package logica;

import interfaces.CalculoMulta;


    public class TipoDeMultaEmpleadosInterno implements CalculoMulta {

        @Override
	public double calcularmulta(Estadia estadia) {
	 long diferenciaTiempo = estadia.getFechaYHoraEntrada().getTime() - estadia.getFechaYHoraSalida().getTime();
         double minutos = diferenciaTiempo / (1000.0* 60.0);

         
           return (minutos / 10) * 1.0;
	}

}
