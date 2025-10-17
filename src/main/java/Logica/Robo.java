/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import excecao.MovimentoInvalidoException;

/**
 *
 * @author Antônio Carlos
 */
public class Robo {
    private int posicaoX;
    private int posicaoY;
    private String cor;
    
    public Robo(String cor){
        this.cor = cor;
        this.posicaoX = 0;
        this.posicaoY = 0;
    }
    
    public String getCor(){
        return cor;
    }
    
    public int getPosicaoX(){
        return posicaoX;
    }
    
    public int getPosicaoY(){
        return posicaoY;
    }
    
    public void setPosicaoX(int novaPosicaoX) throws MovimentoInvalidoException{
        if(novaPosicaoX < 0){
            throw new MovimentoInvalidoException("Tentativa de Locomoção Inválida, pelo robô " + this.cor + "para X = " + novaPosicaoX + " ,(coordenada negativa!)");
        }
        this.posicaoX = novaPosicaoX;
    }
    
    public void setPosicaoY(int novaPosicaoY) throws MovimentoInvalidoException{
        if(novaPosicaoY < 0){
            throw new MovimentoInvalidoException("Tentativa de Locomoção Inválida, pelo robô "+ this.cor + "para Y = " + novaPosicaoY + " ,(coordenada negativa!)");
        }
        this.posicaoY = novaPosicaoY;
    }
    
    public void mover(String direcao) throws MovimentoInvalidoException{
        int novoX = this.posicaoX;
        int novoY = this.posicaoY;
        
        switch(direcao.toLowerCase()){
            case "up":
                novoY++;
                break;
            case "down":
                novoY--;
                break;
            case "right":
                novoX++;
                break;
            case "left":
                novoX--;
            default:
                System.out.println("Direção do movimento Inválida!");
                return;
        }
        if(novoX < 0){
            throw new MovimentoInvalidoException("O robô " + this.cor + " não pode se mover para X = " + novoX + ".");
        }
        if(novoY < 0){
            throw new MovimentoInvalidoException("O robô " + this.cor + " não pode se mover para X = " + novoY + ".");
        }
        this.posicaoX = novoX;
        this.posicaoY = novoY;
        System.out.println("Movimento '" + direcao + "' realizado. Robô " + this.cor + " na posição (" + this.posicaoX + ", " + this.posicaoY + ")");
    }
    
    public void mover(int codigoDirecao) throws MovimentoInvalidoException{
            String direcao = "";
            switch(codigoDirecao){
                case 1:
                    direcao = "up";
                    break;
                case 2:
                    direcao = "down";
                    break;
                case 3:
                    direcao = "right";
                    break;
                case 4:
                    direcao = "left";
                    break;
                default:
                    System.out.println("Código de direção Inválido!");
                    return;
        }
        this.mover(direcao);//execcutar a logica do mover anterior com a exceção
    }
    public boolean encontrouAlimento(int alimentoX, int alimentoY){
        return this.posicaoX == alimentoX && this.posicaoY == alimentoY;
    }
    @Override
    public String toString() {
        // Formato (X, Y)
        return "Robô " + cor + " na posição (" + posicaoX + ", " + posicaoY + ")"; 
    }
}
