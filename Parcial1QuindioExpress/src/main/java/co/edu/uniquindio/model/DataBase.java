package co.edu.uniquindio.model;

import java.util.*;

public class DataBase {
    public Set<Paquete> paquetes;
    public Map<Municipio, Set<Paquete>> paquetesPorMunicipio;
    public ListaEnlazada<Paquete> historialPaquetes;
    public static DataBase instance;

    private DataBase() {
        this.paquetes = new TreeSet<>(Comparator.comparing(Paquete::getCodigo));
        this.paquetesPorMunicipio = new LinkedHashMap<>();
        this.historialPaquetes = new ListaEnlazada<>();
    }
    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
    public void añadirPaquete(Paquete paquete) {
        paquetes.add(paquete);
        if(paquetesPorMunicipio.containsKey(paquete.getDestino())){
            if(paquetesPorMunicipio.get(paquete.getDestino()) == null){
                paquetesPorMunicipio.replace(paquete.getDestino(), new LinkedHashSet<>());
            }
            paquetesPorMunicipio.get(paquete.getDestino()).add(paquete);
        }
    }
    public double calcularPesoPorMunicipio(Municipio municipio){
        double suma = 0.f;
        for(Paquete p : paquetesPorMunicipio.get(municipio)){
            suma += p.getPeso();
        }
        return suma;
    }
    public List<Paquete> obtenerPaquetesPrioridadMayor(int Priopridad){
        List<Paquete> aux = new LinkedList<>();
        for(Paquete p : paquetes){
            if(p.getPrioridad() >= Priopridad){
                aux.add(p);
            }
        }
        return aux;
    }


}
