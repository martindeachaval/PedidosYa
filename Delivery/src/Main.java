import Sistema.Sistema;
import UI.CentralDePedidos;
import Paquete_Restaurante.Excepciones.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        CentralDePedidos central = new CentralDePedidos();

        try {
            central.mostrarMenuGeneral();
        } catch (ValidarProductoRepetido e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
