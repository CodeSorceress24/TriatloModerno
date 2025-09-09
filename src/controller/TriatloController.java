package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import view.TriatloView;

public class TriatloController {
    // Constantes
    private static final int TOTAL_ATLETAS = 25;
    private static final int ARMAS_DISPONIVEIS = 5;
    private static final int TIROS_POR_ATLETA = 3;
    private static final int DISTANCIA_CORRIDA = 3000;
    private static final int DISTANCIA_CICLISMO = 5000;
    private static final int PONTUACAO_BASE_PRIMEIRO = 250;
    private static final int DECREMENTO_PONTUACAO = 10;
    
    private final Semaphore semaforoArmas;
    private final AtomicInteger colocacaoCorrida;
    private final TriatloView view;
    private final Random random;
    
    public TriatloController() {
        this.semaforoArmas = new Semaphore(ARMAS_DISPONIVEIS);
        this.colocacaoCorrida = new AtomicInteger(1);
        this.view = new TriatloView();
        this.random = new Random();
    }
    
    public void iniciarProva() {
        view.mostrarCabecalho();
        
        List<Thread> threads = new ArrayList<>();
        List<Atleta> atletas = new ArrayList<>();
        
        // Criar atletas
        for (int i = 1; i <= TOTAL_ATLETAS; i++) {
            atletas.add(new Atleta(i));
        }
        
        // Iniciar threads
        for (Atleta atleta : atletas) {
            Thread thread = new Thread(() -> executarProva(atleta));
            threads.add(thread);
            thread.start();
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
        
        // Aguardar término
        for (Thread thread : threads) {
            try { thread.join(); } catch (InterruptedException e) {}
        }
        
        // Calcular ranking
        calcularRanking(atletas);
        view.mostrarRankingFinal(atletas);
    }
    
    private void executarProva(Atleta atleta) {
        try {
            corrida(atleta);
            tiros(atleta);
            ciclismo(atleta);
            view.mostrarFinalizacao(atleta);
        } catch (InterruptedException e) {
            view.mostrarErro("Erro no atleta " + atleta.id);
        }
    }
    
    private void corrida(Atleta atleta) throws InterruptedException {
        view.mostrarInicioCorrida(atleta);
        
        int distancia = 0;
        while (distancia < DISTANCIA_CORRIDA) {
            int velocidade = 20 + random.nextInt(6); // 20-25 m/30ms
            distancia += velocidade;
            Thread.sleep(30);
        }
        
        atleta.colocacaoCorrida = colocacaoCorrida.getAndIncrement();
        view.mostrarFimCorrida(atleta);
    }
    
    private void tiros(Atleta atleta) throws InterruptedException {
        view.mostrarAguardandoArma(atleta);
        semaforoArmas.acquire();
        
        try {
            view.mostrarInicioTiros(atleta);
            
            for (int i = 0; i < TIROS_POR_ATLETA; i++) {
                double tempo = 0.5 + random.nextDouble() * 2.5; // 0.5-3s
                int pontos = random.nextInt(11); // 0-10
                atleta.pontosTiros[i] = pontos;
                
                view.mostrarTiro(atleta, i+1, pontos, tempo);
                Thread.sleep((long)(tempo * 1000));
            }
            
            view.mostrarFimTiros(atleta);
        } finally {
            semaforoArmas.release();
        }
    }
    
    private void ciclismo(Atleta atleta) throws InterruptedException {
        view.mostrarInicioCiclismo(atleta);
        
        int distancia = 0;
        while (distancia < DISTANCIA_CICLISMO) {
            int velocidade = 30 + random.nextInt(11); // 30-40 m/40ms
            distancia += velocidade;
            Thread.sleep(40);
        }
        
        view.mostrarFimCiclismo(atleta);
    }
    
    private void calcularRanking(List<Atleta> atletas) {
        // Calcular pontuação
        for (Atleta atleta : atletas) {
            int pontosBase = PONTUACAO_BASE_PRIMEIRO - (atleta.colocacaoCorrida - 1) * DECREMENTO_PONTUACAO;
            int pontosTiros = 0;
            for (int pontos : atleta.pontosTiros) pontosTiros += pontos;
            atleta.pontuacaoFinal = pontosBase + pontosTiros;
        }
        
        // Ordenar por pontuação
        atletas.sort((a1, a2) -> Integer.compare(a2.pontuacaoFinal, a1.pontuacaoFinal));
        
        // Atribuir colocação final
        for (int i = 0; i < atletas.size(); i++) {
            atletas.get(i).colocacaoFinal = i + 1;
        }
    }
    
    // Classe interna simples para Atleta
    public class Atleta {
        int id;
        String nome;
        public int colocacaoCorrida;
        int[] pontosTiros;
        int colocacaoFinal;
        public int pontuacaoFinal;
        
        Atleta(int id) {
            this.id = id;
            this.nome = "Atleta " + id;
            this.pontosTiros = new int[TIROS_POR_ATLETA];
        }
        
        public String toString() {
            return String.format("Atleta %02d", id);
        }
        
        public int getTotalPontosTiros() {
            int total = 0;
            for (int pontos : pontosTiros) total += pontos;
            return total;
        }
    }
}