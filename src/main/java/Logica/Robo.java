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
    public static final int TAMANHO_AREA = 4;
    
    private int posicaoX;
    private int posicaoY;
    private String cor;
    
    // Variável que armazena o código do último movimento VÁLIDO.
    protected int ultimoMovimentoValido = 0; 
    
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
    
    // Método auxiliar para gerar um código de direção randômico (1 a 4)
    public int gerarMovimentoRandomico() {
        // Gera um número entre 1 e 4 (Up:1, Down:2, Right:3, Left:4)
        return (int) (Math.random() * 4) + 1;
    }
    
    // Converte o código da direção para String (útil para logs na Main)
    public String codigoParaDirecao(int codigoDirecao) {
        switch(codigoDirecao) {
            case 1: return "up";
            case 2: return "down";
            case 3: return "right";
            case 4: return "left";
            default: return "INVALIDO";
        }
    }

    public void mover(int codigoDirecao) throws MovimentoInvalidoException{
        int novoX = this.posicaoX;
        int novoY = this.posicaoY;
        
        switch(codigoDirecao){
            case 1: // up
                novoY++;
                break;
            case 2: // down
                novoY--;
                break;
            case 3: // right
                novoX++;
                break;
            case 4: // left
                novoX--;
                break;
            default:
                throw new MovimentoInvalidoException("Código de direção Inválido! (Use 1 a 4)");
        }
        
        // Verificação dos limites da área (0 a 3)
        // Se a nova posição for < 0 OU >= TAMANHO_AREA (4), lança exceção.
        if(novoX < 0 || novoX >= TAMANHO_AREA){
            throw new MovimentoInvalidoException("Movimento para X = " + novoX + " fora da área (0-" + (TAMANHO_AREA-1) + ").");
        }
        if(novoY < 0 || novoY >= TAMANHO_AREA){
            throw new MovimentoInvalidoException("Movimento para Y = " + novoY + " fora da área (0-" + (TAMANHO_AREA-1) + ").");
        }
        
        // Se chegou aqui, o movimento é válido
        this.posicaoX = novoX;
        this.posicaoY = novoY;
        this.ultimoMovimentoValido = codigoDirecao;
    }
    
    public boolean encontrouAlimento(int alimentoX, int alimentoY){
        return this.posicaoX == alimentoX && this.posicaoY == alimentoY;
    }
    
    @Override
    public String toString() {
        return cor + " (" + posicaoX + ", " + posicaoY + ")"; 
    }
}
