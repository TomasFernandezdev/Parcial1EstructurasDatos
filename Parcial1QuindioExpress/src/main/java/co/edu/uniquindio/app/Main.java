package co.edu.uniquindio.app;

import co.edu.uniquindio.model.DivideVenceras;
import co.edu.uniquindio.model.ListaEnlazada;

public class Main {
    public static void main(String[] args) {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        DivideVenceras dv  = new DivideVenceras();
        System.out.println("---PRUEBAS CON DIVIDE Y VENCERAS---");

        ListaEnlazada<Double> lista2 = new ListaEnlazada<>();
        lista2.addLast(11.3);
        lista2.addLast(4.5);
        lista2.addLast(18.7);
        lista2.addLast(5.6);
        lista2.addLast(9.8);

        double mayor = dv.buscarMayorPeso(lista2);
        System.out.println("El mayor peso en la lista es: " + mayor);



        System.out.println("---PRUEBAS DE LISTAS ENLAZADAS---");
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