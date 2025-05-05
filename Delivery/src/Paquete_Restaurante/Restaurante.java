package Paquete_Restaurante;
import Contenedores.Coleccion;
import Contenedores.Identificador;
import Paquete_Restaurante.Excepciones.ValidarProductoRepetido;
import Paquete_Usuario.Direccion ;

import java.util.ArrayList;
import java.util.Objects;

public class Restaurante implements Identificador {
    private String id;
    private String nombre;
    private Direccion direccion;
    private Coleccion<Producto> productos;

    //Constructor
    public Restaurante(String id, String nombre, Direccion direccion) {
        this.id = id ;
        this.nombre = nombre;
        this.direccion = direccion;
        this.productos = new Coleccion<Producto>();
    }

    //Getters and setters
    @Override
    public String getId() {
        return null;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Coleccion<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Coleccion<Producto> productos) {
        this.productos = productos;
    }

    //Método toString
    @Override
    public String toString() {
        String productosString = " ";
        for(Producto producto : productos){
            productosString += producto.toString();
        }
        return "RESTAURANTE{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion=" + direccion +
                ", productos=" + productosString +
                '}';
    }

    //Método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(direccion, that.direccion) && Objects.equals(productos, that.productos);
    }

    //Método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, direccion, productos);
    }


    //Métodos principales: Agregar, modificar, eliminar, listado (PRODUCTOS).

    //Agregar producto
    public void agregarProducto(Producto producto) throws ValidarProductoRepetido {
        for (Producto elemento : productos){
            if (elemento.getId().equals(producto.getId())) {
                throw new ValidarProductoRepetido("--[ El producto YA existe. Modifique su stock en todo caso ]--");
            }
        }
        productos.agregarElemento(producto);
        System.out.println("\t--[ Producto agregado con éxito ]-- ");
    }

    //Modificar producto -> busco el producto antes

    public Producto buscarProducto(String id) {
        for (Producto elemento : productos){
            if (elemento.getId().equals(id)) {
                return elemento;
            }
        }
        return null;
    }

    public void modificarStock(int nuevoStock, String id) {
        Producto productoBuscado = buscarProducto(id);
        if (productoBuscado != null) {
            productoBuscado.setStock(nuevoStock);
            System.out.println("\t --[ Producto modificado con éxito ]--");
        } else {
            System.out.println("\t --[ El producto no fue encontrado ]--");
        }
    }

    /*
        Para modificar el precio, se utiliza un porcentaje y en base al que ya tenía puesto se aumenta o reduce.
        Simplemente le llega el porcentaje y se calcula. Puede ser negativo o positivo ese porcentaje.
     */
    public void modificarPrecio(String id, double porcentaje) {
        Producto productoBuscado = buscarProducto(id);
        if (productoBuscado != null) {
            double precioActual = productoBuscado.getPrecio();
            double nuevoPrecio = precioActual + (precioActual * porcentaje / 100);
            productoBuscado.setPrecio(nuevoPrecio);
            System.out.println("\t --[ El nuevo precio del producto es " + nuevoPrecio + " ]--");
        } else {
            System.out.println("\t --[ El producto no fue encontrado ]--");
        }
    }


    //Eliminar producto
    public void eliminarProducto(String id) {
        boolean prodEliminado = productos.eliminarElemento(id);
        if (prodEliminado) {
            System.out.println("\t --[ Producto eliminado con éxito ]--");
        } else {
            System.out.println("\t --[ El producto no fue encontrado ]--");
        }
    }


    //Mostrar listado de productos
    public void mostrarListadoProductos() {
        if (productos.isEmpty()) {
            System.out.println("\t --[ La lista está vacía ]--");
        } else {
            for (Producto elemento : productos){
                System.out.println(elemento);
            }
        }
    }




}
