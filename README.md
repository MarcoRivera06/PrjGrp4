# Simulador de compra-venta de acciones en tiempo real

## 📈 Simulador de Inversión en Acciones y Criptomonedas

Un simulador financiero interactivo desarrollado en **Java SE** que modela el comportamiento en tiempo real de un mercado de valores de renta variable y criptoactivos. El sistema opera mediante una interfaz de consola (CLI) optimizada, utilizando hilos asíncronos para simular la volatilidad financiera y aplicando conceptos avanzados de Programación Orientada a Objetos (POO), y persistencia de datos.

---

## 🚀 Características Principales y Mejoras Recientes

- **Consolidación del Mercado Global:** Se ha centralizado la estructura eliminando la redundancia de perfiles de usuario múltiples para enfocar la experiencia en una sesión interactiva unificada y persistente, facilitando un control directo sobre el flujo del portafolio.
- **Simulación Asíncrona en Tiempo Real:** Fluctuación automática de precios de activos cada 30 segundos utilizando un pool de hilos programados (`ScheduledExecutorService`), lo que mantiene la consola libre y receptiva para el ingreso de comandos sin bloquear el hilo principal de ejecución.
- **Órdenes de Trading Dinámicas:** Capacidad para ejecutar transacciones en un solo comando mediante la segmentación y análisis sintáctico de texto (ej: `ct` para comprar unidades de Tesla o `vz` para vender unidades de Amazon).
- **Guardado de datos:** Persistencia de la economía del juego a través de la serialización en archivos de texto planos (`mercado.txt` y persistencia de sesión).

---

## 🛠️ Tecnologías y Librerías Utilizadas

El proyecto se fundamenta exclusivamente en la API estándar de Java (**JDK**), garantizando portabilidad y eficiencia sin dependencias externas:

- **`java.util.Map` & `java.util.HashMap`:** Utilizados como el motor de base de datos en memoria para el indexado y la búsqueda de activos financieros mediante sus *tickers* con una complejidad temporal de constante de $O(1)$.
- **`java.util.concurrent` (`ScheduledExecutorService`, `TimeUnit`):** Implementación de multitarea asíncrona distribuyendo la simulación del mercado en hilos de fondo independientes del hilo de lectura de consola.
- **`java.io` (`BufferedReader`, `PrintWriter`, `File`):** Flujos de entrada/salida (I/O) encargados de la persistencia física del estado económico del portafolio y las acciones del usuario en el disco local.
- **`java.util.Random` & `java.util.Scanner`:** Generación matemática de variaciones pseudoaleatorias para los activos financieros y captura síncrona de datos ingresados por consola.

---

## 📁 Arquitectura del Código y Flujo Estructural

El núcleo del simulador se consolida en estructuras modulares de alta cohesión que gestionan el ciclo de vida del programa:

1. **Clase Principal (`SimuladorCompleto`):** Controla el bucle de la aplicación (`do-while`), el menú de inicio, el parseo de comandos mediante técnicas de *splitting* (`.split("\\s+")`), y coordina los servicios de lectura/escritura de archivos.
2. **Clase Anidada / Molde (`Activo`):** Representa el modelo de datos de cada entidad financiera. Encapsula las 5 variables esenciales de estado:
   - `nombre` (Identificador formal)
   - `precio` (Valor nominal fluctuante)
   - `porcentajeCambio` (Diferencial de rendimiento en la última iteración)
   - `cantidad` (Unidades en posesión del usuario actual)
   - `volatilidad` (Coeficiente de variación matemática para el algoritmo de riesgo)

---

## 💻 Manual de Operación (Interfaz de Comandos)

Al iniciar el sistema y verificar usuario y contraseña se abrirá la terminal de trading en vivo que acepta los siguientes patrones sintácticos:

| Comando | Acción Ejecutada | Ejemplo Práctico |
| :--- | :--- | :--- |
| **`c<ticker>`** | Compra la unidad especificada del activo (se asume 1). | `ct` *(Compra acciones de Tesla)* |
| **`v<ticker>`** | Vende la unidad especificada del activo (se asume 1). | `vb` *(Vende Bitcoin)* |
| **`p`** | Despliega el **Reporte Integral de Portafolio** (Cálculo de Cash, activos y Patrimonio Neto). | `p` *(ver acciones y saldo)* |
| **`s`** | Cierra el mercado, sincroniza y guarda todos los datos en el almacenamiento local. | `s` *(salir al menu)* |

---

## ⚙️ Requisitos e Instalación

1. **Requisitos de Sistema:** Java Development Kit (**JDK 17** o superior) instalado y configurado en las variables de entorno.
2. **Compilación:**
