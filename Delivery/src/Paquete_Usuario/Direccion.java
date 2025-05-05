package Paquete_Usuario;

import java.util.Objects;
import java.util.Scanner;

public class Direccion {
    private String ciudad ;
    private String calle ;
    private int numero ;

    //Constructor
    public Direccion(String ciudad, String calle, int numero) {
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }

    //Getters and setters
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    //Método toString
    @Override
    public String toString() {
            return "DIRECCIÓN{" +
                "ciudad='" + ciudad + '\'' +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                '}';
    }

    //Método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return numero == direccion.numero && Objects.equals(ciudad, direccion.ciudad) && Objects.equals(calle, direccion.calle);
    }

}
