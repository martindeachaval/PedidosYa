package UI;

import Contenedores.Coleccion;
import Paquete_Restaurante.Excepciones.ValidarProductoRepetido;
import Paquete_Restaurante.Producto;
import Paquete_Restaurante.Restaurante;
import Paquete_Usuario.Direccion;
import Paquete_Usuario.Repartidor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Scanner;

public class Menu_Restaurante {


    private static final String RESTAURANTE_FILE_PATH = "restaurantes.json";
    private Gson gson = new Gson();
    Scanner scanner = new Scanner(System.in) ;


    public Menu_Restaurante() {

    }

    public void mostrarMenuRestaurante(Coleccion<Restaurante> coleccionRestaurante) throws ValidarProductoRepetido {
        Scanner scanner = new Scanner(System.in) ;
        while (true){
            System.out.println("\n\t\t-- RESTAURANTE --");
            System.out.println("[ 1 ] Agregar un restaurante..");
            System.out.println("[ 2 ] Mostrar listado de restaurantes..");
            System.out.println("[ 3 ] Salir");
            System.out.println("> Ingrese una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: //Agregar un restaurante
                    Restaurante restaurante = cargarUnRestaurante(scanner) ;
                    coleccionRestaurante.agregarElemento(restaurante);


                    try {
                        guardarRestaurantesEnArchivo(coleccionRestaurante);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 2: //Mostrar listado de restaurantes
                    coleccionRestaurante.mostrarElementos();
                    break;

                case 3: //Salir
                    System.out.println("\t\t--[ Saliendo ]--");
                    return;

                default:
                    System.out.println("\t\t--[ OPCIÓN NO VALIDA ]-- ");
            }
        }


    }

    public Direccion cargarUnaDireccion(Scanner scanner){
        System.out.println("\t -- DIRECCIÓN --");
        System.out.println("* Ingresa la ciudad: ");
        String ciudad = scanner.nextLine();

        System.out.println("* Ingresa la calle: ");
        String calle = scanner.nextLine();

        System.out.println("* Ingresa el número: ");
        int numero = scanner.nextInt();

        Direccion direccion = new Direccion(ciudad, calle, numero);
        return direccion ;
    }

    private String generarIdUnico() {
        return " "+System.currentTimeMillis(); // Genera ID mostrando la hora en milisegundcs
    }

    public Restaurante cargarUnRestaurante(Scanner scanner) throws ValidarProductoRepetido {

        String id = generarIdUnico() ;

        System.out.println("* Ingresa el nombre del restaurante: ");
        String nombre = scanner.nextLine();

        Direccion direccion = cargarUnaDireccion(scanner) ;

        Restaurante restaurante = new Restaurante(id,nombre, direccion) ;

        Menu_Producto menuProducto = new Menu_Producto(restaurante) ;
        menuProducto.mostrarMenuProducto();

        return restaurante ;
    }

    public Restaurante seleccionarRestaurante(Coleccion<Restaurante> coleccionRestaurante){
        Restaurante restauranteElegido = null;
        if(coleccionRestaurante.isEmpty()){
            System.out.println("\t\t--[ No hay restaurantes disponibles ]--");
        }else {
            System.out.println("\t\t--[ LISTADO RESTAURANTES ]--");
            mostrarDatosEspResto(coleccionRestaurante);

            System.out.println("* Ingresa el nombre del restaurante: ");
            String nombre = scanner.nextLine();

            restauranteElegido = coleccionRestaurante.buscarPorNombre(nombre);
        }

        return restauranteElegido;
    }

    public void mostrarDatosEspResto(Coleccion<Restaurante> coleccionRestaurante){
        for(Restaurante restaurante : coleccionRestaurante){
            System.out.println("--------------------------------------------------");
            System.out.println("Nombre= "+ restaurante.getNombre());
            System.out.println("Dirección= "+ restaurante.getDireccion().getCalle()+ " N°= "+ restaurante.getDireccion().getNumero());
            System.out.println("--------------------------------------------------");
        }
    }

    public Producto seleccionarProducto(Restaurante restaurante){
        Producto producto = null;
        do{
            System.out.println("* Ingresa el id del producto elegido: ");
            String idElegido = scanner.nextLine() ;

            Coleccion<Producto> productos = restaurante.getProductos() ;
            producto = productos.buscarElemento(idElegido) ;
            if(producto == null){
                System.out.println("\t\t--[ El id del producto no existe ]--");
            }
        }while (producto == null);
        //PREGUNTAR CUANTAS UNIDADES QUIERE PEDIR, COMPARAR CON STOCK, MODIFICAR STOCK
        int cantidad;
        boolean exitoso = false;
        do{
            System.out.println("Ingrese la cantidad deseada: ");
            cantidad = scanner.nextInt();
            scanner.nextLine(); //Limpia Buffer

            if(cantidad<=0){
                System.out.println("\t\t--[ La cantidad debe ser mayor a cero ]--");
            } else if (cantidad > producto.getStock()) {
                System.out.println("\t\t--[ Stock insuficiente. Stock disponible: " + producto.getStock() + " ]--");
            } else {
                producto.setStock(producto.getStock() - cantidad);
                System.out.println("\t\t--[ Pedido realizado con éxito. Stock restante: " + producto.getStock() + " ]--");
                exitoso = true; //Pasamos el pedido exitoso
            }
        } while (!exitoso);

        return producto ;
    }


    private Coleccion<Restaurante> cargarRestaurantesDesdeArchivo() throws IOException {
        File file = new File(RESTAURANTE_FILE_PATH);
        if(!file.exists()){
            return new Coleccion<>();
        }
        try(Reader reader = new FileReader(file)){
            Type restauranteColeccionType = new TypeToken<Coleccion<Restaurante>>() {}.getType();
            return gson.fromJson(reader, restauranteColeccionType);
        }
    }

    private void guardarRestaurantesEnArchivo(Coleccion<Restaurante>restaurantes)throws IOException{
        /*ArrayList<Restaurante> listaRestaurantes = new ArrayList<>();
        for(Restaurante restaurante : restaurantes){
            listaRestaurantes.add(restaurante) ;
        }
        */
        try(Writer writer = new FileWriter(RESTAURANTE_FILE_PATH)){
            gson.toJson(restaurantes, writer);
        }
    }

}





