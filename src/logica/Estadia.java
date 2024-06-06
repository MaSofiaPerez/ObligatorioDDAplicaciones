package logica;
import java.util.ArrayList;
import java.util.Date;
public class Estadia {

	private Date fechaYHoraEntrada;

	private Date fechaYHoraSalida;

	private double totalFacturado;

	private Vehiculo vehiculo;
        private Cochera cochera;

	private ArrayList<Anomalia> anomalias;

        private ArrayList<Estadia> estadiasfinañizadas;
    public Estadia(Vehiculo vehiculo, Cochera cochera) {
        this.vehiculo = vehiculo;
        this.cochera = cochera;
    }

    public Date getFechaYHoraEntrada() {
        return fechaYHoraEntrada;
    }

    public Date getFechaYHoraSalida() {
        return fechaYHoraSalida;
    }

    public double getTotalFacturado() {
        return totalFacturado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Cochera getCochera() {
        return cochera;
    }

    public ArrayList<Anomalia> getAnomalias() {
        return anomalias;
    }

	
	public void procesarEgreso() {
         TipoVehiculo  tipoVehiculo = vehiculo.getTipoVehiculo();
	  Parking p = cochera.getParking();
	  ArrayList<Tarifario> tarifarios = p.getTarifarios();
	  double tarifario = 0;
	  for(Tarifario t: tarifarios){
	  if(t.getTipoVehiculo()== tipoVehiculo){
	  tarifario = t.getValorHora();
	  }
	}
          double multas=calcularMulta();
          long tiempoestadiaMinutos=CalcularTiempoEstadiaEnMinutos();
	  double montoFacturado= calcularTotalFacturado(tiempoestadiaMinutos,tarifario,multas);
	  this.totalFacturado = montoFacturado;
	  cochera.setEstadiaActual(null);
	  debitarCuentaCorrientePropietario(montoFacturado);
        }
	
	public void setFechaYHoraEntrada() {
         fechaYHoraEntrada = new Date();
	}

	
	public double calcularMulta() {
		 double valorMulta;
            ArrayList<Double> totalDelasMultas=null;     
	  ArrayList<Etiqueta> listaEtiquetasMula = null;
	  if(cochera.getEtiquetas() != null && vehiculo.getEtiquetas() != null){
	 
	  for (Etiqueta eVehiculo: vehiculo.getEtiquetas()){
	  if(!cochera.tieneEtiqueta(eVehiculo.getDescripcion())){
	  listaEtiquetasMula.add(eVehiculo);
	  }            
	}
         
           
          for(Etiqueta m:listaEtiquetasMula){
              switch (m.getDescripcion()) {
                  case "discapacitados":
                       TipoDeMultaDiscapacitado multaDiscapacitado = null; 
                     Estadia estadia1=cochera.getEstadiaActual();
                     totalDelasMultas.add(multaDiscapacitado.calcularmulta(estadia1));
                      break;
                case "electricos":
                     TipoDeMultaElectrico multaElectrico = null; 
                    Estadia estadia2=cochera.getEstadiaActual();
                     totalDelasMultas.add(multaElectrico.calcularmulta(estadia2));
                      break;
                   case "empleadosInternos":
                     TipoDeMultaEmpleadosInterno multaEmpleadosInterno = null; 
                     Estadia estadia3=cochera.getEstadiaActual();
                     totalDelasMultas.add(multaEmpleadosInterno.calcularmulta(estadia3));
                      break;    
              }
          }
       double totaldeMultas=0;
       for(Double numero:totalDelasMultas){
       totaldeMultas+=numero;
       }
       return totaldeMultas;
          }
        return -1;
        }
	private long CalcularTiempoEstadiaEnMinutos() {
		long diferenciaTiempo = fechaYHoraSalida.getTime() - fechaYHoraEntrada.getTime();
	  
	  return diferenciaTiempo / (60*1000);
	}

	/**
	 * vehiculo.getPropietario().pagarEstadia(monto);
	 */
	private void debitarCuentaCorrientePropietario(double montoFacturado) {
          vehiculo.getPropietario().pagarEstadia(montoFacturado);
	}

	/**
	 * (PB*UT*FD)+M
	 * (tarifario*UT*factorDemanda)+multa
	 */
	private double calcularTotalFacturado(long tiempoEstadia,double tarifa,double multas) {
		return tarifa*tiempoEstadia*cochera.getParking().getFactorDeDemanda()+multas;
	}

public double calcularSubTotalFacturado() {
double tiempo=	CalcularTiempoEstadiaEnMinutos();
double tarifa=cochera.getParking().getTarifario(vehiculo.getTipoVehiculo());
    return tarifa*tiempo*cochera.getParking().getFactorDeDemanda();
	}
	public void setFechaYHoraSalida() {
           fechaYHoraSalida = new Date();
	}

	
        
	public void agregarAnomalia(Anomalia anomalia) {
           anomalias.add(anomalia);
	}

}
