package Interface;
import excecao.MovimentoInvalidoException;
import Logica.Robo;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    
    private static int obterCoordenada(Scanner sc , String eixo){
        int coord = -1;
        while(coord < 0){
            System.out.print("Digite a coordenada " + eixo + " do alimento: ");
            try{
                coord = sc.nextInt();
                if(coord < 0){
                    System.out.println("ERRO:A coordenada nao deve ser negativa.Tente Novamente.");
                }
            }catch(InputMismatchException e){
                // verifica se o usuario vai digitar um inteiro
                System.out.println("ERRO:Entrada Invalida.Digite um número inteiro.");
                sc.nextInt();
            }
        }
        return coord;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Robo meuRobo = new Robo("Rosa Marfin");
        int alimentoX ,alimentoY , direcao;
        
        System.out.println("==========================================");
        System.out.println("                 ROBO LOGO");
        System.out.println("==========================================");
        System.out.println("Robo " + meuRobo.getCor() + " iniciado na posicao (0 , 0).");
        System.out.println("\nCONFIGURACAO DO ALIMENTO");
        alimentoX = obterCoordenada(sc, "X");
        alimentoY = obterCoordenada(sc, "Y");
        System.out.printf("Alimento posicionado em: (%d, %d)\n", alimentoX, alimentoY);
        System.out.println("\nJOGO INICIADO COM SUCESSO!");
        
        while (!meuRobo.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("------------------------------------------");
            System.out.println("Posicao Atual: " + meuRobo.toString());
            System.out.println("Mova o Robo:");
            System.out.println("  1: up | 2: down | 3: right | 4: left");
            System.out.print("Sua escolha: ");
            
            try {
                if (sc.hasNextInt()) {
                    direcao = sc.nextInt();
                    
                    meuRobo.mover(direcao); 
                } else {
                    // Captura e trata a InputMismatchException (se digitar letra, por exemplo)
                    System.err.println("ERRO DE ENTRADA: Digite um número inteiro de 1 a 4.");
                    sc.next(); // Consome a entrada inválida
                }
            } catch (MovimentoInvalidoException e) {
                // Captura e trata a exceção(movimento negativo)
                System.err.println("\n MOVIMENTO BLOQUEADO!");
                System.out.println("----------------------------");
                System.err.println("Motivo: " + e.getMessage());
                System.out.println("O robo nao se moveu e permanece na posicao anterior.");
            }
        }
        
        System.out.println("\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("!!! ALIMENTO ENCONTRADO !!!");
        System.out.printf("O Robo chegou a posicao do alimento (%d, %d), Onde estava o Alimento!.\n", alimentoX, alimentoY);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        
        
        sc.close();
    }
}
