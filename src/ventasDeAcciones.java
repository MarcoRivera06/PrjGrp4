
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/*Clase principal, aqui se asigna las variables que tendra cada una de las acciones
y se especifica que se hara con cada una de ellas */
public class ventasDeAcciones {

    private static class Activo {
        private final String nombre;
        private double precio;
        private int cantidad;
        private final double maxVariacion;
        private double porcentajeCambio;

        public Activo(String nombre, double precioInicial, double maxVariacion) {
            this.nombre = nombre;
            this.precio = precioInicial;
            this.maxVariacion = maxVariacion;
            this.cantidad = 0;
            this.porcentajeCambio = 0.0;
        }

        public void fluctuarPrecio(Random rand) {
            double variacion = (-maxVariacion + rand.nextDouble() * (maxVariacion * 2)) / 100.0;
            this.porcentajeCambio = variacion;
            this.precio *= (1 + variacion);
        }

        public double getPorcentajeCambio() {
            return this.porcentajeCambio;
        }
        public double getValorInvertido() {
            return this.cantidad * this.precio;
        }
        public String getNombre() {
            return nombre;
        }
        public double getPrecio() {
            return precio;
        }
        public int getCantidad() {
            return cantidad;
        }
        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }


    private String usuarioActual = "";
    private String passwordActual = "";
    private double saldoEfectivo = 10000.0;
    private int dia = 0;

/* Creacion de un "Diccionario" almacenamiento de las acciones con una estructura de datos,
un mapa para contener a las acciones con un identificador unico*/

    private final Map<String, Activo> mercado = new HashMap<>();
    private final String ARCHIVO_DB = "usuarios.txt";

    public ventasDeAcciones() {
        mercado.put("b",  new Activo("Bitcoin", 50000.0, 6.0));
        mercado.put("a", new Activo("Apple", 150.0, 3.0));
        mercado.put("z", new Activo("Amazon", 130.0, 3.0));
        mercado.put("t", new Activo("Tesla", 200.0, 5.0));
        mercado.put("g", new Activo("Google", 140.0, 2.5));
        mercado.put("m", new Activo("Microsoft", 320.0, 2.0));
    }
    
    public void iniciarSimulador() {
        String opc;
        Scanner teclado = new Scanner(System.in);
        Random rand = new Random();

        do{
            System.out.println("=================================================================");
            System.out.println("0.- Salir");
            System.out.println("1.- Inicio");
            System.out.print("Elige una opcion: ");
            opc = teclado.nextLine().trim();
            System.out.println("=================================================================");

            switch(opc){
                case "0" -> {
                    System.out.println("Saliendo del programa...");
                    return;
                }
                case "1" -> {

                    System.out.println("Inicio de sesion: ");
                    System.out.print("Ingrese su nombre de usuario: ");
                    usuarioActual = teclado.nextLine();

                    if (!autenticarUsuario(teclado)) {
                        System.out.println("\n [SEGURIDAD]: Regresando al menú principal...");
                        break;
                    }
    //ACABA AUTENTICACION
                    String opcion;
                    do{
                        System.out.println("");
                        System.out.println("0.- Salir");
                        System.out.println("1.- Iniciar simulador");
                        System.out.println("2.- Ver tu portafolio");
                        System.out.print("Eliga una opcion: ");
                        opcion = teclado.nextLine().trim();
                        switch(opcion){
                            case "0" -> {System.out.println("Saliendo al menu anterior. . ."); break;}
                            case "1" -> {
                                /*Inicio de hilo parte principal del juego, el hilo es un temporizador en segundo plano que actualiza los precios de las acciones
                                en timepo real sin necesidad de congelar el tecado*/
                                    ScheduledExecutorService reloj = Executors.newScheduledThreadPool(1);

                                    reloj.scheduleAtFixedRate(() -> {
                                        dia++;

                                        for (Activo activo : mercado.values()) {
                                            activo.fluctuarPrecio(rand);
                                        }

                                        System.out.println("\n-----------------------------------------------------------------------------");
                                        System.out.println(" [MERCADO EN VIVO - DIA " + dia + " | USUARIO: " + usuarioActual + "]");
                                        System.out.println("\n-----------------------------------------------------------------------------");
                                        System.out.printf("BTC: $%.2f (%+.2f%%)  |  AAPL: $%.2f (%+.2f%%)  |  AMZN: $%.2f (%+.2f%%)\n",
                                                mercado.get("b").getPrecio(),  mercado.get("b").getPorcentajeCambio()*100,
                                                mercado.get("a").getPrecio(), mercado.get("a").getPorcentajeCambio()*100,
                                                mercado.get("z").getPrecio(), mercado.get("z").getPorcentajeCambio()*100
                                        );

                                        System.out.printf("TSLA: $%.2f (%+.2f%%) |  GOOG: $%.2f (%+.2f%%)  |  MSFT: $%.2f (%+.2f%%)\n",
                                                mercado.get("t").getPrecio(), mercado.get("t").getPorcentajeCambio()*100,
                                                mercado.get("g").getPrecio(), mercado.get("g").getPorcentajeCambio()*100,
                                                mercado.get("m").getPrecio(), mercado.get("m").getPorcentajeCambio()*100
                                        );
                                        System.out.println("-----------------------------------------------------------------------------");
                                        System.out.print("[MERCADO] Ingresa orden (ej: ct = comprar tesla, vz = vender amazon, p = portafolio, s = salir): ");

                                    }, 5, 30, TimeUnit.SECONDS);
                                    /*Fin del hilo y comienzo de verficacion del comando que el usuario ingresa*/

                                    System.out.println("=================================================================");
                                    System.out.println(" ACCESO CONCEDIDO - SIMULADOR ACTIVO ");
                                    System.out.println("=================================================================");
                                    System.out.println("[MERCADO] comandos: ct = comprar tesla, vz = vender amazon, p = portafolio, s = salir: ");
                                    boolean activo = true;
                                    while (activo) {
                                        System.out.print("[MERCADO] Esperando orden: ");
                                        String comando = teclado.nextLine().trim().toLowerCase();

                                        if (comando.length() >= 2 && (comando.startsWith("c") || comando.startsWith("v"))) {
                                            char operacion = comando.charAt(0);
                                            String ticker = comando.substring(1).replace("-", "");

                                            if (mercado.containsKey(ticker)) {
                                                Activo seleccionado = mercado.get(ticker);

                                                if (operacion == 'c') {
                                                    if (saldoEfectivo >= seleccionado.getPrecio()) {
                                                        saldoEfectivo -= seleccionado.getPrecio();
                                                        seleccionado.setCantidad(seleccionado.getCantidad() + 1);
                                                        System.out.printf(" Compraste 1 %s a $%.2f\n", seleccionado.getNombre(), seleccionado.getPrecio());
                                                        guardarDatosUsuario();
                                                    } else {
                                                        System.out.println(" Saldo insuficiente.");
                                                    }
                                                } else {
                                                    if (seleccionado.getCantidad() > 0) {
                                                        seleccionado.setCantidad(seleccionado.getCantidad() - 1);
                                                        saldoEfectivo += seleccionado.getPrecio();
                                                        System.out.printf(" Vendiste 1 %s por $%.2f\n", seleccionado.getNombre(), seleccionado.getPrecio());
                                                        guardarDatosUsuario();
                                                    } else {
                                                        System.out.println(" No tienes este activo en tu portafolio.");
                                                    }
                                                }
                                                continue;
                                            }
                                        }

                                        switch (comando) {
                                            case "p" -> {
                                                System.out.println("\n=================================================================");
                                                System.out.println("  REPORTE INTEGRAL DE PORTAFOLIO (ANÁLISIS DE OBJETOS)");
                                                System.out.println("=================================================================");
                                                System.out.printf(" Cash Disponible: $%.2f\n", saldoEfectivo);
                                                System.out.println(" Activos en Propiedad:");

                                                double valorAcciones = 0;
                                                for (Activo act : mercado.values()) {
                                                    if (act.getCantidad() > 0) {
                                                        System.out.printf("  %s: %d unidades (Valor actual: $%.2f)\n", act.getNombre(), act.getCantidad(), act.getValorInvertido());
                                                        valorAcciones += act.getValorInvertido();
                                                    }
                                                }
                                                System.out.printf("\n  VALOR NETO DE TU PATRIMONIO: $%.2f\n", (saldoEfectivo + valorAcciones));
                                                System.out.println("=================================================================");
                                            }
                                            case "s" -> {
                                                activo = false;
                                                guardarDatosUsuario();
                                                System.out.println("\n[SISTEMA]: Datos guardados. Cerrando el mercado...");
                                            }
                                            default -> {
                                                if(!mercado.containsKey(comando.substring(Math.min(comando.length(), 1)))){
                                                    System.out.println(" Comando invalido.");
                                                }
                                            }
                                        }
                                    }
                                    reloj.shutdown();
                                
                                break;
                            }
                            case "2" -> {
                                verInformacionUsuario(teclado);
                                break;
                            }
                            default -> {
                                System.out.println("Opcion no valida");
                                break;
                            }
                    }
                }while(!opcion.equals("0"));

            }
                default -> {
                    System.out.println("Opcion no valida. Intente de nuevo");
                    break;
                }

            }
        }while(!opc.equals("0"));
    }

    /*Metodos complementarios para lectura y escritura de archivo.txt,
    para guardar el progreso del usuario asi como sus datos del portafolio */

    private boolean autenticarUsuario(Scanner teclado) {
        File archivo = new File(ARCHIVO_DB);
        if (!archivo.exists()) {
            System.out.println("[SISTEMA]: Registro inicial requerido.");
            System.out.print(" Asigne una contraseña: ");
            this.passwordActual = teclado.nextLine().trim();
            return true;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.print("Ingrese su contraseña: ");
            passwordActual = teclado.nextLine();
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos[0].equals(usuarioActual) && datos[1].equals(passwordActual) ) {
                    this.passwordActual = datos[1];
                    this.saldoEfectivo = Double.parseDouble(datos[2]);
                    mercado.get("b").setCantidad(Integer.parseInt(datos[3]));
                    mercado.get("a").setCantidad(Integer.parseInt(datos[4]));
                    mercado.get("z").setCantidad(Integer.parseInt(datos[5]));
                    mercado.get("t").setCantidad(Integer.parseInt(datos[6]));
                    mercado.get("g").setCantidad(Integer.parseInt(datos[7]));
                    mercado.get("m").setCantidad(Integer.parseInt(datos[8]));
                    return true;
                }
            }
            System.out.println("Credenciales invalidas");
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public void verInformacionUsuario(Scanner teclado) {
        File archivo = new File(ARCHIVO_DB);

        if (!archivo.exists()) {
            System.out.println("\n [SISTEMA]: No hay ningún usuario registrado todavía.");
            return;
        }

        System.out.println("\n=================================================================");

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                    System.out.println("=================================================================");
                    System.out.println("  EXTRACTO INFORMATIVO DE BASE DE DATOS");
                    System.out.println("=================================================================");
                    System.out.println(" Usuario:    " + datos[0]);
                    System.out.printf(" Saldo Cash: $%.2f\n", Double.valueOf(datos[2]));
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("Portafolio de Activos Custodiados:");
                    System.out.println("   BTC:  " + datos[3] + " unidades");
                    System.out.println("   AAPL: " + datos[4] + " unidades");
                    System.out.println("   AMZN: " + datos[5] + " unidades");
                    System.out.println("   TSLA: " + datos[6] + " unidades");
                    System.out.println("   GOOG: " + datos[7] + " unidades");
                    System.out.println("   MSFT: " + datos[8] + " unidades");
                    System.out.println("=================================================================");
            }

        } catch (IOException e) {
            System.out.println(" [ERROR]: Error al leer la base de datos: " + e.getMessage());
        }
    }

    private void guardarDatosUsuario() {
        File archivo = new File(ARCHIVO_DB);
        StringBuilder contenidoCompleto = new StringBuilder();
        boolean usuarioActualizado = false;

        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos[0].equals(usuarioActual)) {
                        contenidoCompleto.append(construirLineaCSV());
                        usuarioActualizado = true;
                    } else { contenidoCompleto.append(linea).append("\n"); }
                }
            } catch (IOException e) { System.out.println("Error: " + e.getMessage()); }
        }

        if (!usuarioActualizado) { contenidoCompleto.append(construirLineaCSV()); }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            bw.write(contenidoCompleto.toString());
        } catch (IOException e) { System.out.println("Error: " + e.getMessage()); }
    }

    private String construirLineaCSV() {
        return usuarioActual + "," + passwordActual + "," + saldoEfectivo + ","
                + mercado.get("b").getCantidad() + ","
                + mercado.get("a").getCantidad() + ","
                + mercado.get("z").getCantidad() + ","
                + mercado.get("t").getCantidad() + ","
                + mercado.get("g").getCantidad() + ","
                + mercado.get("m").getCantidad() + "\n";
    }
}

