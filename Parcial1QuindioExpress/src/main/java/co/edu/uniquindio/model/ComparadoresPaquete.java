package co.edu.uniquindio.model;

import java.util.Comparator;

public class ComparadoresPaquete {

    private ComparadoresPaquete() {}

    // Por código (equivale al orden natural, pero como Comparator explícito)
    public static final Comparator<Paquete> POR_CODIGO =
            Comparator.comparing(Paquete::getCodigo);

    //para atender paqwuetes en el orden de prioridad, tiempo estimado y codigo
    public static final Comparator<Paquete> POR_PRIORIDAD_TIEMPO_CODIGO =
            Comparator.comparingInt(Paquete::getPrioridad).reversed()
                    .thenComparingInt(Paquete::getTiempoEstimado)
                    .thenComparing(Paquete::getCodigo);
    //prioridad
    public static final Comparator<Paquete> POR_PRIORIDAD =
            Comparator.comparingInt(Paquete::getPrioridad).reversed();
    //peso
    public static final Comparator<Paquete> POR_PESO =
            Comparator.comparingDouble(Paquete::getPeso).reversed();
    //Tiempo
    public static final Comparator<Paquete> POR_TIEMPO =
            Comparator.comparingInt(Paquete::getTiempoEstimado);
}