package co.edu.uniquindio.model;

import java.util.*;

public class DataBase {
    public Set<Paquete> paquetes;
    public Map<Municipio, Set<Paquete>> paquetesPorMunicipio;
    public ListaEnlazada<Paquete> historialPaquetes;
    public ListaEnlazada<Repartidor> repartidores;
    public static DataBase instance;

    private boolean datosInicializados;

    private DataBase() {
        this.paquetes = new TreeSet<>(ComparadoresPaquete.POR_CODIGO);
        this.paquetesPorMunicipio = new LinkedHashMap<>();
        this.historialPaquetes = new ListaEnlazada<>();
        this.repartidores = new ListaEnlazada<>();
        cargarDatosIniciales();
    }
    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public void añadirPaquete(Paquete paquete) {
        paquetes.add(paquete);
        paquetesPorMunicipio
                .computeIfAbsent(paquete.getDestino(), k -> new LinkedHashSet<>())
                .add(paquete);
    }

    private void cargarDatosIniciales() {
        if (datosInicializados) {
            return;
        }
        datosInicializados = true;

        Repartidor r1 = new Repartidor("1001", "Camila Ríos", Municipio.ARMENIA);
        Repartidor r2 = new Repartidor("1002", "Juan Pérez", Municipio.CALARCA);
        Repartidor r3 = new Repartidor("1003", "Laura Gómez", Municipio.SALENTO);
        Repartidor r4 = new Repartidor("1004", "Diego Muñoz", Municipio.MONTENEGRO);
        Repartidor r5 = new Repartidor("1005", "Sofía Ortiz", Municipio.QUIMBAYA);
        Repartidor r6 = new Repartidor("1006", "Andrés Mejía", Municipio.CIRCASIA);
        Repartidor r7 = new Repartidor("1007", "Valentina Ruiz", Municipio.PIJAO);

        repartidores.addLast(r1);
        repartidores.addLast(r2);
        repartidores.addLast(r3);
        repartidores.addLast(r4);
        repartidores.addLast(r5);
        repartidores.addLast(r6);
        repartidores.addLast(r7);

        Object[][] datosPaquetes = {
                {"QX-104", Municipio.ARMENIA, 2.5, 1, 45, null},
                {"QX-207", Municipio.CALARCA, 8.0, 3, 60, null},
                {"QX-019", Municipio.SALENTO, 1.2, 5, 90, null},
                {"QX-333", Municipio.ARMENIA, 15.0, 2, 30, null},
                {"QX-088", Municipio.QUIMBAYA, 4.4, 4, 75, null},
                {"QX-501", Municipio.MONTENEGRO, 6.7, 1, 50, null},
                {"QX-022", Municipio.CIRCASIA, 3.1, 2, 40, null},
                {"QX-910", Municipio.ARMENIA, 9.9, 3, 55, null},
                {"QX-045", Municipio.PIJAO, 0.8, 5, 120, null},
                {"QX-776", Municipio.CALARCA, 12.0, 2, 65, null},
                {"QX-131", Municipio.SALENTO, 5.5, 4, 80, null},
                {"QX-602", Municipio.ARMENIA, 7.3, 1, 35, null},
                {"QX-289", Municipio.QUIMBAYA, 2.0, 3, 70, null},
                {"QX-014", Municipio.MONTENEGRO, 18.5, 2, 95, null},
                {"QX-845", Municipio.CIRCASIA, 4.0, 5, 45, null},
                {"QX-367", Municipio.ARMENIA, 6.1, 4, 50, null},
                {"QX-058", Municipio.CALARCA, 11.2, 1, 60, null},
                {"QX-993", Municipio.SALENTO, 3.8, 2, 85, null},
                {"QX-421", Municipio.PIJAO, 2.2, 3, 100, null},
                {"QX-156", Municipio.QUIMBAYA, 9.0, 5, 40, null},
                {"QX-702", Municipio.MONTENEGRO, 5.0, 1, 55, null},
                {"QX-033", Municipio.ARMENIA, 14.4, 2, 38, null},
                {"QX-888", Municipio.CIRCASIA, 1.5, 4, 42, null},
                {"QX-244", Municipio.CALARCA, 7.7, 3, 68, null},
                {"QX-579", Municipio.SALENTO, 10.1, 1, 88, null},
                {"QX-061", Municipio.QUIMBAYA, 3.3, 2, 72, null},
                {"QX-390", Municipio.ARMENIA, 4.9, 5, 33, null},
                {"QX-812", Municipio.MONTENEGRO, 8.8, 3, 58, null},
                {"QX-127", Municipio.PIJAO, 6.6, 4, 110, null},
                {"QX-955", Municipio.CIRCASIA, 13.3, 1, 48, null},
        };

        for (Object[] fila : datosPaquetes) {
            Paquete paquete = new Paquete(
                    (String) fila[0],
                    (Municipio) fila[1],
                    (Double) fila[2],
                    (Integer) fila[3],
                    (Integer) fila[4],
                    (Repartidor) fila[5]
            );
            añadirPaquete(paquete);
        }
    }
    public double calcularPesoPorMunicipio(Municipio municipio){
        Set<Paquete> delMunicipio = paquetesPorMunicipio.get(municipio);
        if (delMunicipio == null || delMunicipio.isEmpty()) {
            return 0;
        }
        double suma = 0.f;
        for (Paquete p : delMunicipio) {
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