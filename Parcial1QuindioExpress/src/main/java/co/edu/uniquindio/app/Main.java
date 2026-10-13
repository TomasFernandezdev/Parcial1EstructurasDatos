package co.edu.uniquindio.app;

import co.edu.uniquindio.model.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Distribuidora distribuidora;

    public static void main(String[] args) {
        distribuidora = new Distribuidora("QuindíoExpress", "112121212");

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║      QUINDÍO EXPRESS         ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("Paquetes en cola : " + distribuidora.getDataBase().paquetes.size());
        System.out.println("Repartidores     : " + distribuidora.getDataBase().repartidores.getSize());

        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> menuRepartidores();
                case 2 -> menuPaquetes();
                case 3 -> menuOperacionesNegocio();
                case 0 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
        System.out.println("Hasta pronto.");
    }

    // ──────────────────────────────────────────────
    //  MENÚ PRINCIPAL
    // ──────────────────────────────────────────────
    private static void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("─── MENÚ PRINCIPAL ───────────────");
        System.out.println(" 1. Gestión de repartidores");
        System.out.println(" 2. Gestión de paquetes");
        System.out.println(" 3. Operaciones de negocio");
        System.out.println(" 0. Salir");
        System.out.println("──────────────────────────────────");
    }

    // ──────────────────────────────────────────────
    //  REPARTIDORES
    // ──────────────────────────────────────────────
    private static void menuRepartidores() {
        boolean volver = false;
        while (!volver) {
            System.out.println();
            System.out.println("─── REPARTIDORES ─────────────────");
            System.out.println(" 1. Registrar repartidor");
            System.out.println(" 2. Listar todos");
            System.out.println(" 3. Consultar por identificación");
            System.out.println(" 4. Actualizar repartidor");
            System.out.println(" 5. Eliminar repartidor");
            System.out.println(" 0. Volver");
            System.out.println("──────────────────────────────────");
            int op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> {
                    Repartidor r = leerRepartidorNuevo();
                    System.out.println(distribuidora.createRepartidor(r)
                            ? "✓ Repartidor registrado."
                            : "✗ Ya existe un repartidor con esa identificación.");
                }
                case 2 -> {
                    if (!distribuidora.readRepartidor()) {
                        System.out.println("No hay repartidores registrados.");
                    }
                }
                case 3 -> {
                    String id = leerTexto("Identificación: ");
                    if (!distribuidora.readRepartidor(id)) {
                        System.out.println("Repartidor no encontrado.");
                    }
                }
                case 4 -> {
                    String id = leerTexto("Identificación a actualizar: ");
                    if (!distribuidora.readRepartidor(id)) {
                        System.out.println("Repartidor no encontrado.");
                        break;
                    }
                    System.out.println("Ingrese los nuevos datos:");
                    String nombre = leerTexto("Nombre: ");
                    Municipio zona = leerMunicipio();
                    boolean disponible = leerSiNo("¿Disponible? (s/n): ");
                    Repartidor actualizado = new Repartidor(id, nombre, zona);
                    actualizado.setEstaDisponible(disponible);
                    System.out.println(distribuidora.updateRepartidor(actualizado)
                            ? "✓ Repartidor actualizado."
                            : "✗ Error al actualizar.");
                }
                case 5 -> {
                    String id = leerTexto("Identificación a eliminar: ");
                    System.out.println(distribuidora.deleteRepartidor(id)
                            ? "✓ Repartidor eliminado."
                            : "✗ Repartidor no encontrado.");
                }
                case 0 -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    // ──────────────────────────────────────────────
    //  PAQUETES
    // ──────────────────────────────────────────────
    private static void menuPaquetes() {
        boolean volver = false;
        while (!volver) {
            System.out.println();
            System.out.println("─── PAQUETES ─────────────────────");
            System.out.println(" 1. Registrar paquete");
            System.out.println(" 2. Listar paquetes en cola");
            System.out.println(" 3. Consultar por código");
            System.out.println(" 4. Actualizar paquete");
            System.out.println(" 5. Eliminar paquete");
            System.out.println(" 0. Volver");
            System.out.println("──────────────────────────────────");
            int op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> {
                    Paquete p = leerPaqueteNuevo(null);
                    System.out.println(distribuidora.createPaquete(p)
                            ? "✓ Paquete registrado."
                            : "✗ Ya existe un paquete con ese código.");
                }
                case 2 -> {
                    if (!distribuidora.readPaquete()) {
                        System.out.println("No hay paquetes en cola.");
                    }
                }
                case 3 -> {
                    String codigo = leerTexto("Código: ");
                    if (!distribuidora.readPaquete(codigo)) {
                        System.out.println("Paquete no encontrado.");
                    }
                }
                case 4 -> {
                    String codigo = leerTexto("Código a actualizar: ");
                    Paquete anterior = distribuidora.buscarPaqueteEnCola(codigo);
                    if (anterior == null) {
                        System.out.println("Paquete no encontrado en cola.");
                        break;
                    }
                    Paquete actualizado = leerPaqueteNuevo(codigo);
                    actualizado.setRepartidorAsignado(anterior.getRepartidorAsignado());
                    System.out.println(distribuidora.updatePaquete(actualizado)
                            ? "✓ Paquete actualizado."
                            : "✗ Error al actualizar.");
                }
                case 5 -> {
                    String codigo = leerTexto("Código a eliminar: ");
                    System.out.println(distribuidora.deletePaquete(codigo)
                            ? "✓ Paquete eliminado."
                            : "✗ Paquete no encontrado.");
                }
                case 0 -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    // ──────────────────────────────────────────────
    //  OPERACIONES DE NEGOCIO
    // ──────────────────────────────────────────────
    private static void menuOperacionesNegocio() {
        boolean volver = false;
        while (!volver) {
            System.out.println();
            System.out.println("─── OPERACIONES DE NEGOCIO ───────");
            System.out.println(" 1. Asignar repartidor a paquete");
            System.out.println(" 2. Registrar entrega");
            System.out.println(" 3. Ver historial de entregas");
            System.out.println(" 4. Atender siguiente paquete (por prioridad)");
            System.out.println(" 5. Peso total por municipio");
            System.out.println(" 6. Paquetes con prioridad >= N");
            System.out.println(" 7. Paquetes por municipio");
            System.out.println(" 8. Ordenar por prioridad");
            System.out.println(" 9. Ordenar por peso");
            System.out.println("10. Ordenar por tiempo estimado");
            System.out.println(" 0. Volver");
            System.out.println("──────────────────────────────────");
            int op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> {
                    String codigo = leerTexto("Código del paquete: ");
                    Paquete p = distribuidora.buscarPaqueteEnCola(codigo);
                    if (p == null) {
                        System.out.println("Paquete no encontrado en cola.");
                    } else {
                        System.out.println(distribuidora.asignarRepartidor(p));
                    }
                }
                case 2 -> {
                    String codigo = leerTexto("Código del paquete entregado: ");
                    Paquete p = distribuidora.buscarPaqueteEnCola(codigo);
                    if (p == null) {
                        System.out.println("Paquete no encontrado en cola.");
                    } else {
                        distribuidora.registrarEntrega(p);
                        System.out.println("✓ Entrega registrada.");
                    }
                }
                case 3 -> distribuidora.mostrarHistorialPaquetes();
                case 4 -> {
                    Paquete p = distribuidora.atenderPaquete();
                    if (p == null) {
                        System.out.println("La cola está vacía.");
                    } else {
                        System.out.println("Siguiente a atender:");
                        System.out.println("  Código    : " + p.getCodigo());
                        System.out.println("  Destino   : " + p.getDestino());
                        System.out.println("  Prioridad : " + p.getPrioridad());
                        System.out.println("  Tiempo est: " + p.getTiempoEstimado() + " min");
                    }
                }
                case 5 -> {
                    Municipio m = leerMunicipio();
                    System.out.printf("Peso total en %s: %.2f kg%n", m, distribuidora.calcularPesoPorMunicipio(m));
                }
                case 6 -> {
                    int min = leerEntero("Prioridad mínima (1-5): ");
                    List<Paquete> lista = distribuidora.obtenerPaquetesPrioridadMayor(min);
                    if (lista.isEmpty()) {
                        System.out.println("Ningún paquete cumple el criterio.");
                    } else {
                        lista.forEach(p -> System.out.println("  " + p.getCodigo() + " → prioridad " + p.getPrioridad()));
                    }
                }
                case 7 -> {
                    Municipio m = leerMunicipio();
                    List<Paquete> lista = distribuidora.paquetesPorMunicipio(m);
                    if (lista.isEmpty()) {
                        System.out.println("Sin paquetes para " + m + ".");
                    } else {
                        lista.forEach(p -> System.out.println(
                                "  " + p.getCodigo() + " | " + p.getPeso() + " kg | prioridad " + p.getPrioridad()));
                    }
                }
                case 8  -> imprimirListaPaquetes(distribuidora.ordenarPorPrioridad(), "prioridad");
                case 9  -> imprimirListaPaquetes(distribuidora.ordenarPorPeso(), "peso");
                case 10 -> imprimirListaPaquetes(distribuidora.ordenarPorTiempo(), "tiempo estimado");
                case 0  -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    // ──────────────────────────────────────────────
    //  UTILIDADES
    // ──────────────────────────────────────────────
    private static void imprimirListaPaquetes(List<Paquete> lista, String criterio) {
        if (lista.isEmpty()) {
            System.out.println("No hay paquetes en cola.");
            return;
        }
        System.out.println("Orden por " + criterio + ":");
        for (Paquete p : lista) {
            System.out.printf("  %-8s | dest %-10s | %.1f kg | pri %d | %d min%n",
                    p.getCodigo(), p.getDestino(), p.getPeso(), p.getPrioridad(), p.getTiempoEstimado());
        }
    }

    private static Repartidor leerRepartidorNuevo() {
        String id = leerTexto("Identificación: ");
        String nombre = leerTexto("Nombre: ");
        Municipio zona = leerMunicipio();
        return new Repartidor(id, nombre, zona);
    }

    private static Paquete leerPaqueteNuevo(String codigoFijo) {
        String codigo = (codigoFijo != null) ? codigoFijo : leerTexto("Código: ");
        Municipio destino = leerMunicipio();
        double peso = leerDouble("Peso (kg): ");
        int prioridad = leerEntero("Prioridad (1-5): ");
        int tiempo = leerEntero("Tiempo estimado (min): ");
        return new Paquete(codigo, destino, peso, prioridad, tiempo, null);
    }

    private static Municipio leerMunicipio() {
        Municipio[] valores = Municipio.values();
        System.out.println("Municipios:");
        for (int i = 0; i < valores.length; i++) {
            System.out.println("  " + (i + 1) + ". " + valores[i]);
        }
        int sel = leerEntero("Seleccione municipio: ");
        if (sel < 1 || sel > valores.length) {
            System.out.println("Selección inválida, usando ARMENIA.");
            return Municipio.ARMENIA;
        }
        return valores[sel - 1];
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Ingrese un número entero: ");
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print("Ingrese un número válido: ");
        }
        double v = scanner.nextDouble();
        scanner.nextLine();
        return v;
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static boolean leerSiNo(String mensaje) {
        String r = leerTexto(mensaje).toLowerCase();
        return r.equals("s") || r.equals("si") || r.equals("y") || r.equals("yes");
    }
}
