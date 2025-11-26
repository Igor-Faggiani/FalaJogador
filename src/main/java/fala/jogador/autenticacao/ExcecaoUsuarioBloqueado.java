package fala.jogador.autenticacao;

public class ExcecaoUsuarioBloqueado extends RuntimeException {
    public ExcecaoUsuarioBloqueado(String mensagem) {
        super(mensagem);
    }
}
