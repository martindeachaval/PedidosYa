package UI;

import Contenedores.Coleccion;
import Paquete_Usuario.Repartidor;
import Paquete_Usuario.Usuario;
import Paquete_Usuario.Vehiculo;
import Sistema.Sistema ;

import UI.CentralDePedidos ;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu_Repartidor {

    private static final String REPARTIDOR_FILE_PATH = "repartidores.json";
    private Gson gson = new Gson();


    public Menu_Repartidor() {

    }

    public void mostrarMenuRepartidor(Coleccion<Repartidor> coleccionRepartidor, Sistema sistema) {
        Scanner scanner = new Scanner(System.in);

        int opcion = 0;
        do {
            System.out.println("\n\t\t-- REPARTIDOR --");
            System.out.println("[ * ] ¿Tu vehículo está registrado en el sistema?..");
            System.out.println("\t[ 1 ] SI..");
            System.out.println("\t[ 2 ] NO..");

            System.out.println("> Ingrese una opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 2) {
                Repartidor repartidor = agregarRepartidor(sistema, scanner);
                coleccionRepartidor.agregarElemento(repartidor);

                try {
                    System.out.println("Antes: "+coleccionRepartidor);
                    guardarRepartidoresEnArchivo(coleccionRepartidor);
                    System.out.println("Despues: "+coleccionRepartidor);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Tus datos son: " + repartidor);
            } else {
                System.out.println("\t¡Excelente! Ya podes ser asignado para un pedido..");
            }
            break;
        } while (opcion < 0 || opcion > 2);

    }

    public Vehiculo cargarVehiculo(Scanner scanner) {
        System.out.println("\t -- VEHICULO --");
        System.out.println("* Ingresa la patente: ");
        String patente = scanner.nextLine();

        System.out.println("* Ingresa la marca: ");
        String marca = scanner.nextLine();

        System.out.println("* Ingresa el modelo: ");
        String modelo = scanner.nextLine();

        Vehiculo vehiculo = new Vehiculo(patente, modelo, marca);
        return vehiculo;
    }

    public Repartidor agregarRepartidor(Sistema sistema, Scanner scanner) {
        Usuario usuarioActivo = sistema.getUsuarioActivo();

        Vehiculo vehiculo = cargarVehiculo(scanner);

        int disponibilidad = 0; // 0=DISPONIBLE - 1= NO DISPONIBLE
        return new Repartidor(usuarioActivo.getId(), usuarioActivo.getNombre(), usuarioActivo.getEmail(), usuarioActivo.getContrasena(), disponibilidad, vehiculo);
    }


    public Repartidor seleccionarRepartidor(Coleccion<Repartidor> coleccionRepartidor) {
        for (Repartidor repartidor : coleccionRepartidor) {
            if (repartidor.getDisponibilidad() == 0) {
                return repartidor;
            }
        }
        return null; // No hay repartidores disponibles
    }

    private Coleccion<Repartidor> cargarRepartidoresDesdeArchivo() throws IOException {
        File file = new File(REPARTIDOR_FILE_PATH);
        if(!file.exists()){
            return new Coleccion<>();
        }
        try(Reader reader = new FileReader(file)){
            Type repartidorColeccionType = new TypeToken<Coleccion<Repartidor>>() {}.getType();
            return gson.fromJson(reader, repartidorColeccionType);
        }
    }

    private void guardarRepartidoresEnArchivo(Coleccion<Repartidor>repartidores) throws IOException {

        try(Writer writer = new FileWriter(REPARTIDOR_FILE_PATH)){
            gson.toJson(repartidores, writer);
        }
    }
}
