package co.edu.uniquindio.model;

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
        }else{
            for(Nodo<T> actual = cabeza; actual.getSiguiente() != null; actual = actual.getSiguiente()){
                if(actual.getSiguiente().getSiguiente() == null){
                    actual.setSiguiente(null);
                    break;
                }
            }
        }
    }
    public Nodo<T> getFirst(){
        return cabeza;
    }
    public Nodo<T> getLast(){
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
        int contador = 0;
        Nodo<T> aux = null;
        for(Nodo<T> nodo = cabeza;nodo != null; nodo = nodo.getSiguiente()){
            if(contador == index){
                aux.setSiguiente(nodo.getSiguiente());
                break;
            }
            aux = nodo;
            contador++;
        }
    }
    public void print(){
        for(Nodo<T> nodo = cabeza;nodo != null; nodo = nodo.getSiguiente()){
            System.out.println(nodo.getT());
        }
    }
}
