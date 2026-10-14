package co.edu.uniquindio.model;

import java.util.List;

public class Distribuidora {
    private String nombre;
    private String nit;
    private DataBase dataBase;

    public Distribuidora(String nombre, String nit) {
        this.nombre = "QuindíoExpress";
        this.nit = "112121212";
        this.dataBase = DataBase.getInstance();
    }

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void registrarPaquete(Paquete paquete) {
        this.dataBase.añadirPaquete(paquete);
    }

    public void registrarEntrega(Paquete paquete) {
        if (paquete.getRepartidorAsignado() != null) {
            paquete.getRepartidorAsignado().setEstaDisponible(true);
        }
        paquete.setEntregado(true);
        dataBase.paquetes.remove(paquete);
        if (dataBase.paquetesPorMunicipio.containsKey(paquete.getDestino())) {
            dataBase.paquetesPorMunicipio.get(paquete.getDestino()).remove(paquete);
        }
        this.dataBase.historialPaquetes.addLast(paquete);
    }

    public void mostrarHistorialPaquetes() {
        dataBase.historialPaquetes.mostrarHistorial();
    }

    public String asignarRepartidor(Paquete paquete) {
        if (dataBase.repartidores.isEmpty()) {
            return "No hay lista de repartidores registrada.";
        }
        Repartidor disponible = dataBase.repartidores.buscar(r -> r.isEstaDisponible());

        if (disponible.isEstaDisponible() && paquete.getDestino().equals(disponible.getZona())) {
            disponible.setEstaDisponible(false);
            paquete.setRepartidorAsignado(disponible);
            return "Paquete " + paquete.getCodigo() + " asignado exitosamente a " + disponible.getNombre();
        }

        return "No hay repartidores disponibles en este momento.";
    }

    public double calcularPesoPorMunicipio(Municipio municipio) {
        return dataBase.calcularPesoPorMunicipio(municipio);
    }

    public List<Paquete> obtenerPaquetesPrioridadMayor(int prioridadMinima) {
        return dataBase.obtenerPaquetesPrioridadMayor(prioridadMinima);
    }

    public Paquete atenderPaquete() {
        return dataBase.atenderPaquete();
    }

    public List<Paquete> ordenarPorPrioridad() {
        return dataBase.ordenarporPrioridad();
    }

    public List<Paquete> ordenarPorPeso() {
        return dataBase.ordenarporPeso();
    }

    public List<Paquete> ordenarPorTiempo() {
        return dataBase.ordenarporTiempo();
    }

    public double sumarPesoMunicipioDivideVenceras(Municipio municipio) {
        DivideVenceras dv = new DivideVenceras();
        return dv.sumarPesoRecursivo(municipio, 0, 0);
    }

    public Paquete buscarPaquetePorCodigoDivideVenceras(String codigo) {
        return new DivideVenceras().buscarPaquetePorCodigo(codigo);
    }

    public Paquete buscarPaqueteMasPesadoDivideVenceras() {
        return new DivideVenceras().buscarPaqueteMasPesado();
    }

    public Paquete[] paquetesPrioridadMayorDivideVenceras(int limite) {
        return new DivideVenceras().determinarPrioridadMayor(limite);
    }

    public List<Paquete> paquetesPorMunicipio(Municipio municipio) {
        if (!dataBase.paquetesPorMunicipio.containsKey(municipio)) {
            return List.of();
        }
        return List.copyOf(dataBase.paquetesPorMunicipio.get(municipio));
    }

    // CRUD REPARTIDOR
    public boolean createRepartidor(Repartidor repartidor) {
        if (repartidor == null || repartidor.getIdentificacion() == null) {
            return false;
        }

        if (dataBase.repartidores.existe(r -> r.getIdentificacion().equals(repartidor.getIdentificacion()))) {
            return false;
        }

        dataBase.repartidores.addLast(repartidor);
        return true;
    }

    public boolean readRepartidor(String identificacion) {
        if (identificacion == null) {
            return false;
        }

        Repartidor repartidor = dataBase.repartidores.buscar(r -> r.getIdentificacion().equals(identificacion));
        if (repartidor == null) {
            return false;
        }

        imprimirRepartidor(repartidor);
        return true;
    }

    public boolean readRepartidor() {
        if (dataBase.repartidores.isEmpty()) {
            return false;
        }

        dataBase.repartidores.forEach(this::imprimirRepartidorConSeparador);
        return true;
    }

    private void imprimirRepartidor(Repartidor repartidor) {
        System.out.println("Identificación: " + repartidor.getIdentificacion());
        System.out.println("Nombre: " + repartidor.getNombre());
        System.out.println("Zona: " + repartidor.getZona());
        System.out.println("Disponible: " + repartidor.isEstaDisponible());
    }

    private void imprimirRepartidorConSeparador(Repartidor repartidor) {
        imprimirRepartidor(repartidor);
        System.out.println("-----------------------------");
    }

    public boolean updateRepartidor(Repartidor repartidor) {
        if (repartidor == null || repartidor.getIdentificacion() == null) {
            return false;
        }

        return dataBase.repartidores.actualizar(
                r -> r.getIdentificacion().equals(repartidor.getIdentificacion()),
                actual -> {
                    actual.setNombre(repartidor.getNombre());
                    actual.setZona(repartidor.getZona());
                    actual.setEstaDisponible(repartidor.isEstaDisponible());
                }
        );
    }

    public boolean deleteRepartidor(String identificacion) {
        if (identificacion == null) {
            return false;
        }
        return dataBase.repartidores.eliminar(r -> r.getIdentificacion().equals(identificacion));
    }

    // CRUD PAQUETES
    public boolean createPaquete(Paquete paquete) {
        if (paquete == null || paquete.getCodigo() == null) {
            return false;
        }

        if (dataBase.paquetes.contains(paquete)) {
            return false;
        }

        dataBase.añadirPaquete(paquete);
        return true;
    }

    public boolean readPaquete(String codigo) {
        if (codigo == null) {
            return false;
        }

        for (Paquete paquete : dataBase.paquetes) {
            if (paquete.getCodigo().equals(codigo)) {
                imprimirPaquete(paquete);
                return true;
            }
        }

        return false;
    }

    public boolean readPaquete() {
        if (dataBase.paquetes.isEmpty()) {
            return false;
        }

        for (Paquete paquete : dataBase.paquetes) {
            imprimirPaquete(paquete);
            System.out.println("-----------------------------");
        }

        return true;
    }

    private void imprimirPaquete(Paquete paquete) {
        System.out.println("Código: " + paquete.getCodigo());
        System.out.println("Destino: " + paquete.getDestino());
        System.out.println("Peso: " + paquete.getPeso());
        System.out.println("Prioridad: " + paquete.getPrioridad());
        System.out.println("Tiempo estimado: " + paquete.getTiempoEstimado());
        System.out.println("Repartidor asignado: " + paquete.getRepartidorAsignado());
        System.out.println("Entregado: " + paquete.isEntregado());
    }

    public boolean updatePaquete(Paquete paquete) {
        if (paquete == null || paquete.getCodigo() == null) {
            return false;
        }

        Paquete paqueteActual = null;

        for (Paquete p : dataBase.paquetes) {
            if (p.getCodigo().equals(paquete.getCodigo())) {
                paqueteActual = p;
                break;
            }
        }

        if (paqueteActual == null) {
            return false;
        }

        deletePaquete(paqueteActual.getCodigo());
        dataBase.añadirPaquete(paquete);
        return true;
    }

    public boolean deletePaquete(String codigo) {
        if (codigo == null || dataBase.paquetes.isEmpty()) {
            return false;
        }

        Paquete paqueteEliminar = null;

        for (Paquete paquete : dataBase.paquetes) {
            if (paquete.getCodigo().equals(codigo)) {
                paqueteEliminar = paquete;
                break;
            }
        }

        if (paqueteEliminar == null) {
            return false;
        }

        dataBase.paquetes.remove(paqueteEliminar);

        if (dataBase.paquetesPorMunicipio.containsKey(paqueteEliminar.getDestino())) {
            dataBase.paquetesPorMunicipio.get(paqueteEliminar.getDestino()).remove(paqueteEliminar);
        }

        return true;
    }

    public Paquete buscarPaqueteEnCola(String codigo) {
        for (Paquete paquete : dataBase.paquetes) {
            if (paquete.getCodigo().equals(codigo)) {
                return paquete;
            }
        }
        return null;
    }
}
