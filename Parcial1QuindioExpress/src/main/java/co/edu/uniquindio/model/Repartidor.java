package co.edu.uniquindio.model;

public class Repartidor {
    private String identificacion;
    private String nombre;
    private Municipio zona;
    private boolean estaDisponible;

    public Repartidor(String identificacion, String nombre, Municipio zona, boolean estaDisponible) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.zona = zona;
        this.estaDisponible = estaDisponible;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Municipio getZona() {
        return zona;
    }

    public void setZona(Municipio zona) {
        this.zona = zona;
    }

    public boolean isEstaDisponible() {
        return estaDisponible;
    }

    public void setEstaDisponible(boolean estaDisponible) {
        this.estaDisponible = estaDisponible;
    }
}
