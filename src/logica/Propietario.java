package logica;

public class Propietario {

	private String cedula;

	private String nombreCompleto;

	private CuentaCorriente cuentaCorriente;

    public Propietario(String cedula, String nombreCompleto, CuentaCorriente cuentaCorriente) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.cuentaCorriente = cuentaCorriente;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }


	
        
	public void pagarEstadia(double monto) {
          cuentaCorriente.restar(monto);
	}

}
