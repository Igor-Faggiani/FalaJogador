package fala.jogador.aluno;

public class ExcecaoRegistroNaoEncontrado extends RuntimeException {
    public ExcecaoRegistroNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}