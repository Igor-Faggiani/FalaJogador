package fala.jogador.autenticacao;

public class ExcecaoSenhaIncorreta extends RuntimeException {
    public ExcecaoSenhaIncorreta(String mensagem) {
        super(mensagem);
    }
}
