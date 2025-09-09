package view;

import java.util.List;
import controller.TriatloController;

public class TriatloView {
    
    public void mostrarCabecalho() {
        System.out.println("PROVA DE TRIATLO MODERNO");
        System.out.println("========================");
        System.out.println("25 atletas - 5 armas - 3 fases");
        System.out.println();
    }
    
    public void mostrarInicioCorrida(TriatloController.Atleta atleta) {
        System.out.println(atleta + " iniciou corrida (3Km)");
    }
    
    public void mostrarFimCorrida(TriatloController.Atleta atleta) {
        System.out.println(atleta + " terminou corrida em " + atleta.colocacaoCorrida + " lugar");
    }
    
    public void mostrarAguardandoArma(TriatloController.Atleta atleta) {
        System.out.println(atleta + " aguardando arma...");
    }
    
    public void mostrarInicioTiros(TriatloController.Atleta atleta) {
        System.out.println(atleta + " comecou os tiros");
    }
    
    public void mostrarTiro(TriatloController.Atleta atleta, int numero, int pontos, double tempo) {
        System.out.println("   " + atleta + " - Tiro " + numero + ": " + pontos + " pontos (" + String.format("%.1fs", tempo) + ")");
    }
    
    public void mostrarFimTiros(TriatloController.Atleta atleta) {
        System.out.println(atleta + " terminou tiros: " + atleta.getTotalPontosTiros() + " pontos");
    }
    
    public void mostrarInicioCiclismo(TriatloController.Atleta atleta) {
        System.out.println(atleta + " iniciou ciclismo (5Km)");
    }
    
    public void mostrarFimCiclismo(TriatloController.Atleta atleta) {
        System.out.println(atleta + " terminou ciclismo");
    }
    
    public void mostrarFinalizacao(TriatloController.Atleta atleta) {
        System.out.println(atleta + " COMPLETOU A PROVA!");
        System.out.println("------------------------");
    }
    
    public void mostrarRankingFinal(List<TriatloController.Atleta> atletas) {
        System.out.println();
        System.out.println("RANKING FINAL");
        System.out.println("=============");
        System.out.println("Pos | Atleta   | Pontos | Tiros | Col.Corr");
        System.out.println("----|----------|--------|-------|---------");
        
        for (int i = 0; i < atletas.size(); i++) {
            TriatloController.Atleta atleta = atletas.get(i);
            System.out.printf("%2d | %s | %5d  | %4d  | %8d%n",
                i + 1,
                atleta,
                atleta.pontuacaoFinal,
                atleta.getTotalPontosTiros(),
                atleta.colocacaoCorrida);
        }
    }
    
    public void mostrarErro(String mensagem) {
        System.out.println("ERRO: " + mensagem);
    }
}