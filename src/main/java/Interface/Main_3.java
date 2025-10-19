/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import Logica.Robo;
import Logica.RoboInteligente;
import excecao.MovimentoInvalidoException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Antônio Carlos
 */
public class Main_3 {
    private static final int AREA_LADO = Robo.TAMANHO_AREA; // 4
    
    // Método para obter as coordenadas do alimento
    private static int obterCoordenada(Scanner sc , String eixo){
        int coord = -1;
        while(coord < 0 || coord >= AREA_LADO){
            System.out.print("Digite a coordenada " + eixo + " do alimento (0 a " + (AREA_LADO-1) + "): ");
            try{
                coord = sc.nextInt();
                if(coord < 0 || coord >= AREA_LADO){
                    System.out.println("ERRO: A coordenada deve estar entre 0 e " + (AREA_LADO-1) + ". Tente Novamente.");
                }
            }catch(InputMismatchException e){
                System.out.println("ERRO: Entrada Inválida. Digite um número inteiro.");
                sc.next(); // Consome a entrada inválida
            }
        }
        return coord;
    }
    
    // área de locomoção
    private static void desenharArea(Robo robo1, Robo robo2, int alimentoX, int alimentoY) {
        System.out.println("\n--- SIMULACAO (" + AREA_LADO + "x" + AREA_LADO + ") ---");
        for (int y = AREA_LADO - 1; y >= 0; y--) { // Desenha de cima para baixo
            System.out.print(y + " | ");
            for (int x = 0; x < AREA_LADO; x++) {
                String simbolo = " ";
                
                boolean r1 = (x == robo1.getPosicaoX() && y == robo1.getPosicaoY());
                boolean r2 = (x == robo2.getPosicaoX() && y == robo2.getPosicaoY());
                boolean a = (x == alimentoX && y == alimentoY);

                if (r1 && r2) {
                    simbolo = "P"; // P de Partida (ambos os robôs na mesma posição)
                } else if (r1) {
                    simbolo = robo1.getCor().substring(0, 1); //robô 1 (ex: C de "Cinza")
                } else if (r2) {
                    simbolo = robo2.getCor().substring(0, 1); //robô 2 (ex: V de "Verde")
                } else if (a) {
                    simbolo = "A"; // Alimento
                } else {
                    simbolo = "*"; // Vazio
                }
                
                System.out.print(simbolo + " ");
            }
            System.out.println();
        }
        System.out.print("  +");
        for (int i = 0; i < AREA_LADO * 2; i++) System.out.print("-");
        System.out.println();
        System.out.print("    ");
        for (int x = 0; x < AREA_LADO; x++) {
            System.out.print(x + " ");
        }
        System.out.println("\n---------------------------------");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 1. Instanciar os robôs
        Robo roboNormal = new Robo("Cinza");
        RoboInteligente roboInteligente = new RoboInteligente("Verde");
        
        int alimentoX, alimentoY;
        long movimentosNormal = 0;
        long movimentosInteligente = 0;
        
        System.out.println("==========================================");
        System.out.println("  SIMULACAO DE ROBOS EM AREA " + AREA_LADO + "x" + AREA_LADO);
        System.out.println("==========================================");
        
        // 2. Obter a posição do alimento
        System.out.println("\nCONFIGURACAO DO ALIMENTO (0 a " + (AREA_LADO-1) + ")");
        alimentoX = obterCoordenada(sc, "X");
        alimentoY = obterCoordenada(sc, "Y");
        System.out.printf("Alimento posicionado em: (%d, %d)\n", alimentoX, alimentoY);
        System.out.println("\nJOGO INICIADO! Robôs iniciam em (0, 0).");
        
        // 3. Simulação de movimento por turnos
        boolean roboNormalEncontrou = false;
        boolean roboInteligenteEncontrou = false;
        
        desenharArea(roboNormal, roboInteligente, alimentoX, alimentoY);
        
        while (!roboNormalEncontrou || !roboInteligenteEncontrou) {
            
            //Movimento do Robô Normal
            if (!roboNormalEncontrou) {
                int dir = roboNormal.gerarMovimentoRandomico();
                try {
                    roboNormal.mover(dir);
                    movimentosNormal++;
                    System.out.println("Robô " + roboNormal.getCor() + " moveu (" + roboNormal.codigoParaDirecao(dir) + "). Posição: " + roboNormal.toString());
                } catch (MovimentoInvalidoException e) {
                    // Robô Normal: Falha. Não conta o movimento.
                    System.err.println("Robô " + roboNormal.getCor() + " [FALHA]: Movimento (" + roboNormal.codigoParaDirecao(dir) + ") inválido. " + e.getMessage());
                }
                roboNormalEncontrou = roboNormal.encontrouAlimento(alimentoX, alimentoY);
            }
            
            //Movimento do Robô Inteligente
            if (!roboInteligenteEncontrou) {
                int dir = roboInteligente.gerarMovimentoRandomico();
                try {
                    roboInteligente.mover(dir); 
                    movimentosInteligente++;
                    System.out.println("Robô " + roboInteligente.getCor() + " moveu (" + roboInteligente.codigoParaDirecao(dir) + "). Posição: " + roboInteligente.toString());
                } catch (MovimentoInvalidoException e) {
                    // Robô Inteligente: Falha. Não conta o movimento.
                    System.err.println("Robô " + roboInteligente.getCor() + " [FALHA]: Movimento (" + roboInteligente.codigoParaDirecao(dir) + ") inválido. " + e.getMessage());
                }
                roboInteligenteEncontrou = roboInteligente.encontrouAlimento(alimentoX, alimentoY);
            }
            
            desenharArea(roboNormal, roboInteligente, alimentoX, alimentoY);
            
            // Metodo de visualização , que eu achei legal
            try {
                Thread.sleep(200); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // 4. Mostrar resultados finais
        System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("!!! SIMULACAO CONCLUIDA !!!");
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        
        System.out.println("Robô Normal (" + roboNormal.getCor() + ") encontrou o alimento em: " + movimentosNormal + " movimentos.");
        System.out.println("Robô Inteligente (" + roboInteligente.getCor() + ") encontrou o alimento em: " + movimentosInteligente + " movimentos.");
        
        sc.close();
    }
}
