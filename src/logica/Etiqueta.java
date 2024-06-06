package logica;

public class Etiqueta {

	/**
	 * discapacitado
	 * electrico
	 * empleado
	 */
	private String descripcion;

    public Etiqueta(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public boolean validarEtiqueta(String etiqueta){
        boolean validar=false;
        validar = switch (etiqueta) {
                case "discapacitado" ->validar= true;
                case "electrico" ->validar= true;
                case "empleado" ->validar= true;
                default -> false;
            };
    return  validar;
    
    }

}
