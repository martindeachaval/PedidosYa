package UI;

import Contenedores.Coleccion;
import Paquete_Pedido.Pedido;
import Paquete_Restaurante.Producto;
import Paquete_Restaurante.Restaurante;
import Paquete_Usuario.Cliente;
import Paquete_Usuario.Direccion;
import Paquete_Usuario.Repartidor;
import Paquete_Usuario.Usuario;
import Sistema.Sistema;
import UI.Menu_Restaurante ;

import java.util.Scanner;

public class Menu_Pedido {

    Scanner scanner = new Scanner(System.in) ;


    public Menu_Pedido() {
    }

    public void mostrarMenuPedido(Coleccion<Pedido> coleccionPedido, Coleccion<Repartidor> coleccionRepartidor, Coleccion<Restaurante> coleccionRestaurante, Sistema sistema) {
        while (true){
            System.out.println("\n\t\t-- PEDIDO --");
            System.out.println("[ 1 ] Agregar un pedido..");
            System.out.println("[ 2 ] Cancelar un pedido..");
            System.out.println("[ 3 ] Mostrar listado de pedidos..");
            System.out.println("[ 4 ] Salir");
            System.out.println("> Ingrese una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: //Agregar un pedido
                    Pedido pedido = agregarUnPedido(coleccionPedido, coleccionRestaurante, coleccionRepartidor, sistema);
                    coleccionPedido.agregarElemento(pedido);
                    break;

                case 2: //Eliminar un pedido
                    cancelarPedido(coleccionPedido);
                    break;

                case 3: //Mostrar listado de pedidos
                    mostrarPedido(coleccionPedido);
                    break;

                case 4: //Salir
                    System.out.println("\t\t--[ Saliendo ]--");
                    return;

                default:
                    System.out.println("\t\t--[ OPCIÓN NO VALIDA ]-- ");
            }
        }
    }

/*
    En un principio se había pensando un método para modificar, pero creímos mejor que no sea posible una modificación
    luego de ser realizado el pedido. En realidad se debería eliminar el pedido en sus totalidad.
*/

    private String generarIdUnico() {
        return " "+System.currentTimeMillis(); // Genera ID mostrando la hora en milisegundcs
    }

    public Direccion cargarUnaDireccion(){
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

    public Pedido agregarUnPedido(Coleccion<Pedido> coleccionPedido, Coleccion<Restaurante> coleccionRestaurante, Coleccion<Repartidor> coleccionRepartidor, Sistema sistema){
        Usuario usuarioActivo = sistema.getUsuarioActivo() ;
        String id = generarIdUnico() ;

        Direccion direccion = cargarUnaDireccion() ;
        Cliente cliente = new Cliente(usuarioActivo.getId(), usuarioActivo.getNombre(), usuarioActivo.getEmail(), usuarioActivo.getContrasena(), direccion) ;

        Menu_Repartidor menuRepartidor = new Menu_Repartidor() ;
        Repartidor repartidor = menuRepartidor.seleccionarRepartidor(coleccionRepartidor) ;
        if(repartidor == null){
            System.out.println("\t\t--[ No hay repartidores disponibles en este momento ]--");
            return null;
        }

        Menu_Restaurante menuRestaurante = new Menu_Restaurante() ;
        Restaurante restaurante = menuRestaurante.seleccionarRestaurante(coleccionRestaurante) ;
        if(restaurante!=null) {
            mostrarProductoRestauranteEsp(restaurante);
        }

        Producto producto = menuRestaurante.seleccionarProducto(restaurante) ;

        int estadoDelPedido = 0; //Arranca estando disponible
        Pedido pedido = new Pedido(id, cliente, repartidor, restaurante, producto, estadoDelPedido) ;
        System.out.println("\n-----------ID de pedido: "+pedido.getId()+"-----------");

        double kilometros=0;
        do {
            System.out.println("\nIngrese la cantidad de kilometros para calcular tu envio: ");
            kilometros=scanner.nextDouble();

        }while (kilometros<=0);

        double envioTotal=calcularEnvio(kilometros);
        System.out.println("El costo del envio es de: "+envioTotal);

        return pedido;
    }

    public void mostrarProductoRestauranteEsp(Restaurante restauranteEsp){
        for(Producto producto : restauranteEsp.getProductos()){
            System.out.println("----------------------------------");
            System.out.println("ID Producto= "+ producto.getId());
            System.out.println("Nombre= "+ producto.getNombre());
            System.out.println("Precio= "+ producto.getPrecio());
            System.out.println("Stock= "+ producto.getStock());
            System.out.println("----------------------------------");
        }
    }

    public Pedido buscarPedido(String id, Coleccion<Pedido> coleccionPedido ) {
        for (Pedido elemento : coleccionPedido) {
            if (elemento.getId().equals(id)) {
                /*
                System.out.println("Elemento: "+elemento);//Lo muestra correctamente
                 */
                return elemento;
            }
            /*
            System.out.println("Elemento: "+elemento);//Lo convierte en NULL
             */
        }
        return null;
    }
    public void cancelarPedido(Coleccion<Pedido> coleccionPedido){
        System.out.println("* Ingresa el id de tu pedido: ");
        String idBuscado = scanner.nextLine();

        Pedido pedidoBuscado = buscarPedido(idBuscado, coleccionPedido);
        //Devuelve NULL

        if(pedidoBuscado != null){   //Arrastra el NULL de la funcion buscarPedido
            pedidoBuscado.setCancelado(1); //1=Cancelado
            System.out.println("\t\t--[ Pedido cancelado ]--");
        }else{
            System.out.println("\t\t--[ No se encontró el pedido ]--");
        }
    }

    public void mostrarPedido(Coleccion<Pedido> coleccionPedido){
        for(Pedido pedido : coleccionPedido){
            System.out.println("\t\t--[ INFORMACIÓN DEL PEDIDO ]--");
            System.out.println("ID Pedido= "+ pedido.getId());
            System.out.println("Cliente= "+ pedido.getCliente().getNombre());
            System.out.println("Repartidor= "+ pedido.getRepartidor().getNombre());
            System.out.println("Restaurante= "+ pedido.getRestaurante().getNombre());
            System.out.println("Producto= "+ pedido.getProducto().getNombre());
        }
    }

    public double calcularEnvio(double kilometro){
        int cantCuadras = (int) (kilometro * 10);
        double costoEnvioPorCuadra = 100.0;
        return cantCuadras * costoEnvioPorCuadra ;
    }
}
