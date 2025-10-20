package Logica;
import excecao.MovimentoInvalidoException;

public  abstract class Obstaculo {
    private final int id;
    private static int proximoId = 1;

    public Obstaculo(){
        this.id = proximoId++;
    }
    public int getId(){
        return this.id;
    }

    public abstract boolean bater(Robo robo , int ultimaPosicaoX , int ultimaPosicaoY);
}
