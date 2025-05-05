package Contenedores;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Coleccion <T extends Identificador> implements Iterable<T>{
    private Set<T> coleccion;

    public Coleccion() {
        this.coleccion = new LinkedHashSet<>();
    }

    public void agregarElemento(T nuevoElemento) {
        coleccion.add(nuevoElemento);
    }


    public boolean eliminarElemento(String id) {
        return coleccion.removeIf(nuevoElemento -> Objects.equals(nuevoElemento.getId(),id));
    }


    public T buscarElemento(String id) {
        for (T elemento : coleccion) {
            if (elemento.getId().equals(id)) {
                return elemento;
            }
        }
        return null;
    }


    public void mostrarElementos() {
        for (T elemento : coleccion) {
            System.out.println(elemento);
        }
    }

    public T buscarPorNombre(String nombre) {
        for (T elemento : coleccion) {
            if (elemento.getNombre().equals(nombre)) {
                return elemento;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        if(coleccion.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return coleccion.iterator();
    }

}


