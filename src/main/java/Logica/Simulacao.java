package Logica;

import java.util.ArrayList;
import java.util.List;

import excecao.MovimentoInvalidoException;

public class Simulacao {
    public static final int AREA_LADO = Robo.TAMANHO_AREA; 
    
    private final Robo roboNormal;
    private final RoboInteligente roboInteligente;
    private final int alimentoX;
    private final int alimentoY;
    
    private final List<PosicaoObstaculo> obstaculos;

    private long movimentosNormal = 0;
    private long movimentosInteligente = 0;

    public Simulacao(Robo roboNormal, RoboInteligente roboInteligente, int alimentoX, int alimentoY, List<PosicaoObstaculo> obstaculos) {
        this.roboNormal = roboNormal;
        this.roboInteligente = roboInteligente;
        this.alimentoX = alimentoX;
        this.alimentoY = alimentoY;
        this.obstaculos = new ArrayList<>(obstaculos); 
    }
    
    public static class PosicaoObstaculo {
        public int posicaox, posicaoy;
        public Obstaculo obstaculo;

        public PosicaoObstaculo(int posicaox, int posicaoy, Obstaculo obstaculo) {
            this.posicaox = posicaox;
            this.posicaoy = posicaoy;
            this.obstaculo = obstaculo;
        }
    }
    
    private boolean tentarMoverRobo(Robo robo, int codigoDirecao) {
        if (!robo.isAtivo()) {
            return false;
        }
        
        int oldX = robo.getPosicaoX();
        int oldY = robo.getPosicaoY();
        
        try {
            robo.mover(codigoDirecao);
            
            // 1. CHECA COLISÃO
            PosicaoObstaculo obstaculoEncontrado = null;
            for (PosicaoObstaculo po : obstaculos) {
                if (po.posicaox == robo.getPosicaoX() && po.posicaoy == robo.getPosicaoY()) {
                    obstaculoEncontrado = po;
                    break;
                }
            }

            if (obstaculoEncontrado != null) {
                System.out.println("-> Colisão: Robô " + robo.getCor() + " atingiu " + obstaculoEncontrado.obstaculo.toString());
                
                boolean removerObstaculo = obstaculoEncontrado.obstaculo.bater(robo, oldX, oldY);
                
                if (removerObstaculo) {
                    obstaculos.remove(obstaculoEncontrado);
                    System.out.println("-> " + obstaculoEncontrado.obstaculo.toString() + " desapareceu.");
                }
            }
            
            if (robo.encontrouAlimento(alimentoX, alimentoY)) {
                System.out.println("--- ROBÔ " + robo.getCor() + " ENCONTROU O ALIMENTO ---");
            }
            
            return true; 
            
        } catch (MovimentoInvalidoException e) {
            System.err.println("Robô " + robo.getCor() + " [FALHA]: " + e.getMessage());
            return false;
        }
    }
    public void executarTurnoNormal() {
        if (roboNormal.isAtivo() && !roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
            int dir = roboNormal.gerarMovimentoRandomico();
            if(tentarMoverRobo(roboNormal, dir)) {
                movimentosNormal++;
            }
        }
    }
    public void executarTurnoInteligente() {
        if (roboInteligente.isAtivo() && !roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
            int dir = roboInteligente.gerarMovimentoRandomico();
            if(tentarMoverRobo(roboInteligente, dir)) {
                movimentosInteligente++;
            }
        }
    }
    public boolean jogoAcabou() {
        boolean algumEncontrou = roboNormal.encontrouAlimento(alimentoX, alimentoY) || roboInteligente.encontrouAlimento(alimentoX, alimentoY);
        boolean ambosExplodiram = !roboNormal.isAtivo() && !roboInteligente.isAtivo();
        
        return algumEncontrou || ambosExplodiram;
    }
    
    // resultado final
    public long getMovimentosNormal() { return movimentosNormal; }
    public long getMovimentosInteligente() { return movimentosInteligente; }
    
    // para o JavaFX ler o status do robô
    public Robo getRoboNormal() { return roboNormal; }
    public RoboInteligente getRoboInteligente() { return roboInteligente; }
}
