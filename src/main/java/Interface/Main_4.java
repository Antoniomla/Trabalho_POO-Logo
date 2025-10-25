/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Logica.Bomba;
import Logica.Robo;
import Logica.RoboInteligente;
import Logica.Rocha;
import Logica.Simulacao;
import Logica.Simulacao.PosicaoObstaculo;

/**
 *
 * @author Antônio Carlos
 */
public class Main_4 {
    private static final int AREA_LADO = Robo.TAMANHO_AREA; 
    private static final int POSICAO_INICIAL = 0;

    // Obtém uma coordenada válida (mantida)
    private static int obterCoordenada(Scanner sc , String eixo, int coordOposta, int alimentoX, int alimentoY, List<PosicaoObstaculo> obstaculos) {
        int coord = -1;
        boolean valido = false;
        
        while(!valido) {
            System.out.print("Digite a coordenada " + eixo + " (0 a " + (AREA_LADO-1) + "): ");
            try {
                if (!sc.hasNextInt()) {
                    System.out.println("ERRO: Entrada Inválida. Digite um número inteiro.");
                    sc.next();
                    continue;
                }
                coord = sc.nextInt();
                
                if (coord < 0 || coord >= AREA_LADO) {
                    System.out.println("ERRO: A coordenada deve estar entre 0 e " + (AREA_LADO-1) + ".");
                    continue;
                }
                
                // Checagem de posição (se for a segunda coordenada, checa a posição completa)
                if (coordOposta != -1) {
                    int x = (eixo.equals("X") ? coord : coordOposta);
                    int y = (eixo.equals("Y") ? coord : coordOposta);

                    if (x == alimentoX && y == alimentoY) {
                        System.out.println("ERRO: Posição do obstáculo não pode ser a do alimento!");
                        continue;
                    }
                    if (x == POSICAO_INICIAL && y == POSICAO_INICIAL) {
                        System.out.println("ERRO: Posição do obstáculo não pode ser a posição inicial (0, 0)!");
                        continue;
                    }
                    // Checa se a posição está ocupada por um obstáculo já adicionado
                    for (PosicaoObstaculo po : obstaculos) {
                        if (po.posicaox == x && po.posicaoy == y) {
                            System.out.println("ERRO: Posição (" + x + ", " + y + ") já ocupada por outro obstáculo!");
                            continue;
                        }
                    }
                }
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada Inválida. Digite um número inteiro.");
                sc.next();
            }
        }
        return coord;
    }
    
    // Método para permitir o usuário inserir os obstáculos (mantido)
    private static List<PosicaoObstaculo> obterObstaculos(Scanner sc, int alimentoX, int alimentoY) {
        List<PosicaoObstaculo> obstaculos = new ArrayList<>();
        String continuar = "S";
        
        System.out.println("\n--- CONFIGURACAO DE OBSTÁCULOS ---");
        System.out.println("Você pode adicionar Rochas ou Bombas na área.");

        while (continuar.toUpperCase().startsWith("S")) {
            System.out.println("\nAdicionar novo obstáculo:");
            
            // TIPO
            int tipo = 0;
            while(tipo < 1 || tipo > 2) {
                System.out.print("Tipo (1: Bomba / 2: Rocha): ");
                if (sc.hasNextInt()) {
                    tipo = sc.nextInt();
                } else {
                    sc.next();
                }
            }
            
            // COORDENADAS
            int obstaculoX = obterCoordenada(sc, "X", -1, alimentoX, alimentoY, obstaculos);
            int obstaculoY = obterCoordenada(sc, "Y", obstaculoX, alimentoX, alimentoY, obstaculos);
            
            // CRIAÇÃO
            if (tipo == 1) {
                obstaculos.add(new PosicaoObstaculo(obstaculoX, obstaculoY, new Bomba()));
            } else {
                obstaculos.add(new PosicaoObstaculo(obstaculoX, obstaculoY, new Rocha()));
            }
            
            System.out.println("Obstáculo adicionado!");
            
            System.out.print("Deseja adicionar mais obstáculos? (S/N): ");
            continuar = sc.next();
        }
        return obstaculos;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 1. Configuração Inicial
        Robo roboNormal = new Robo("Rosa");
        RoboInteligente roboInteligente = new RoboInteligente("Verde");
        
        int alimentoX, alimentoY;
        
        System.out.println("==========================================");
        System.out.println("     SIMULACAO : ROBOS E OBSTACULOS");
        System.out.println("==========================================");
        
        // 2. Obter a posição do alimento
        System.out.println("\n--- CONFIGURACAO DO ALIMENTO ---");
        alimentoX = obterCoordenada(sc, "X", -1, -1, -1, new ArrayList<>());
        alimentoY = obterCoordenada(sc, "Y", alimentoX, -1, -1, new ArrayList<>());
        System.out.printf("Alimento posicionado em: (%d, %d)\n", alimentoX, alimentoY);
        
        // 3. Obter os obstáculos do usuário
        List<PosicaoObstaculo> obstaculos = obterObstaculos(sc, alimentoX, alimentoY);
        
        // 4. Cria a simulação (o motor do jogo)
        Simulacao simulacao = new Simulacao(roboNormal, roboInteligente, alimentoX, alimentoY, obstaculos);
        
        System.out.println("\nJOGO INICIADO! Robôs iniciam em (0, 0).");
        System.out.println("------------------------------------------");
        
        while (!simulacao.jogoAcabou()) {
            
            simulacao.executarTurnoNormal();

            simulacao.executarTurnoInteligente();
        }
    
        long movimentosNormal = simulacao.getMovimentosNormal();
        long movimentosInteligente = simulacao.getMovimentosInteligente();
        
        System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("    !!! SIMULACAO CONCLUIDA !!!");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        
        // Resultados do Robô Normal
        System.out.println("--- Robô Normal (" + roboNormal.getCor() + ") ---");
        if (roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("STATUS: VENCEU! Encontrou o alimento.");
        } else if (!roboNormal.isAtivo()) {
            System.out.println("STATUS: EXPLODIU! Posição: " + roboNormal.toString());
        } else {
            System.out.println("STATUS: Perdeu, mas permaneceu ativo. Posição: " + roboNormal.toString());
        }
        System.out.println("Total de movimentos tentados: " + movimentosNormal);

        // Resultados do Robô Inteligente
        System.out.println("\n--- Robô Inteligente (" + roboInteligente.getCor() + ") ---");
        if (roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("STATUS: VENCEU! Encontrou o alimento.");
        } else if (!roboInteligente.isAtivo()) {
            System.out.println("STATUS: EXPLODIU! Posição: " + roboInteligente.toString());
        } else {
            System.out.println("STATUS: Perdeu, mas permaneceu ativo. Posição: " + roboInteligente.toString());
        }
        System.out.println("Total de movimentos tentados: " + movimentosInteligente);
        
        sc.close();
    }
}
