package UI;

import Contenedores.Coleccion;
import Paquete_Pedido.Pedido;
import Paquete_Restaurante.Excepciones.ValidarProductoRepetido;
import Paquete_Restaurante.Restaurante;
import Paquete_Usuario.Repartidor;
import Paquete_Usuario.Usuario;
import Sistema.Sistema;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CentralDePedidos {


    Scanner scanner = new Scanner(System.in) ;
    Coleccion<Pedido> coleccionPedido = new Coleccion<>();
    Coleccion<Repartidor> coleccionRepartidor = new Coleccion<>();
    Coleccion<Restaurante> coleccionRestaurante = new Coleccion<>();
    Sistema sistema = new Sistema() ;


    public void mostrarMenuGeneral() throws ValidarProductoRepetido {
        //Cargamos datos al inicio
        try {
            coleccionRepartidor = cargarRepartidoresDesdeArchivo();
            coleccionRestaurante = cargarRestaurantesDesdeArchivo();
            coleccionPedido = cargarPedidosDesdeArchivo();
        } catch (IOException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n\t\t-- PEDIDOS --");
            System.out.println("[ 1 ] Cliente..");
            System.out.println("[ 2 ] Repartidor..");
            System.out.println("[ 3 ] Restaurante..");
            System.out.println("[ 4 ] Salir");
            System.out.println("> Ingrese una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                case 1: //Menu cliente
                    sistema = menuSistema(scanner);
                    Menu_Pedido menuPedido = new Menu_Pedido() ;
                    menuPedido.mostrarMenuPedido(coleccionPedido, coleccionRepartidor, coleccionRestaurante, sistema);
                    break;

                case 2: //Menu repartidor
                    sistema = menuSistema(scanner);
                    Menu_Repartidor menuRepartidor = new Menu_Repartidor();
                    menuRepartidor.mostrarMenuRepartidor(coleccionRepartidor, sistema);
                    break;

                case 3: //Menu restaurante
                    sistema = menuSistema(scanner);
                    Menu_Restaurante menuRestaurante = new Menu_Restaurante() ;
                    menuRestaurante.mostrarMenuRestaurante(coleccionRestaurante);
                    break;

                case 4: //Salir
                    System.out.println("\t\t--[ Saliendo ]--");
                    return;

                default:
                    System.out.println("\t\t--[ OPCIÓN NO VALIDA ]-- ");
            }
        }
    }

    public Sistema menuSistema(Scanner scanner){
        int opcion = 0;
        do{
            System.out.println("\n--¡MENÚ PRINCIPAL!--");
            System.out.println("\t [ 1 ] Iniciar sesión.. ");
            System.out.println("\t [ 2 ] Registrarse.. ");
            System.out.println("- OPCIÓN: ");
            opcion = scanner.nextInt();
        }while (opcion < 0 || opcion > 2) ;

        Sistema sistema = new Sistema();

        switch (opcion){
            case 1:
                boolean sesionIniciada = false;
                do{
                    try {
                        sistema.iniciarSesion();
                        sesionIniciada = true;
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }while (!sesionIniciada);
                break;
            case 2:
                sistema.registrarUsuario();
                break;
        }
        return sistema;
    }

    private Coleccion<Repartidor> cargarRepartidoresDesdeArchivo() throws IOException {
        File file = new File("repartidores.json");
        if (!file.exists()) {
            return new Coleccion<>();
        }
        try (Reader reader = new FileReader(file)) {
            Type repartidorColeccionType = new TypeToken<Coleccion<Repartidor>>() {}.getType();
            return new Gson().fromJson(reader, repartidorColeccionType);
        }
    }

    private void guardarRepartidoresEnArchivo(Coleccion<Repartidor> repartidores) throws IOException {
        try (Writer writer = new FileWriter("repartidores.json")) {
            new Gson().toJson(repartidores, writer);
        }
    }

    private Coleccion<Restaurante> cargarRestaurantesDesdeArchivo() throws IOException {
        File file = new File("restaurantes.json");
        if(!file.exists()){
            return new Coleccion<>();
        }
        try(Reader reader = new FileReader(file)){
            Type restauranteColeccionType = new TypeToken<Coleccion<Restaurante>>() {}.getType();
            return new Gson().fromJson(reader, restauranteColeccionType);
        }
    }

    private void guardarRestaurantesEnArchivo(Coleccion<Restaurante>restaurantes)throws IOException{
        try(Writer writer = new FileWriter("restaurantes.json")){
            new Gson().toJson(restaurantes, writer);
        }
    }

    private Coleccion<Pedido> cargarPedidosDesdeArchivo() throws IOException {
        File file = new File("pedidos.json");
        if (!file.exists()) {
            return new Coleccion<>();
        }
        try (Reader reader = new FileReader(file)) {
            Type pedidoColeccionType = new TypeToken<Coleccion<Pedido>>() {}.getType();
            return new Gson().fromJson(reader, pedidoColeccionType);
        }
    }

    private void guardarPedidosEnArchivo(Coleccion<Pedido> pedidos) throws IOException {
        try (Writer writer = new FileWriter("pedidos.json")) {
            new Gson().toJson(pedidos, writer);
        }
    }


    private void guardarDatos() {
        try {
            guardarRepartidoresEnArchivo(coleccionRepartidor);
            guardarRestaurantesEnArchivo(coleccionRestaurante);
            guardarPedidosEnArchivo(coleccionPedido);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

}
