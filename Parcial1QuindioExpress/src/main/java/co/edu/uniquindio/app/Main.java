package co.edu.uniquindio.app;

import co.edu.uniquindio.model.ListaEnlazada;

public class Main {
    public static void main(String[] args) {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.addLast(1);
        lista.addLast(2);
        lista.addLast(3);
        lista.addLast(4);
        lista.addLast(5);
        lista.print();
        System.out.println(lista.findByIndex(2).getT());
        lista.removeByIndex(2);
        lista.print();
        System.out.println(lista.findByIndex(2).getT());
    }
}