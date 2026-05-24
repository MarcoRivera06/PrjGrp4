public class App {
    public static void main(String[] args) throws Exception {
        Controlador.runManager manager = new Controlador().new runManager();
        manager.iniciarSimulador();
    }
}
