package co.edu.uniquindio.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DataBase {
    public Map<Municipio, Set<Paquete>> paquetesPorMunicipio;
    public ListaEnlazada<Paquete> historialPaquetes;
    public static DataBase instance;

    private DataBase() {
        this.paquetesPorMunicipio = new LinkedHashMap<>();
        this.historialPaquetes = new ListaEnlazada<>();
    }
    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
}
