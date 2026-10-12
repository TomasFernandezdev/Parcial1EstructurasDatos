package co.edu.uniquindio.model;

import java.util.Comparator;

public class ComparadoresPaquete {

    private ComparadoresPaquete() {}

    // Por código (equivale al orden natural, pero como Comparator explícito)
    public static final Comparator<Paquete> POR_CODIGO =
            Comparator.comparing(Paquete::getCodigo);

    // Por prioridad, de mayor a menor (5 primero)
    public static final Comparator<Paquete> POR_PRIORIDAD_DESC =
            Comparator.comparingInt(Paquete::getPrioridad).reversed();

    // Por peso, de mayor a menor
    public static final Comparator<Paquete> POR_PESO_DESC =
            Comparator.comparingDouble(Paquete::getPeso).reversed();

    // Por tiempo estimado, de menor a mayor
    public static final Comparator<Paquete> POR_TIEMPO_ASC =
            Comparator.comparingInt(Paquete::getTiempoEstimado);
}