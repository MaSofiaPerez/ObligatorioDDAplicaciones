/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import logica.Cochera;
import logica.CuentaCorriente;
import logica.Etiqueta;
import logica.Fachada;
import logica.Parking;
import logica.Propietario;
import logica.SensorParking;
import logica.Tarifario;
import logica.TipoVehiculo;
import logica.Vehiculo;
import simuladortransito.Estacionable;
import simuladortransito.FlujoEgreso;
import simuladortransito.FlujoIngreso;
import simuladortransito.Modo;
import simuladortransito.PerfilIngreso;
import simuladortransito.Periodo;
import simuladortransito.SimuladorTransito;
import simuladortransito.Transitable;

/**
 *
 * @author sofia
 */
public class DatosPrueba {

    private static SimuladorTransito simulador;

    public static void iniciar() {

        Fachada fachada = Fachada.getInstancia();

        Parking parking1 = new Parking("Parking 1", "Av Italia 3342");
        Parking parking2 = new Parking("Parking 2", "Canelones 1236");
        fachada.agregar(parking1);
        fachada.agregar(parking2);

        ArrayList<Propietario> propietarios = generarPropietarioAleatorio();
        for (Propietario p : propietarios) {
            fachada.agregar(p);
        }

        ArrayList<Etiqueta> etiquetas = new ArrayList();
        Etiqueta discapacitado = new Etiqueta("Discapacitado");
        Etiqueta electrico = new Etiqueta("Electrico");
        Etiqueta empleado = new Etiqueta("Empleado");
        etiquetas.add(discapacitado);
        etiquetas.add(electrico);
        etiquetas.add(empleado);

        ArrayList<Cochera> cocherasP1 = generarCocheraAleatoria(etiquetas, 80);
        ArrayList<Cochera> cocherasP2 = generarCocheraAleatoria(etiquetas, 80);

       // verificarEtiquetasCocheras(cocherasP1);

        parking1.setCocheras(cocherasP1);
        parking2.setCocheras(cocherasP2);

        ArrayList<TipoVehiculo> tiposVehiculos = new ArrayList();
        TipoVehiculo motocicleta = new TipoVehiculo("Motocicleta");
        TipoVehiculo standard = new TipoVehiculo("Standard");
        TipoVehiculo carga = new TipoVehiculo("Carga");
        TipoVehiculo pasajero = new TipoVehiculo("Pasajero");
        tiposVehiculos.add(motocicleta);
        tiposVehiculos.add(standard);
        tiposVehiculos.add(carga);
        tiposVehiculos.add(pasajero);
        for (TipoVehiculo tipo : tiposVehiculos) {
            fachada.agregar(tipo);
        }

        ArrayList<Vehiculo> vehiculos = generarVehiculosAleatorios(propietarios, etiquetas, tiposVehiculos, 320);
        for (Vehiculo v : vehiculos) {
            fachada.agregar(v);
        }

        ArrayList<Tarifario> tarifarios = new ArrayList();
        Tarifario tarifarioMotocicletas = new Tarifario(motocicleta, 0.05);
        Tarifario tarifarioStandard = new Tarifario(standard, 0.1);
        Tarifario tarifarioCarga = new Tarifario(carga, 0.1);
        Tarifario tarifarioPasajero = new Tarifario(pasajero, 0.1);
        tarifarios.add(tarifarioMotocicletas);
        tarifarios.add(tarifarioStandard);
        tarifarios.add(tarifarioCarga);
        tarifarios.add(tarifarioPasajero);

        parking1.setTarifarios(tarifarios);
        parking2.setTarifarios(tarifarios);

        //1.Configurar simulador
        ArrayList<Transitable> vehiculosTransitables = generarVehiculos(100);
        ArrayList<Estacionable> cocheras = generarCocheras(50);

        simulador = SimuladorTransito.getInstancia();
        simulador.addTransitables(vehiculosTransitables);
        simulador.addEstacionables(cocheras);
        //2. Programarlo
        try {
            FlujoIngreso flujo = new FlujoIngreso("Ingreso matutino", new Periodo(0, 5), 10);
            simulador.programar(flujo);
            FlujoIngreso flujo2 = new FlujoIngreso("Entrada Partido", new Periodo(0, 6), 20);
            simulador.programar(flujo2);
            PerfilIngreso perfilAnomalo = new PerfilIngreso.Builder().invadirEstacionableDiscapacitado(Modo.ALEATORIO)
                    .invadirEstacionableElectrico(Modo.NUNCA)
                    .invadirEstacionableEmpleadoInterno(Modo.SIEMPRE)
                    .ocuparEstacionableOcupado(Modo.ALEATORIO)
                    .build();
            FlujoIngreso flujoAnomalo = new FlujoIngreso("Ingeso con anomalias", new Periodo(0, 10), 10, perfilAnomalo);
            simulador.programar(flujoAnomalo);
            /*
            FlujoEgreso flujoegreso = new FlujoEgreso("Salida de clases", new Periodo(4, 2), 30);
            simulador.programar(flujoegreso);
            FlujoIngreso flujo3 = new FlujoIngreso("Ingreso Trabajo", new Periodo(3, 3), 50);
            simulador.programar(flujo3);
            FlujoEgreso flujoegreso2 = new FlujoEgreso("Salida de Estadio", new Periodo(8, 3), 80);
            simulador.programar(flujoegreso2);*/
            FlujoEgreso flujoegreso3 = new FlujoEgreso("Salida de Trabajo", new Periodo(3, 10), 40);
           simulador.programar(flujoegreso3);
           
           //TODO: revisar el flujoAnomalo - generacion de cocheras
           //TODO: revisar egresos - no muestra los subtotales ni las multas

            //3. Ejecutarlo
            simulador.iniciar(new SensorParking(parking1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Transitable> generarVehiculos(int cantidad) {
        Fachada fachada = Fachada.getInstancia();
        ArrayList<Vehiculo> vehiculos = fachada.getVehiculos();
        ArrayList<Transitable> ret = new ArrayList<>();
        for (int i = 0; i < cantidad && i < vehiculos.size(); i++) {
            ret.add(vehiculos.get(i));
        }
        for (Transitable r : ret) {
            vehiculos.add((Vehiculo) r);
        }

        return ret;
    }

    /*private static void verificarEtiquetasCocheras(ArrayList<Cochera> cocheras) {
        for (Cochera cochera : cocheras) {
            System.out.println("Cochera: " + cochera.getCodigo());
            for (Etiqueta etiqueta : cochera.getEtiquetas()) {
                System.out.println("  Etiqueta: " + etiqueta.getDescripcion());
            }
        }
    }*/

    private static ArrayList<Estacionable> generarCocheras(int cantidad) {
        Fachada fachada = Fachada.getInstancia();
        ArrayList<Estacionable> ret = new ArrayList<>();
        for (Parking p : fachada.getParkings()) {
            for (Cochera c : p.getCocheras()) {
                if (cantidad > 0) {
                    ret.add(c);
                    cantidad--;
                } else {
                    break;
                }
            }
        }
        return ret;
    }

    private static ArrayList<Propietario> generarPropietarioAleatorio() {
        ArrayList<Propietario> propietarios = new ArrayList();
        for (int i = 0; i < 70; i++) {
            String nombre = "Propietario" + (i + 1);
            int cedula = new Random().nextInt(10000000);
            double saldo = new Random().nextDouble() * 110 - 10;
            CuentaCorriente cc = new CuentaCorriente(saldo);
            Propietario propietario = new Propietario(cedula, nombre, cc);
            propietarios.add(propietario);
        }
        return propietarios;
    }

   private static ArrayList<Cochera> generarCocheraAleatoria(ArrayList<Etiqueta> etiquetas, int cantidad) {
    ArrayList<Cochera> cocheras = new ArrayList<>();
    Random random = new Random();
    Set<String> codigosUsados = new HashSet<>();

    // Asegurar que todas las etiquetas se usen al menos una vez
    for (Etiqueta etiqueta : etiquetas) {
        String codigoCochera;
        do {
            codigoCochera = "C" + (random.nextInt(100) + 1);
        } while (codigosUsados.contains(codigoCochera));
        
        codigosUsados.add(codigoCochera);
        Cochera cochera = new Cochera(codigoCochera);
        cochera.addEtiqueta(etiqueta);
        cocheras.add(cochera);
    }

    // Calcular el número de cocheras que deben tener al menos una etiqueta (20%)
    int cocherasConEtiqueta = (int) Math.ceil(cantidad * 0.2);

    // Asignar etiquetas aleatoriamente al resto del 20% de las cocheras
    while (cocheras.size() < cocherasConEtiqueta) {
        String codigoCochera;
        do {
            codigoCochera = "C" + (random.nextInt(100) + 1);
        } while (codigosUsados.contains(codigoCochera));

        codigosUsados.add(codigoCochera);
        Cochera cochera = new Cochera(codigoCochera);

        // Asignar una o dos etiquetas de forma aleatoria
        int numeroEtiquetas = random.nextInt(2) + 1; // 1 o 2 etiquetas
        for (int i = 0; i < numeroEtiquetas; i++) {
            Etiqueta etiqueta = etiquetas.get(random.nextInt(etiquetas.size()));
            if (!cochera.getEtiquetas().contains(etiqueta)) {
                cochera.addEtiqueta(etiqueta);
            }
        }
        cocheras.add(cochera);
    }

    // Rellenar el resto de las cocheras sin etiquetas
    while (cocheras.size() < cantidad) {
        String codigoCochera;
        do {
            codigoCochera = "C" + (random.nextInt(100) + 1);
        } while (codigosUsados.contains(codigoCochera));

        codigosUsados.add(codigoCochera);
        Cochera cochera = new Cochera(codigoCochera);
        cocheras.add(cochera);
    }

    return cocheras;
}


    private static ArrayList<Vehiculo> generarVehiculosAleatorios(ArrayList<Propietario> propietarios, ArrayList<Etiqueta> etiquetas, ArrayList<TipoVehiculo> tiposVehiculos, int cantidad) {
        Random random = new Random();
        ArrayList<Vehiculo> vehiculos = new ArrayList();
        for (int i = 0; i < cantidad; i++) {
            Propietario propietario = propietarios.get(random.nextInt(propietarios.size()));
            TipoVehiculo tipoVehiculo = tiposVehiculos.get(random.nextInt(tiposVehiculos.size()));
            String patente = generarPatenteAleatoria();
            Vehiculo vehiculo = new Vehiculo(patente, tipoVehiculo, propietario);
            for (Etiqueta e : etiquetas) {
                int probabilidad = random.nextInt(10);
                if (probabilidad < 2 && vehiculo.getEtiquetas().size() < 2) {
                    vehiculo.addEtiqueta(e);
                }
            }
            vehiculos.add(vehiculo);

        }
        return vehiculos;
    }

    private static String generarPatenteAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder patente = new StringBuilder();
        Random random = new Random();
        // Generar una patente de 7 caracteres
        for (int i = 0; i < 7; i++) {
            // Seleccionar un carácter aleatorio del conjunto de caracteres
            char caracter = caracteres.charAt(random.nextInt(caracteres.length()));
            patente.append(caracter);
        }
        return patente.toString();
    }

}
