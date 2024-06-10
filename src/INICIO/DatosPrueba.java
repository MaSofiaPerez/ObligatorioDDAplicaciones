/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package INICIO;

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
import logica.Tarifario;
import logica.TipoVehiculo;
import logica.Vehiculo;
import simuladortransito.Estacionable;
import simuladortransito.FlujoEgreso;
import simuladortransito.FlujoIngreso;
import simuladortransito.Modo;
import simuladortransito.PerfilEgreso;
import simuladortransito.PerfilIngreso;
import simuladortransito.Periodo;
import simuladortransito.Sensor;
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
        ArrayList<Transitable> vehiculosTransitables = generarVehiculos(200);
        ArrayList<Estacionable> cocheras = generarCocheras(130);

        simulador = SimuladorTransito.getInstancia();
        simulador.addTransitables(vehiculosTransitables);
        simulador.addEstacionables(cocheras);
        //2. Programarlo
        try {
            FlujoIngreso flujo = new FlujoIngreso("Ingreso matutino", new Periodo(0, 5), 10);
            simulador.programar(flujo);
            FlujoIngreso flujo2 = new FlujoIngreso("Entrada Partido", new Periodo(3, 6), 10);
            simulador.programar(flujo2);
            PerfilIngreso perfilMultas = new PerfilIngreso.Builder().invadirEstacionableDiscapacitado(Modo.ALEATORIO)
                    .buildRandom();
            FlujoIngreso flujoMultas = new FlujoIngreso("Ingeso con multas", new Periodo(0, 2), 8, perfilMultas);
            simulador.programar(flujoMultas);
            
            PerfilIngreso perfilAnomalia = new PerfilIngreso.Builder().ocuparEstacionableOcupado(Modo.SIEMPRE).buildRandom();
            FlujoIngreso flujoAnomalia = new FlujoIngreso("Ingreso anomalo", new Periodo(0, 4), 10, perfilAnomalia);
            simulador.programar(flujoAnomalia);

            FlujoEgreso flujoegreso3 = new FlujoEgreso("Egreso normal", new Periodo(6, 10), 20);
            simulador.programar(flujoegreso3);
            
            PerfilEgreso perfilEgresoAnomalo = new PerfilEgreso.Builder().buildRandom();
            FlujoEgreso flujoEgresoAnomalo = new FlujoEgreso("Egreso con anomalias", new Periodo(7,8), 5, perfilEgresoAnomalo);
            simulador.programar(flujoEgresoAnomalo);

            //3. Ejecutarlo  
            simulador.iniciar(new Sensor() {
                @Override
                public void ingreso(Transitable transitable, Estacionable estacionable) {
                    Vehiculo v = (Vehiculo) transitable;
                    Cochera c = (Cochera) estacionable;
                    Vehiculo vehiculo = fachada.getVehiculo(v.getPatente());
                    fachada.ingresarVehiculo(c.getCodigo(), vehiculo);
                    
                    
                }

                @Override
                public void egreso(Transitable transitable, Estacionable estacionable) {
                    Vehiculo v = (Vehiculo) transitable;
                    Cochera c = (Cochera) estacionable;
                    Vehiculo vehiculo = fachada.getVehiculo(v.getPatente());
                    fachada.egresarVehiculo(c.getCodigo(), vehiculo);
                }

            });

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

        // Aseguramos que todas las etiquetas se usen al menos una vez
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

        // El 20% de las cocheras que tienen que tener etiquetas
        int cocherasConEtiqueta = (int) Math.ceil(cantidad * 0.2);

        // Asignamos etiquetas aleatoriamente
        while (cocheras.size() < cocherasConEtiqueta) {
            String codigoCochera;
            do {
                codigoCochera = "C" + (random.nextInt(100) + 1);
            } while (codigosUsados.contains(codigoCochera));

            codigosUsados.add(codigoCochera);
            Cochera cochera = new Cochera(codigoCochera);

            // Asignamos una o dos etiquetas 
            int numeroEtiquetas = random.nextInt(2) + 1; // 1 o 2 etiquetas
            for (int i = 0; i < numeroEtiquetas; i++) {
                Etiqueta etiqueta = etiquetas.get(random.nextInt(etiquetas.size()));
                if (!cochera.getEtiquetas().contains(etiqueta)) {
                    cochera.addEtiqueta(etiqueta);
                }
            }
            cocheras.add(cochera);
        }

        //Completamos el resto de las cocheras sin etiquetas
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
        for (int i = 0; i < 7; i++) {
            char caracter = caracteres.charAt(random.nextInt(caracteres.length()));
            patente.append(caracter);
        }
        return patente.toString();
    }

}
