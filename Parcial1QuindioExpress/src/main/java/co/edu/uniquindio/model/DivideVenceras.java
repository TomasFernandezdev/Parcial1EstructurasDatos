package co.edu.uniquindio.model;

public class DivideVenceras {

    public double buscarMayorPeso(ListaEnlazada<Double> lista) {
        if (lista == null || lista.getFirst() == null) {
            return 0;
        }
        // Calculamr el tamaño de la lista
        int tamano = 0;
        Nodo<Double> aux = lista.getFirst();
        while (aux != null) {
            tamano++;
            aux = aux.getSiguiente();
        }

        return buscarMayorRecursivo(lista, 0, tamano - 1);
    }

    private double buscarMayorRecursivo(ListaEnlazada<Double> lista, int inicio, int fin) {
        //caso base
        if (inicio == fin) {
            return lista.findByIndex(inicio).getT();
        }
        //division del problema
        int medio = inicio + (fin - inicio) / 2;
        //subptoblema izquierdo
        double mayorIzquierda = buscarMayorRecursivo(lista, inicio, medio);
        //subproblema derecho
        double mayorDerecha = buscarMayorRecursivo(lista, medio + 1, fin);
        //Retornar el mayor de los 2 lados
        return Math.max(mayorIzquierda, mayorDerecha);
    }
}
