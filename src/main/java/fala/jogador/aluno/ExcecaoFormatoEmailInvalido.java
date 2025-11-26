package fala.jogador.aluno;

public class ExcecaoFormatoEmailInvalido extends RuntimeException {
    public ExcecaoFormatoEmailInvalido(String mensagem) {
        super(mensagem);
    }
}