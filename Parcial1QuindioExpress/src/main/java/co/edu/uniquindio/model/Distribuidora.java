package co.edu.uniquindio.model;

public class Distribuidora {
    private String nombre;
    private String nit;
    private DataBase dataBase; // 1. Atributo para la base de datos

    public Distribuidora(String nombre, String nit) {
        this.nombre = "QuindíoExpress";
        this.nit = "112121212";
        this.dataBase = DataBase.getInstance(); //singleton
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void registrarPaquete(Paquete paquete) {
        this.dataBase.añadirPaquete(paquete);
    }

    public void registrarEntrega(Paquete paquete) {
        paquete.getRepartidorAsignado().setEstaDisponible(true);
        paquete.setEntregado(true);
        this.dataBase.historialPaquetes.addLast(paquete);
    }

    public void mostrarHistorialPaquetes() {
        dataBase.historialPaquetes.mostrarHistorial();
    }

    public String asignarRepartidor(Paquete paquete) {
        if (this.dataBase.repartidores == null) {
            return "No hay lista de repartidores registrada.";
        }
        Repartidor disponible = this.dataBase.repartidores.buscar(new Criterio<Repartidor>() {
            @Override
            public boolean probar(Repartidor r) {
                return r.isEstaDisponible();
            }
        });

        if (disponible != null) {
            disponible.setEstaDisponible(false);
            paquete.setRepartidorAsignado(disponible);
            return "Paquete " + paquete.getCodigo() + " asignado exitosamente a " + disponible.getNombre();
        }

        return "No hay repartidores disponibles en este momento.";
    }

}

