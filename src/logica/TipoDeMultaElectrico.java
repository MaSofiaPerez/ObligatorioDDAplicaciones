package logica;

import iu.CalculoMula;


public class  TipoDeMultaElectrico implements CalculoMula {

	public double calcularmulta(Estadia estadia) {
		return estadia.calcularSubTotalFacturado()/50;
	}

}
