/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iu;

import java.util.ArrayList;
import java.util.Random;
import logica.Cochera;
import logica.CuentaCorriente;
import logica.Etiqueta;
import logica.Parking;
import logica.Propietario;
import logica.Tarifario;
import logica.TipoVehiculo;
import logica.Vehiculo;

/**
 *
 * @author sofia
 */
public class DatosPrueba {

    public static void iniciar() {
        Parking parking1 = new Parking("Parking 1", "Av Italia 3342");
        Parking parking2 = new Parking("Parking 2", "Canelones 1236");

        ArrayList<Propietario> propietarios = generarPropietarioAleatorio();

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

        ArrayList<Vehiculo> vehiculos = generarVehiculosAleatorios(propietarios, etiquetas, tiposVehiculos, 4);

        ArrayList<Tarifario> tarifarios = new ArrayList();
        Tarifario tarifarioMotocicletas = new Tarifario(motocicleta, 0.05);
        Tarifario tarifarioStandard = new Tarifario(standard, 0.1);
        Tarifario tarifarioCarga = new Tarifario(standard, 0.1);
        Tarifario tarifarioPasajero = new Tarifario(standard, 0.1);
        tarifarios.add(tarifarioMotocicletas);
        tarifarios.add(tarifarioStandard);
        tarifarios.add(tarifarioCarga);
        tarifarios.add(tarifarioPasajero);

        parking1.setTarifarios(tarifarios);
        parking2.setTarifarios(tarifarios);

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
        ArrayList<Cochera> cocheras = new ArrayList();
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            String codigoCochera = "C" + (random.nextInt(100) + 1);
            Cochera cochera = new Cochera(codigoCochera);
            for (Etiqueta e : etiquetas) {
                int probabilidad = random.nextInt(10);
                if (probabilidad < 2 && cochera.getEtiquetas().size() < 2) {
                    cochera.addEtiqueta(e);
                }
                cocheras.add(cochera);
            }

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
