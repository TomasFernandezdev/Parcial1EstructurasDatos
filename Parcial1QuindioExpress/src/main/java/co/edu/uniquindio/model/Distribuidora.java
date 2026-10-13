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
    //CRUD REPARTIDOR
    public boolean createRepartidor(Repartidor repartidor) {
        if (repartidor == null || repartidor.getIdentificacion() == null) {
            return false;   
        }

        for (Nodo<Repartidor> actual = dataBase.repartidores.cabeza; actual != null; actual = actual.siguiente) {
            if (actual.getT().getIdentificacion().equals(repartidor.getIdentificacion())) {
                return false;
            }
        }

        dataBase.repartidores.addLast(repartidor);
        return true;
    }

    public boolean readRepartidor(String identificacion) {
        if (identificacion == null) {
            return false;
        }

        for (Nodo<Repartidor> actual = dataBase.repartidores.cabeza; actual != null; actual = actual.siguiente) {
            Repartidor repartidor = actual.getT();

            if (repartidor.getIdentificacion().equals(identificacion)) {
                System.out.println("Identificación: " + repartidor.getIdentificacion());
                System.out.println("Nombre: " + repartidor.getNombre());
                System.out.println("Zona: " + repartidor.getZona());
                System.out.println("Disponible: " + repartidor.isEstaDisponible());
                return true;
            }
        }

        return false;
    }

    public boolean readRepartidor() {
        if (dataBase.repartidores == null || dataBase.repartidores.cabeza == null) {
            return false;
        }

        for (Nodo<Repartidor> actual = dataBase.repartidores.cabeza; actual != null; actual = actual.siguiente) {
            Repartidor repartidor = actual.getT();

            System.out.println("Identificación: " + repartidor.getIdentificacion());
            System.out.println("Nombre: " + repartidor.getNombre());
            System.out.println("Zona: " + repartidor.getZona());
            System.out.println("Disponible: " + repartidor.isEstaDisponible());
            System.out.println("-----------------------------");
        }

        return true;
    }

    public boolean updateRepartidor(Repartidor repartidor) {
        if (repartidor == null || repartidor.getIdentificacion() == null) {
            return false;
        }

        for (Nodo<Repartidor> actual = dataBase.repartidores.cabeza; actual != null; actual = actual.siguiente) {
            Repartidor repartidorActual = actual.getT();

            if (repartidorActual.getIdentificacion().equals(repartidor.getIdentificacion())) {
                repartidorActual.setNombre(repartidor.getNombre());
                repartidorActual.setZona(repartidor.getZona());
                repartidorActual.setEstaDisponible(repartidor.isEstaDisponible());
                return true;
            }
        }

        return false;
    }

    public boolean deleteRepartidor(String identificacion) {
        if (identificacion == null || dataBase.repartidores == null || dataBase.repartidores.cabeza == null) {
            return false;
        }

        Nodo<Repartidor> actual = dataBase.repartidores.cabeza;
        Nodo<Repartidor> anterior = null;

        while (actual != null) {
            if (actual.getT().getIdentificacion().equals(identificacion)) {
                if (anterior == null) {
                    dataBase.repartidores.cabeza = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }

                return true;
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        return false;
    }
    
    //CRUD PAQUETES
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
                System.out.println("Código: " + paquete.getCodigo());
                System.out.println("Destino: " + paquete.getDestino());
                System.out.println("Peso: " + paquete.getPeso());
                System.out.println("Prioridad: " + paquete.getPrioridad());
                System.out.println("Tiempo estimado: " + paquete.getTiempoEstimado());
                System.out.println("Repartidor asignado: " + paquete.getRepartidorAsignado());
                System.out.println("Entregado: " + paquete.isEntregado());
                return true;
            }
        }

        return false;
    }

    public boolean readPaquete() {
        if (dataBase.paquetes == null || dataBase.paquetes.isEmpty()) {
            return false;
        }

        for (Paquete paquete : dataBase.paquetes) {
            System.out.println("Código: " + paquete.getCodigo());
            System.out.println("Destino: " + paquete.getDestino());
            System.out.println("Peso: " + paquete.getPeso());
            System.out.println("Prioridad: " + paquete.getPrioridad());
            System.out.println("Tiempo estimado: " + paquete.getTiempoEstimado());
            System.out.println("Repartidor asignado: " + paquete.getRepartidorAsignado());
            System.out.println("Entregado: " + paquete.isEntregado());
            System.out.println("-----------------------------");
        }

        return true;
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
        if (codigo == null || dataBase.paquetes == null || dataBase.paquetes.isEmpty()) {
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
}
