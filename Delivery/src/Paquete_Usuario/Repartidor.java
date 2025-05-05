package Paquete_Usuario;

import Contenedores.Identificador;

import java.util.Objects;

public class Repartidor extends Usuario implements Identificador {
    private int disponibilidad ;
    private Vehiculo vehiculo ;

    //Constructor
    public Repartidor(String id, String nombre, String email, String contrasena, int disponibilidad, Vehiculo vehiculo) {
        super(id, nombre, email, contrasena);
        this.disponibilidad = disponibilidad;
        this.vehiculo = vehiculo;
    }

    //Getters and Setters
    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int calificacion) {
        this.disponibilidad = calificacion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    //Método toString
    @Override
    public String toString() {
        return "Repartidor{" +
                "disponibilidad=" + disponibilidad +
                ", vehiculo=" + vehiculo +
                "} " + super.toString();
    }

    //Método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Repartidor that = (Repartidor) o;
        return disponibilidad == that.disponibilidad && Objects.equals(vehiculo, that.vehiculo);
    }

}
