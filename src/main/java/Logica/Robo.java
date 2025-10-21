package Logica;
import excecao.MovimentoInvalidoException;

public class Robo {
    public static final int TAMANHO_AREA = 4;
    
    private int posicaoX;
    private int posicaoY;
    private String cor;
    private boolean ativo;
    
    protected int ultimoMovimentoValido = 0; 

    // 🟩 Novos atributos para contar os movimentos
    private int movimentosValidos = 0;
    private int movimentosInvalidos = 0;
    
    public Robo(String cor){
        this.cor = cor;
        this.posicaoX = 0;
        this.posicaoY = 0;
        this.ativo = true;
    }

    public boolean isAtivo(){
        return ativo;
    }

    public void explodir(){
        this.ativo = false;
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
    
    public int getMovimentosValidos() {
        return movimentosValidos;
    }

    public int getMovimentosInvalidos() {
        return movimentosInvalidos;
    }

    public void setPosicaoX(int novaPosicaoX) throws MovimentoInvalidoException {
        if(novaPosicaoX < 0 || novaPosicaoX >= TAMANHO_AREA){
            throw new MovimentoInvalidoException("Tentativa de Inválida para X = " + novaPosicaoX + " (fora da área 0-" + (TAMANHO_AREA-1) + ").");
        }
        this.posicaoX = novaPosicaoX;
    }
    
    public void setPosicaoY(int novaPosicaoY) throws MovimentoInvalidoException {
        if(novaPosicaoY < 0 || novaPosicaoY >= TAMANHO_AREA){
            throw new MovimentoInvalidoException("Tentativa de Inválida para Y = " + novaPosicaoY + " (fora da área 0-" + (TAMANHO_AREA-1) + ").");
        }
        this.posicaoY = novaPosicaoY;
    }

    public int gerarMovimentoRandomico() {
        // Gera um número entre 1 e 4 (Up:1, Down:2, Right:3, Left:4)
        return (int) (Math.random() * 4) + 1;
    }
    
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
        if (!this.ativo){
            throw new MovimentoInvalidoException("Robo inativo pela Bomba. Não pode se mover.");
        }
        
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
                movimentosInvalidos++; // ❌ Direção inválida
                throw new MovimentoInvalidoException("Código de direção Inválido! (Use 1 a 4)");
        }
        
        // Validação de Limites
        if(novoX < 0 || novoX >= TAMANHO_AREA || novoY < 0 || novoY >= TAMANHO_AREA){
            movimentosInvalidos++; // ❌ Fora da área
            throw new MovimentoInvalidoException("Movimento fora da área (0-" + (TAMANHO_AREA-1) + ").");
        }

        // ✅ Movimento válido
        this.posicaoX = novoX;
        this.posicaoY = novoY;
        this.ultimoMovimentoValido = codigoDirecao;
        this.movimentosValidos++;
    }
    
    public boolean encontrouAlimento(int alimentoX, int alimentoY){
        return this.posicaoX == alimentoX && this.posicaoY == alimentoY;
    }
    
    @Override
    public String toString() {
        return cor + " (" + posicaoX + ", " + posicaoY + ")"; 
    }
}
