# 📦 QuindíoExpress — Sistema de Gestión de Paquetes

> **Parcial 1 · Estructuras de Datos 2026-2**  
> Universidad del Quindío · Ingeniería de Sistemas y Computación

---

## 👥 Autores

| Nombre | Rol |
|---|---|
| **Andres Felipe Montenegro** | Desarrollador |
| **Tomas Fernandez Gomez** | Desarrollador |

---

## 📋 Descripción General

**QuindíoExpress** es un sistema de gestión de paquetes y repartidores para una empresa de mensajería que opera en los municipios del departamento del Quindío (Colombia). El proyecto fue desarrollado como parcial de la asignatura *Estructuras de Datos* y pone en práctica el diseño e implementación manual de estructuras de datos clásicas, algoritmos de ordenamiento y búsqueda recursivos, y el paradigma de programación orientada a objetos.

La aplicación corre completamente en consola (CLI) y permite gestionar el ciclo de vida completo de los paquetes: desde su registro hasta su entrega, pasando por la asignación de repartidores y la consulta de estadísticas.

---

## 🏗️ Arquitectura del Proyecto

```
Parcial1EstructurasDatos/
└── Parcial1QuindioExpress/
    ├── pom.xml
    └── src/
        └── main/
            └── java/
                └── co/edu/uniquindio/
                    ├── app/
                    │   └── Main.java              ← Punto de entrada (menú CLI)
                    └── model/
                        ├── Distribuidora.java     ← Lógica de negocio (CRUD + operaciones)
                        ├── DataBase.java          ← Singleton de datos en memoria
                        ├── Paquete.java           ← Entidad paquete
                        ├── Repartidor.java        ← Entidad repartidor
                        ├── Municipio.java         ← Enum con los 12 municipios del Quindío
                        ├── ListaEnlazada.java     ← Estructura lista enlazada genérica
                        ├── Nodo.java              ← Nodo genérico para la lista
                        ├── Criterio.java          ← Interfaz funcional para búsquedas/filtros
                        ├── ComparadoresPaquete.java ← Comparadores reutilizables
                        └── DivideVenceras.java    ← Algoritmos recursivos (Divide y Vencerás)
```

---

## 🧠 Estructuras de Datos Implementadas

### Lista Enlazada Simple Genérica (`ListaEnlazada<T>`)

Implementación manual de una lista enlazada simple, **sin utilizar** `java.util.LinkedList`. Soporta:

| Operación | Descripción |
|---|---|
| `addFirst(T)` | Inserta al inicio |
| `addLast(T)` | Inserta al final |
| `removeFirst()` | Elimina el primer elemento |
| `removeLast()` | Elimina el último elemento |
| `findByIndex(int)` | Busca por índice |
| `removeByIndex(int)` | Elimina por índice |
| `buscar(Criterio<T>)` | Búsqueda por predicado funcional |
| `existe(Criterio<T>)` | Verifica existencia por predicado |
| `eliminar(Criterio<T>)` | Elimina por predicado |
| `actualizar(Criterio<T>, Consumer<T>)` | Actualiza en sitio por predicado |
| `forEach(Consumer<T>)` | Iteración con función |
| `mostrarHistorial()` | Muestra encadenado con `->` |
| `getSize()` | Tamaño de la lista |
| `isEmpty()` | Verificación de vacío |

**Usada para:**
- La lista de **repartidores** registrados.
- El **historial de entregas** (paquetes entregados).

### TreeSet con Comparadores Personalizados

Los paquetes en cola se almacenan en un `TreeSet<Paquete>` ordenado por código (`ComparadoresPaquete.POR_CODIGO`). El sistema define múltiples comparadores reutilizables:

| Comparador | Criterio |
|---|---|
| `POR_CODIGO` | Orden alfabético por código |
| `POR_PRIORIDAD` | De mayor a menor prioridad (1=menor, 5=mayor) |
| `POR_PESO` | De mayor a menor peso |
| `POR_TIEMPO` | De menor a mayor tiempo estimado |
| `POR_PRIORIDAD_TIEMPO_CODIGO` | Compuesto: prioridad ↑, tiempo ↑, código ↑ |

### Map de Paquetes por Municipio

`Map<Municipio, Set<Paquete>>` para acceso en O(1) a todos los paquetes con destino a un municipio específico, usado en las operaciones de filtrado y cálculo de peso por zona.

---

## 🔁 Algoritmos — Divide y Vencerás (`DivideVenceras`)

Se implementaron cuatro algoritmos **recursivos** bajo el paradigma Divide y Vencerás:

| Método | Descripción |
|---|---|
| `sumarPesoRecursivo(Municipio, double, int)` | Suma recursiva del peso total de paquetes hacia un municipio |
| `determinarPrioridadMayor(int)` | Filtra recursivamente paquetes cuya prioridad sea ≥ al límite dado |
| `buscarPaquetePorCodigo(String)` | Búsqueda binaria recursiva por código |
| `buscarPaqueteMasPesado()` | Encuentra el paquete más pesado dividiendo el arreglo en mitades |

---

## ⚙️ Funcionalidades del Sistema

### 1. Gestión de Repartidores (CRUD completo)

- **Registrar** un nuevo repartidor (identificación, nombre, municipio de zona).
- **Listar** todos los repartidores registrados.
- **Consultar** un repartidor por su identificación.
- **Actualizar** los datos de un repartidor existente.
- **Eliminar** un repartidor del sistema.

### 2. Gestión de Paquetes (CRUD completo)

- **Registrar** un paquete (código, destino, peso, prioridad 1-5, tiempo estimado en minutos).
- **Listar** todos los paquetes en cola.
- **Consultar** un paquete por su código.
- **Actualizar** los datos de un paquete existente.
- **Eliminar** un paquete de la cola.

### 3. Operaciones de Negocio

| Opción | Descripción |
|---|---|
| Asignar repartidor | Asigna automáticamente el primer repartidor disponible cuya zona coincida con el destino del paquete |
| Registrar entrega | Marca un paquete como entregado, libera al repartidor y lo mueve al historial |
| Ver historial | Muestra todos los paquetes entregados encadenados |
| Atender siguiente | Atiende el paquete con mayor prioridad (y menor tiempo estimado en caso de empate) |
| Peso por municipio | Calcula el peso total de paquetes pendientes hacia un municipio |
| Paquetes por prioridad | Lista paquetes cuya prioridad sea ≥ N |
| Paquetes por municipio | Filtra paquetes por municipio de destino |
| Ordenar por prioridad | Muestra los paquetes ordenados de mayor a menor prioridad |
| Ordenar por peso | Muestra los paquetes ordenados de mayor a menor peso |
| Ordenar por tiempo | Muestra los paquetes ordenados de menor a mayor tiempo estimado |

---

## 🗺️ Municipios Cubiertos

El sistema cubre los **12 municipios del departamento del Quindío**:

| | | | |
|---|---|---|---|
| Armenia | Buenavista | Calarcá | Circasia |
| Córdoba | Filandia | Génova | La Tebaida |
| Montenegro | Pijao | Quimbaya | Salento |

---

## 🗄️ Datos Iniciales (Precargados)

Al iniciar la aplicación, el sistema carga automáticamente:

- **7 repartidores** precargados, uno por municipio principal.
- **30 paquetes** de prueba distribuidos entre los distintos municipios, con distintas prioridades, pesos y tiempos estimados.

Esto permite probar todas las funcionalidades sin necesidad de ingresar datos manualmente.

---

## 🔗 Modelo de Entidades

```
Paquete
├── codigo: String          (identificador único)
├── destino: Municipio      (municipio de entrega)
├── peso: double            (kilogramos)
├── prioridad: int          (1 = baja … 5 = alta)
├── tiempoEstimado: int     (minutos)
├── repartidorAsignado: Repartidor (puede ser null)
└── entregado: boolean

Repartidor
├── identificacion: String
├── nombre: String
├── zona: Municipio         (municipio al que atiende)
└── estaDisponible: boolean
```

---

## 🚀 Cómo Ejecutar el Proyecto

### Requisitos Previos

- **Java 23** o superior
- **Apache Maven 3.8+**

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/dressinhoMc/Parcial1EstructurasDatos.git
cd Parcial1EstructurasDatos

# 2. Compilar el proyecto
cd Parcial1QuindioExpress
mvn compile

# 3. Ejecutar
mvn exec:java -Dexec.mainClass="co.edu.uniquindio.app.Main"
```

O desde un IDE (IntelliJ IDEA, Eclipse, VS Code con extensión Java):
1. Abrir el módulo `Parcial1QuindioExpress` como proyecto Maven.
2. Ejecutar directamente la clase `Main`.

---

## 🖥️ Ejemplo de Uso

```
╔══════════════════════════════╗
║      QUINDÍO EXPRESS         ║
╚══════════════════════════════╝
Paquetes en cola : 30
Repartidores     : 7

─── MENÚ PRINCIPAL ───────────────
 1. Gestión de repartidores
 2. Gestión de paquetes
 3. Operaciones de negocio
 0. Salir
──────────────────────────────────
Seleccione una opción:
```

---

## 🛠️ Tecnologías y Herramientas

| Tecnología | Versión |
|---|---|
| Java | 23 |
| Apache Maven | 3.8+ |
| JDK | OpenJDK 23 |
| Build System | Maven (`pom.xml`) |

---

## 📚 Conceptos de Estructuras de Datos Aplicados

- ✅ **Lista enlazada simple** implementada desde cero con genéricos
- ✅ **Nodos** con referencia al siguiente elemento
- ✅ **Interfaz funcional** `Criterio<T>` para búsquedas y filtros desacoplados
- ✅ **TreeSet** con comparadores personalizados (`Comparator<Paquete>`)
- ✅ **HashMap / LinkedHashMap** para acceso rápido por municipio
- ✅ **Patrón Singleton** en la capa de datos (`DataBase`)
- ✅ **Algoritmos recursivos** con Divide y Vencerás
- ✅ **CRUD** completo sobre dos entidades del dominio
- ✅ **Comparadores múltiples** y ordenamientos con Streams

---

## 📄 Licencia

Este proyecto está bajo la licencia GPL-3.0. Ver archivo [LICENSE](./LICENSE) para más detalles.

---

*Desarrollado para la asignatura Estructuras de Datos · Semestre 2026-2 · Universidad del Quindío*