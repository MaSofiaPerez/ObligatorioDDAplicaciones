package logica;

import interfaces.CalculoMulta;


public class  TipoDeMultaElectrico implements CalculoMulta {

	public double calcularmulta(Estadia estadia) {
		return estadia.calcularSubTotalFacturado()/50;
	}

}
