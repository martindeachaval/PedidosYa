package UI;

import Paquete_Restaurante.Excepciones.ValidarProductoRepetido;
import Paquete_Restaurante.Producto;
import Paquete_Restaurante.Restaurante;

import java.util.Scanner;

public class Menu_Producto {

    Scanner scanner = new Scanner(System.in) ;
    private Restaurante restaurante ;

    public Menu_Producto(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public void mostrarMenuProducto() throws ValidarProductoRepetido {
        Scanner scanner = new Scanner(System.in) ;
        int opcion;
        while (true) {
            System.out.println("\n\t\t-- PRODUCTO --");
            System.out.println("[ 1 ] Agregar producto..");
            System.out.println("[ 2 ] Modificar producto..");
            System.out.println("[ 3 ] Eliminar un producto..");
            System.out.println("[ 4 ] Mostrar listado de productos..");
            System.out.println("[ 5 ] Salir");
            System.out.println("> Ingrese una opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: //Agregar producto
                    try {
                        agregarProducto(scanner);
                    }catch (ValidarProductoRepetido x){
                        System.out.println(x.getMessage());
                    }

                    break;
                case 2: //Modificar producto
                    modificarElemento(scanner);
                    break;
                case 3: //Eliminar un producto
                    eliminarProducto(scanner);
                    break;
                case 4: //Mostrar listado de productos
                    restaurante.mostrarListadoProductos();
                    break;
                case 5: //Salir
                    System.out.println("\t\t--[ Saliendo ]--");
                    return;
                default:
                    System.out.println("\t\t--[ OPCIÓN NO VALIDA ]-- ");
            }
        }
    }


    //Cargar un producto
    public void agregarProducto(Scanner scanner) throws ValidarProductoRepetido {
        System.out.println("¬ Ingresa el id del producto: ");
        String id = scanner.nextLine();

        System.out.println("¬ Ingresa el nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("¬ Ingresa el precio: ");
        double precio = scanner.nextDouble();

        System.out.println("¬ Ingresa el stock: ");
        int stock = scanner.nextInt();

        restaurante.agregarProducto(new Producto(id, nombre, precio, stock));
    }


    //Elegir qué elemento modificar
    public void modificarElemento(Scanner scanner){
        System.out.println("\n* Ingresa el id del producto que queres modificar: ");
        String id = scanner.nextLine();

        Producto producto = restaurante.buscarProducto(id) ;
        if(producto != null) {
            while (true){
                System.out.println("* ¿Qué queres modificar? ");
                System.out.println("\t 1- Stock.. ");
                System.out.println("\t 2- Precio.. ");
                System.out.println("> Opción: ");
                int opcion = scanner.nextInt();

                switch (opcion){
                    case 1:
                        System.out.println("- Ingresa el nuevo stock: ");
                        int nuevoStock = scanner.nextInt() ;
                        restaurante.modificarStock(nuevoStock, id) ;
                        break;

                    case 2:
                        System.out.println("- Ingresa el porcentaje que le queres aplicar a tu precio (En caso de querer restarle, ingresar porcentaje negativo):");
                        double porcentaje = scanner.nextDouble();
                        restaurante.modificarPrecio(id, porcentaje);
                        break;
                } //fin switch
                break;
            }
    /*
        Lo puse con un switch para que puedan agregarse más opciones a futuro o no, y en base a eso sólo
        modificar un campo de toda la clase.
    */
        }else{
            System.out.println("\t --[ Producto no encontrado ]-- ");
        }
    }

    //Eliminar un producto
    public void eliminarProducto(Scanner scanner){
        System.out.println("\n* Ingresa el id del producto que queres eliminar: ");
        String id = scanner.nextLine();
        restaurante.eliminarProducto(id);
    }


}
