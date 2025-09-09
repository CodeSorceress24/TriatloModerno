import controller.TriatloController;

public class Main {
    public static void main(String[] args) {
        System.out.println("SIMULACAO DE TRIATLO MODERNO");
        System.out.println("=============================");
        
        TriatloController controller = new TriatloController();
        controller.iniciarProva();
        
        System.out.println("Prova finalizada! Ranking oficial:");
    }
}