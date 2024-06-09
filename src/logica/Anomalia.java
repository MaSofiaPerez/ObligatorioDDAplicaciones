package logica;

import java.util.Date;

public class Anomalia {

    private String codigoError;

    private Date fechaYHora;
    
    private Estadia estadia;

    public Anomalia(String codigoError, Date fechaYHora, Estadia estadia) {
        this.codigoError = codigoError;
        this.fechaYHora = fechaYHora;
        this.estadia = estadia;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public Date getFechaYHora() {
        return fechaYHora;
    }

    public Estadia getEstadia() {
        return estadia;
    }

}
