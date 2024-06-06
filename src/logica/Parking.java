package logica;

import iu.EscuchadorSensor;
import java.util.Date;
import java.util.ArrayList;


public class Parking implements EscuchadorSensor{

	private double factorDeDemanda;

	private String nombre;

	private String direccion;

	private Date fechaYHora;

	private int ingresosVehiculo;

	private int egresoVehiculo;

	private ArrayList <Tarifario> tarifarios;

        private ArrayList<Cochera> cocheras;
	private Sensor sensor;

	private Fachada fachada;

    public double getFactorDeDemanda() {
        return factorDeDemanda;
    }

    public ArrayList<Tarifario> getTarifarios() {
        return tarifarios;
    }

    public ArrayList<Cochera> getCocheras() {
        return cocheras;
    }

	
	public Cochera getCochera(int codigo) {
		for(Cochera c: cocheras){
	  if(c.getCodigo() == codigo){
	       
              return c;
	   }
        
                }
                return null;
        }

	public double getTarifario(TipoVehiculo tipo) {
		for(Tarifario f:tarifarios){
	 if(f.getTipoVehiculo().equals(tipo)){
         return f.getValorHora();
	}
                }
                return-1;
        }

	/**
	 
	 */
	public void actualizarFactorDemanda() {
        if(fechaYHora==null){
	  fechaYHora =  new Date();
	  }
       Date horaIngres = new Date();
	  long diferenciaTiempo = fechaYHora.getTime() - horaIngres.getTime();
             double minutos = diferenciaTiempo / (1000.0* 60.0);
	   int diferenciaIngEgreso= ingresosVehiculo-egresoVehiculo;
	  
	  if( minutos!=0){
	      int capacidad=cocheras.size();
	      int ocupado=0;
	      for(Cochera c : cocheras){
	         if(!c.estaLibre()){
	             ocupado+=1;
	            }
	        }     
	  
	   int capacidadLibreCochera= capacidad-ocupado;
	    
	    if(diferenciaIngEgreso<=capacidad/10 && diferenciaIngEgreso> 0 ){
	  
	           factorDeDemanda = factorDeDemanda-0.01*minutos;
	             if(factorDeDemanda<0.025){
	                    factorDeDemanda=0.025;
	                    }
	                     }else  if(diferenciaIngEgreso>capacidad/10 ){
	                  
	                           if(capacidadLibreCochera<capacidad/33){
	                    factorDeDemanda=factorDeDemanda+0.05*minutos;
	                                 
	                                   }if(capacidadLibreCochera>=capacidad/33 && capacidadLibreCochera<=capacidad/66){ 
	                                     factorDeDemanda=factorDeDemanda+0.1*minutos;                 
	                                            }
	                                   else if(capacidadLibreCochera>capacidad/66){
	                factorDeDemanda=factorDeDemanda+0.15*minutos;   
	  }
	            if(factorDeDemanda>10){
	                    factorDeDemanda=10;
	                    }                           
	  }else if(diferenciaIngEgreso<=capacidad/10 && diferenciaIngEgreso< 0 ){
	      if(factorDeDemanda>1){
	      factorDeDemanda=1;
	      }else{
	       factorDeDemanda=factorDeDemanda-0.05*minutos;
	       if(factorDeDemanda<0.25){
	       factorDeDemanda=0.25;
	       }
	      }
	  }
	  }
	}

	
	public void agregarEstadia(String patente, String codCochera) {
         Cochera cochera;
             int codigo=Integer.parseInt(codCochera);
            cochera = cocheras.stream()
                    .filter(c -> c.getCodigo() == codigo)
                    .findFirst()
                    .orElse(null);
	  if(cochera != null){
	  if(cochera.estaLibre()){
	  Vehiculo v = fachada.getVehiculo(patente);
	  Estadia e = new Estadia(v, cochera);
	  cochera.estacionarVehiculo(e);
	  }else{
	  Estadia estadiaAnomala = getEstadiaActual(cochera);
	  Anomalia anomalia = new Anomalia("HOUDINI", new Date(),estadiaAnomala);
	  estadiaAnomala.agregarAnomalia(anomalia); 
	  }
	 
	}
        }

	
	private Vehiculo getVehiculo(String patente) {
	return fachada.getVehiculo(patente);
	}

	
	public void ingreso(String patente, String codigoCochera) {
          ingresosVehiculo  +=1;
	  actualizarFactorDemanda();
	  agregarEstadia(patente, codigoCochera);
	}

	
	public void egreso(String patente, String codigoCochera) {
            egresoVehiculo+=1;
	  actualizarFactorDemanda();
	  cerrarEstadia(patente, codigoCochera);
	}

	
	public void cerrarEstadia(String patente, String codCochera) {
             Cochera cochera;
             int codigo=Integer.parseInt(codCochera);
            cochera = cocheras.stream()
                    .filter(c -> c.getCodigo() == codigo)
                    .findFirst()
                    .orElse(null);
	  
	  if(cochera != null){
	     if(!cochera.estaLibre()){
	     Estadia e = getEstadiaActual(cochera);
	  if(e.getVehiculo().getPatente().equals(patente)){
	  e.setFechaYHoraSalida();
	  e.procesarEgreso();
	  }else{
	  Anomalia a = new Anomalia("TRANSPORTADOR1",new Date() ,e );
	  e.agregarAnomalia(a);
	  
	  Vehiculo v = fachada.getVehiculo(patente);
	  Estadia estadiaAnomalaTransporter2 = new Estadia(v, cochera);
	  Anomalia a2 = new Anomalia("TRANSPORTADOR2",new Date(),estadiaAnomalaTransporter2 );
	  }
	  }else{
	  Estadia estadiaAnomalaMistery = getEstadiaActual(cochera);
	  estadiaAnomalaMistery.setFechaYHoraEntrada();
	  estadiaAnomalaMistery.setFechaYHoraSalida();
	  Anomalia a3 = new Anomalia("MISTERY", new Date(),estadiaAnomalaMistery );
	  estadiaAnomalaMistery.agregarAnomalia(a3);
	}
          }}
	
	
	private Estadia getEstadiaActual(Cochera cochera) {
              return cochera.getEstadiaActual();
	}

}
