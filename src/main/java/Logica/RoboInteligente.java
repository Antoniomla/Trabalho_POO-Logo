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
public class RoboInteligente extends Robo {
    private int ultimoMovimentoInvalido = 0; 

    public RoboInteligente(String cor) {
        super(cor);
    }
    @Override
    public void mover(int codigoDirecao) throws MovimentoInvalidoException {
        
        // Evitar repetir o movimento que falhou
        if (codigoDirecao == ultimoMovimentoInvalido && ultimoMovimentoInvalido != 0) {
             this.ultimoMovimentoInvalido = 0; // Se repetiu e falhou, limpamos o registro para que ele não bloqueie um terceiro movimento
             throw new MovimentoInvalidoException("Movimento evitado (Cód: " + codigoDirecao + "). Foi o mesmo que falhou na rodada anterior.");
        }
        
        try {
            super.mover(codigoDirecao);
            this.ultimoMovimentoInvalido = 0;
            
        } catch (MovimentoInvalidoException e) {
            // quando o movimento falhou, armazena o código.
            this.ultimoMovimentoInvalido = codigoDirecao;
            
            // Relança a exceção para que a Main saiba que houve uma falha.
            throw e; 
        }
    }
}
