package logica;

public class CuentaCorriente {

    private double saldo;

    public CuentaCorriente(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void restar(double montoEstadia) {
        saldo -= montoEstadia;
    }

}
