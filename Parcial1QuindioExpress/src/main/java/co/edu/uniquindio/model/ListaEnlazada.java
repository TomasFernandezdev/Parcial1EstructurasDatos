package co.edu.uniquindio.model;

import java.util.function.Consumer;

public class ListaEnlazada<T> {
    public Nodo<T> cabeza;

    public void addFirst(T t){
        Nodo<T> nuevoNodo = new Nodo<T>(t);
        nuevoNodo.setSiguiente(cabeza);
        cabeza = nuevoNodo;
    }
    public void addLast(T t){
        if(cabeza == null){
            cabeza = new Nodo<T>(t);
        }else{
            Nodo<T> actual = cabeza;
            while(actual.getSiguiente() != null){
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(new Nodo<T>(t));
        }
    }
    public void removeFirst(){
        if(cabeza != null){
            cabeza = cabeza.getSiguiente();
        }
    }
    public void removeLast(){
        if(cabeza == null){
            throw new RuntimeException("La lista está vacía");
        }
        if(cabeza.getSiguiente() == null){
            cabeza = null;
            return;
        }
        for(Nodo<T> actual = cabeza; actual.getSiguiente() != null; actual = actual.getSiguiente()){
            if(actual.getSiguiente().getSiguiente() == null){
                actual.setSiguiente(null);
                break;
            }
        }
    }
    public Nodo<T> getFirst(){
        return cabeza;
    }
    public Nodo<T> getLast(){
        if (cabeza == null) {
            return null;
        }
        Nodo<T> actual = cabeza;
        while(actual.getSiguiente() != null){
            actual = actual.getSiguiente();
        }
        return actual;
    }
    public Nodo<T> findByIndex(int index){
        Nodo<T> actual = cabeza;
        int contador = 0;
        while(actual != null){
            if(contador == index){
                return actual;
            }
            actual = actual.getSiguiente();
            contador++;
        }
        return null;
    }
    public void removeByIndex(int index){
        if (cabeza == null || index < 0) {
            return;
        }
        if (index == 0) {
            cabeza = cabeza.getSiguiente();
            return;
        }
        int contador = 0;
        Nodo<T> aux = cabeza;
        for (Nodo<T> nodo = cabeza.getSiguiente(); nodo != null; nodo = nodo.getSiguiente()) {
            contador++;
            if (contador == index) {
                aux.setSiguiente(nodo.getSiguiente());
                return;
            }
            aux = nodo;
        }
    }
    public void print(){
        for(Nodo<T> nodo = cabeza;nodo != null; nodo = nodo.getSiguiente()){
            System.out.println(nodo.getT());
        }
    }

    public void mostrarHistorial() {
        if (cabeza == null) {
            System.out.println("El historial de entregas está vacío");
            return;
        }
        Nodo<T> actual = cabeza;
        while (actual != null) {
            System.out.print(actual.getT());

            if (actual.getSiguiente() != null) {
                System.out.print(" -> ");
            }
            actual = actual.getSiguiente();
        }
        System.out.println();
    }

    public T buscar(Criterio<T> criterio) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (criterio.probar(actual.getT())) {
                return actual.getT();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    public int getSize(){
        int contador = 0;
        for(Nodo<T> nodo = cabeza;nodo != null; nodo = nodo.getSiguiente()){
            contador++;
        }
        return contador;
    }

    public boolean isEmpty() {
        return cabeza == null;
    }

    public void forEach(Consumer<T> accion) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            accion.accept(actual.getT());
            actual = actual.getSiguiente();
        }
    }

    public boolean existe(Criterio<T> criterio) {
        return buscar(criterio) != null;
    }

    public boolean eliminar(Criterio<T> criterio) {
        if (cabeza == null || criterio == null) {
            return false;
        }

        if (criterio.probar(cabeza.getT())) {
            cabeza = cabeza.getSiguiente();
            return true;
        }

        Nodo<T> anterior = cabeza;
        Nodo<T> actual = cabeza.getSiguiente();
        while (actual != null) {
            if (criterio.probar(actual.getT())) {
                anterior.setSiguiente(actual.getSiguiente());
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public boolean actualizar(Criterio<T> criterio, Consumer<T> modificador) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (criterio.probar(actual.getT())) {
                modificador.accept(actual.getT());
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

}
