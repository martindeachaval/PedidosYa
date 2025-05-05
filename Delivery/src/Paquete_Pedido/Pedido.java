package Paquete_Pedido;

import Contenedores.Coleccion;
import Contenedores.Identificador;
import Paquete_Restaurante.Producto;
import Paquete_Restaurante.Restaurante;
import Paquete_Usuario.Cliente;
import Paquete_Usuario.Repartidor;

import java.util.Iterator;
import java.util.Objects;

public class Pedido implements Identificador {
    private String id;
    private Cliente cliente;
    private Repartidor repartidor ;
    private Restaurante restaurante;
    private Producto producto ;
    private int cancelado ; //0=DISPONIBLE - 1=CANCELADO

    //Constructor
    public Pedido(String id, Cliente cliente, Repartidor repartidor, Restaurante restaurante, Producto producto, int cancelado) {
        this.id = id;
        this.cliente = cliente;
        this.repartidor = repartidor;
        this.restaurante = restaurante;
        this.producto = producto;
        this.cancelado = cancelado;
    }

    //Getters and setters

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }

    //Método toString
    @Override
    public String toString() {
        return "PEDIDO{" +
                "id='" + id + '\'' +
                ", cliente=" + cliente +
                ", repartidor=" + repartidor +
                ", restaurante=" + restaurante +
                ", producto=" + producto +
                '}';
    }

    //Método equals


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return cancelado == pedido.cancelado && Objects.equals(id, pedido.id) && Objects.equals(cliente, pedido.cliente) && Objects.equals(repartidor, pedido.repartidor) && Objects.equals(restaurante, pedido.restaurante) && Objects.equals(producto, pedido.producto);
    }

    //Método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, repartidor, restaurante, producto, cancelado);
    }

    @Override
    public String getNombre() {
        return null;
    }
}
