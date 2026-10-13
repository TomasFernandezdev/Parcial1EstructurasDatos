package co.edu.uniquindio.model;

import java.util.*;

public class DataBase {
    public Set<Paquete> paquetes;
    public Map<Municipio, Set<Paquete>> paquetesPorMunicipio;
    public ListaEnlazada<Paquete> historialPaquetes;
    public ListaEnlazada<Repartidor> repartidores;
    public static DataBase instance;

    private DataBase() {
        this.paquetes = new TreeSet<>(ComparadoresPaquete.POR_CODIGO);
        this.paquetesPorMunicipio = new LinkedHashMap<>();
        this.historialPaquetes = new ListaEnlazada<>();
        this.repartidores = new ListaEnlazada<>();
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
    public Paquete atenderPaquete(){
        return paquetes.stream().min(ComparadoresPaquete.POR_PRIORIDAD_TIEMPO_CODIGO).orElse(null);
    }
    public List<Paquete> ordenarporPrioridad(){
        return paquetes.stream().sorted(ComparadoresPaquete.POR_PRIORIDAD).toList();
    }
    public List<Paquete> ordenarporPeso(){
        return paquetes.stream().sorted(ComparadoresPaquete.POR_PESO).toList();
    }
    public List<Paquete> ordenarporTiempo(){
        return paquetes.stream().sorted(ComparadoresPaquete.POR_TIEMPO).toList();
    }

}
