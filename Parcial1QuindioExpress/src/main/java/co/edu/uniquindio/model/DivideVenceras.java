package co.edu.uniquindio.model;

public class DivideVenceras {
    public Paquete[] p;

    public DivideVenceras(){
        p = DataBase.getInstance().paquetes.toArray(new Paquete[0]);
    }

    public double sumarPesoRecursivo(Municipio m, double suma, int aux){
        if (aux == 0) {
            p = DataBase.getInstance().paquetes.toArray(new Paquete[0]);
        }
        if(aux == p.length){
            return suma;
        }
        if(m.equals(p[aux].getDestino())){
            suma += p[aux].getPeso();
        }
        return sumarPesoRecursivo(m, suma, aux+1);
    }
    public Paquete[] determinarPrioridadMayor(int limite) {
        p = DataBase.getInstance().paquetes.toArray(new Paquete[0]);
        Paquete[] temporal = new Paquete[p.length];
        int[] contador = {0};
        determinarPrioridadMayorRec(0, limite, temporal, contador);
        Paquete[] resultado = new Paquete[contador[0]];
        System.arraycopy(temporal, 0, resultado, 0, contador[0]);
        return resultado;
    }

    private void determinarPrioridadMayorRec(int inicio, int limite, Paquete[] aux, int[] contador) {
        if (inicio == p.length) {
            return;
        }
        if (p[inicio].getPrioridad() >= limite) {
            aux[contador[0]++] = p[inicio];
        }
        determinarPrioridadMayorRec(inicio + 1, limite, aux, contador);
    }

    public Paquete buscarPaquetePorCodigo(String codigo) {
        p = DataBase.getInstance().paquetes.toArray(new Paquete[0]);
        return buscarPaquetePorCodigoRecursivo(codigo, 0, p.length - 1);
    }

    private Paquete buscarPaquetePorCodigoRecursivo(String codigo, int inicio, int fin) {
        if (codigo == null || inicio > fin) {
            return null;
        }

        int medio = inicio + (fin - inicio) / 2;
        Paquete paqueteMedio = p[medio];

        int comparacion = codigo.compareTo(paqueteMedio.getCodigo());

        if (comparacion == 0) {
            return paqueteMedio;
        }

        if (comparacion < 0) {
            return buscarPaquetePorCodigoRecursivo(codigo, inicio, medio - 1);
        }

        return buscarPaquetePorCodigoRecursivo(codigo, medio + 1, fin);
    }

    public Paquete buscarPaqueteMasPesado() {
        p = DataBase.getInstance().paquetes.toArray(new Paquete[0]);

        if (p.length == 0) {
            return null;
        }

        return buscarPaqueteMasPesadoRecursivo(0, p.length - 1);
    }

    private Paquete buscarPaqueteMasPesadoRecursivo(int inicio, int fin) {
        if (inicio == fin) {
            return p[inicio];
        }

        int medio = inicio + (fin - inicio) / 2;

        Paquete paqueteIzquierdo = buscarPaqueteMasPesadoRecursivo(inicio, medio);
        Paquete paqueteDerecho = buscarPaqueteMasPesadoRecursivo(medio + 1, fin);

        if (paqueteIzquierdo.getPeso() >= paqueteDerecho.getPeso()) {
            return paqueteIzquierdo;
        }

        return paqueteDerecho;
    }


}
