
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ventasDeAcciones {

    private double precioBitcoin = 50000.0;
    private double precioApple = 150.0;
    private double precioAmazon = 130.0;
    private double precioTesla = 200.0;
    private double precioGoogle = 140.0;
    private double precioMicrosoft = 320.0;
    private int dia = 1;

    private double saldoEfectivo = 10000.0;
    private int misBitcoins = 0;
    private int misApples = 0;
    private int misAmazons = 0;
    private int misTeslas = 0;
    private int misGoogles = 0;
    private int misMicrosofts = 0;

    public void iniciarSimulador() {
        try (Scanner teclado = new Scanner(System.in)) {
            Random rand = new Random();

            ScheduledExecutorService reloj = Executors.newScheduledThreadPool(1);

            reloj.scheduleAtFixedRate(() -> {
                dia++;

                double varBTC = (-6 + rand.nextDouble() * 12) / 100.0;
                precioBitcoin *= (1 + varBTC);

                double varAAPL = (-3 + rand.nextDouble() * 6) / 100.0;
                precioApple *= (1 + varAAPL);

                double varAMZN = (-3 + rand.nextDouble() * 6) / 100.0;
                precioAmazon *= (1 + varAMZN);

                double varTSLA = (-5 + rand.nextDouble() * 10) / 100.0;
                precioTesla *= (1 + varTSLA);

                double varGOOGL = (-2.5 + rand.nextDouble() * 5) / 100.0;
                precioGoogle *= (1 + varGOOGL);

                double varMSFT = (-2 + rand.nextDouble() * 4) / 100.0;
                precioMicrosoft *= (1 + varMSFT);

            System.out.println("\n-----------------------------------------------------------------------------");
                System.out.println("Precios Iniciales: ");
                System.out.printf("BTC: 50000  |  AAPL: 150  |    AMZN: 130\n");
                System.out.printf("TSLA: 200 |  GOOG: 140  |    MSFT: 320\n");
                System.out.println("-----------------------------------------------------------------------------");

                System.out.println("\n\n [MERCADO EN VIVO - DÍA " + dia + "]");
                System.out.println("-----------------------------------------------------------------------------");
                System.out.printf("BTC: $%.2f (%+.2f%%)  |  AAPL: $%.2f (%+.2f%%)  |    AMZN: $%.2f (%+.2f%%)\n",
                        precioBitcoin, varBTC*100, precioApple, varAAPL*100, precioAmazon, varAMZN*100);
                System.out.printf("TSLA: $%.2f (%+.2f%%) |  GOOG: $%.2f (%+.2f%%)  |    MSFT: $%.2f (%+.2f%%)\n",
                        precioTesla, varTSLA*100, precioGoogle, varGOOGL*100, precioMicrosoft, varMSFT*100);
                System.out.println("-----------------------------------------------------------------------------");
                System.out.print("[MERCADO] Ingresa comando (ej: ct = comprar tesla, vz = vender amazon,p = portafolio,s = salir): ");

            }, 5, 30, TimeUnit.SECONDS);

            System.out.println("=================================================================");
            System.out.println(" WALL STREET SIMULATOR - BIENVENIDO ");
            System.out.println(" Saldo Inicial en Cuenta: $10,000.00 dólares líquidos.");
            System.out.println("=================================================================");

            boolean activo = true;
            while (activo) {
                System.out.print("[MERCADO] Esperando orden: ");
                String comando = teclado.nextLine().trim().toLowerCase();

                switch (comando) {
                    case "cb" -> {
                        if (saldoEfectivo >= precioBitcoin) { saldoEfectivo -= precioBitcoin; misBitcoins++; System.out.printf(" Compraste 1 BTC a $%.2f\n", precioBitcoin); }
                        else { System.out.println(" Saldo insuficiente."); }
                    }
                    case "ca" -> {
                        if (saldoEfectivo >= precioApple) { saldoEfectivo -= precioApple; misApples++; System.out.printf(" Compraste 1 AAPL a $%.2f\n", precioApple); }
                        else { System.out.println(" Saldo insuficiente."); }
                    }
                    case "cz" -> {
                        if (saldoEfectivo >= precioAmazon) { saldoEfectivo -= precioAmazon; misAmazons++; System.out.printf(" Compraste 1 AMZN a $%.2f\n", precioAmazon); }
                        else { System.out.println(" Saldo insuficiente."); }
                    }
                    case "ct" -> {
                        if (saldoEfectivo >= precioTesla) { saldoEfectivo -= precioTesla; misTeslas++; System.out.printf(" Compraste 1 TSLA a $%.2f\n", precioTesla); }
                        else { System.out.println(" Saldo insuficiente."); }
                    }
                    case "cg" -> {
                        if (saldoEfectivo >= precioGoogle) { saldoEfectivo -= precioGoogle; misGoogles++; System.out.printf(" Compraste 1 GOOG a $%.2f\n", precioGoogle); }
                        else { System.out.println(" Saldo insuficiente."); }
                    }
                    case "c-m" -> {
                        if (saldoEfectivo >= precioMicrosoft) { saldoEfectivo -= precioMicrosoft; misMicrosofts++; System.out.printf(" Compraste 1 MSFT a $%.2f\n", precioMicrosoft); }
                        else { System.out.println(" Saldo insuficiente."); }
                    }
                    case "v-b" -> {
                        if (misBitcoins > 0) { misBitcoins--; saldoEfectivo += precioBitcoin; System.out.printf(" Vendiste 1 BTC por $%.2f\n", precioBitcoin); }
                        else { System.out.println(" No tienes este activo."); }
                    }
                    case "v-a" -> {
                        if (misApples > 0) { misApples--; saldoEfectivo += precioApple; System.out.printf(" Vendiste 1 AAPL por $%.2f\n", precioApple); }
                        else { System.out.println(" No tienes este activo."); }
                    }
                    case "v-z" -> {
                        if (misAmazons > 0) { misAmazons--; saldoEfectivo += precioAmazon; System.out.printf(" Vendiste 1 AMZN por $%.2f\n", precioAmazon); }
                        else { System.out.println(" No tienes este activo."); }
                    }
                    case "v-t" -> {
                        if (misTeslas > 0) { misTeslas--; saldoEfectivo += precioTesla; System.out.printf(" Vendiste 1 TSLA por $%.2f\n", precioTesla); }
                        else { System.out.println(" No tienes este activo."); }
                    }
                    case "v-g" -> {
                        if (misGoogles > 0) { misGoogles--; saldoEfectivo += precioGoogle; System.out.printf(" Vendiste 1 GOOG por $%.2f\n", precioGoogle); }
                        else { System.out.println(" No tienes este activo."); }
                    }
                    case "v-m" -> {
                        if (misMicrosofts > 0) { misMicrosofts--; saldoEfectivo += precioMicrosoft; System.out.printf(" Vendiste 1 MSFT por $%.2f\n", precioMicrosoft); }
                        else { System.out.println(" No tienes este activo."); }
                    }
                    case "p" -> {
                        System.out.println("\n=================================================================");
                        System.out.println("  REPORTE INTEGRAL DE TU PORTAFOLIO DE INVERSIONES");
                        System.out.println("=================================================================");
                        System.out.printf(" Cash Disponible: $%.2f\n", saldoEfectivo);
                        System.out.println(" Activos Guardados:");
                        System.out.printf("   • BTC: %d | AAPL: %d | AMZN: %d\n", misBitcoins, misApples, misAmazons);
                        System.out.printf("   • TSLA: %d | GOOG: %d | MSFT: %d\n", misTeslas, misGoogles, misMicrosofts);

                        double valorTotal = saldoEfectivo + (misBitcoins * precioBitcoin) + (misApples * precioApple)
                                + (misAmazons * precioAmazon) + (misTeslas * precioTesla)
                                + (misGoogles * precioGoogle) + (misMicrosofts * precioMicrosoft);

                        System.out.printf("\n  VALOR NETO DE TU PATRIMONIO: $%.2f\n", valorTotal);
                        System.out.println("=================================================================");
                    }

                    case "s" -> {
                        activo = false;
                        System.out.println("\n[SISTEMA]: Apagando y deteniendo reloj de cotizaciones...");
                    }

                    default -> System.out.println(" Comando inválido.");
                }
            }
            reloj.shutdown();
        }
    }
}

