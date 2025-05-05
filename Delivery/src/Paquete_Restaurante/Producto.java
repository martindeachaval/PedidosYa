package Paquete_Restaurante;

import Contenedores.Identificador;

import java.util.Objects;

public class Producto implements Identificador {
    private String id ;
    private String nombre ;
    private double precio ;
    private int stock ;

    //Constructores

    public Producto() {
        this.id = null;
        this.nombre = null;
        this.precio = 0;
        this.stock = 0;
    }

    public Producto(String id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    //Getters and setters
    @Override
    public String getId() {
        return id;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    //Método toString
    @Override
    public String toString() {
        return "PRODUCTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }

    //Método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Double.compare(precio, producto.precio) == 0 && stock == producto.stock && Objects.equals(id, producto.id) && Objects.equals(nombre, producto.nombre);
    }

    //Método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precio, stock);
    }
}
