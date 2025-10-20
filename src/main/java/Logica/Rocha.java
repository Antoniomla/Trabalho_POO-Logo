package Logica;

import Logica.Robo;
import excecao.MovimentoInvalidoException;

public class Rocha extends Obstaculo{
    
    @Override
    public boolean bater(Robo robo , int ultimaPosicaoX , int ultimaPosicaoY){
        System.out.println("O robo " + robo.getCor() + " bateu em uma rocha " + getId() + " na posicao (" + robo.getPosicaoX() + " , " + robo.getPosicaoY() + ") e voltou para a posicao anterior (" + ultimaPosicaoX + " , " + ultimaPosicaoY + ").");

        try {
            robo.setPosicaoX(ultimaPosicaoX);
            robo.setPosicaoY(ultimaPosicaoY);
        } catch (MovimentoInvalidoException ignored) {
            // Não deve ocorrer, pois estamos retornando para uma posição válida.
        }
        return false;
    }
    @Override
    public String toString(){
        return "Rocha";
    }
}
