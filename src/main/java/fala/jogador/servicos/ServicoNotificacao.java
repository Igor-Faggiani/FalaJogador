package fala.jogador.servicos;

public interface ServicoNotificacao {
    void enviarNotificacao(Long idDestinatario, String mensagem);
}