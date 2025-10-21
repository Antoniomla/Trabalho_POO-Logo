package Interface;

import Logica.Robo;
import excecao.MovimentoInvalidoException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Main_2 {
        
        // O limite máximo para coordenadas em uma área $4 \times 4$ (0, 1, 2, 3)
    private static final int LIMITE_MAX = Robo.TAMANHO_AREA - 1; 

    private static int obterCoordenada(Scanner sc , String eixo){
        int coord = -1;
        
        // Loop que garante que a entrada seja um número inteiro e esteja dentro do limite [0, 3]
        while(coord < 0 || coord > LIMITE_MAX){ 
            System.out.print("Digite a coordenada " + eixo + " do alimento (0 a " + LIMITE_MAX + "): "); 
            try{
                coord = sc.nextInt();
                
                if(coord < 0 || coord > LIMITE_MAX){ 
                    System.out.printf("ERRO: A coordenada deve estar entre 0 e %d (plano $4 \times 4$). Tente Novamente.\n", LIMITE_MAX);
                }
            }catch(InputMismatchException e){
                // Verifica se o usuário digitou um inteiro
                System.out.println("ERRO: Entrada Invalida. Digite um número inteiro.");
                sc.nextLine();  // Limpa o buffer de entrada inválida
            }
        }
        return coord;
    }
        
    public static void main(String[] args){
        
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        
        //Instanciando robôs
        Robo robo1 = new Robo("Vermelho");
        Robo robo2 = new Robo("Azul");
        
        int alimentoX, alimentoY;
        
        System.out.println("==========================================");
        System.out.println("                 ROBO LOGO");
        System.out.println("==========================================");
        System.out.println("Robo " + robo1.getCor() + " e Robo " + robo2.getCor() + "iniciados na posicao (0 , 0).");
        
        System.out.println("\nCONFIGURACAO DO ALIMENTO");
        alimentoX = obterCoordenada(sc, "X"); //Busca o eixo X
        alimentoY = obterCoordenada(sc, "Y"); //Busca o eixo Y
        System.out.printf("Alimento posicionado em: (%d, %d)\n", alimentoX, alimentoY);
        
        System.out.println("\nJOGO INICIADO COM SUCESSO!");

        
        boolean alimentoEncontrado = false;
        int turnos = 0;
        
          while (!alimentoEncontrado) {
            turnos++;
            System.out.println("------------------------------------------");
            System.out.println("TURNO " + turnos);

            try {
                // Movimento aleatório do robô 1
                int direcao1 = random.nextInt(4) + 1;
                robo1.mover(direcao1);
                if (robo1.encontrouAlimento(alimentoX, alimentoY)) {
                    System.out.println("\n🍎 O robô " + robo1.getCor() + " encontrou o alimento!");
                    alimentoEncontrado = true;
                    break;
                }

                // Movimento aleatório do robô 2
                int direcao2 = random.nextInt(4) + 1;
                robo2.mover(direcao2);
                if (robo2.encontrouAlimento(alimentoX, alimentoY)) {
                    System.out.println("\n🍎 O robô " + robo2.getCor() + " encontrou o alimento!");
                    alimentoEncontrado = true;
                    break;
                }

            } catch (MovimentoInvalidoException e) {
                System.err.println("\n⚠️ MOVIMENTO BLOQUEADO!");
                System.out.println("----------------------------");
                System.err.println("Motivo: " + e.getMessage());
            }
            
            // Evita loop infinito
            if (turnos > 100) {
                System.out.println("\n⏹ Nenhum robô encontrou o alimento após 100 turnos.");
                break;
            }
        }
          
          System.out.println("\nResumo de movimentos:");
          System.out.println("Robo " + robo1.getCor() + " - Válidos: " + robo1.getMovimentosValidos() + " | Inválidos: " + robo1.getMovimentosInvalidos());
          System.out.println("Robo " + robo2.getCor() + " - Válidos: " + robo2.getMovimentosValidos() + " | Inválidos: " + robo2.getMovimentosInvalidos());
        
         // Resultado final
        System.out.println("\n==========================================");
        System.out.println("        FIM DA SIMULAÇÃO AUTOMÁTICA");
        System.out.println("==========================================");
        
        
        sc.close();
        
    }
}

