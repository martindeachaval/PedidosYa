package Paquete_Usuario;

import java.util.Objects;

public class Cliente extends Usuario {
    private Direccion direccion ;

    //Constructor
    public Cliente(String id, String nombre, String email,String contrasena,  Direccion direccion) {
        super(id, nombre, email, contrasena);
        this.direccion = direccion;
    }

    //Getter and setter
    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    //Método toString
    @Override
    public String toString() {
        return "CLIENTE{" +
                "direccion=" + direccion +
                "} " + super.toString();
    }

    //Método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(direccion, cliente.direccion);
    }

}
