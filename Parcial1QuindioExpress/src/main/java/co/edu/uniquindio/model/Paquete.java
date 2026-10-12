package co.edu.uniquindio.model;

import java.util.Objects;

public class Paquete implements Comparable<Paquete>{
    private String codigo;
    private Municipio destino;
    private double peso;
    private int prioridad;
    private int tiempoEstimado; //numero de minutos estimados para la entrega
    private Repartidor repartidorAsignado;
    private boolean entregado;

    public Paquete(String codigo, Municipio destino, double peso, int prioridad, int tiempoEstimado, Repartidor repartidorAsignado) {
        this.codigo = codigo;
        this.destino = destino;
        this.peso = peso;
        this.prioridad = prioridad;
        this.tiempoEstimado = tiempoEstimado;
        this.repartidorAsignado = repartidorAsignado;
        this.entregado = false;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Municipio getDestino() {
        return destino;
    }

    public void setDestino(Municipio destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public Repartidor getRepartidorAsignado() {
        return repartidorAsignado;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public void setRepartidorAsignado(Repartidor repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }
    @Override
    public int compareTo(Paquete o) {
        return this.codigo.compareTo(o.codigo);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paquete paquete = (Paquete) o;
        return codigo.equals(paquete.codigo);
    }
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return this.getCodigo();
    }
}
