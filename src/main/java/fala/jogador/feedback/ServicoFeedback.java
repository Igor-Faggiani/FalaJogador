package fala.jogador.feedback;

import fala.jogador.aluno.ExcecaoRegistroNaoEncontrado;
import fala.jogador.aluno.RepositorioAluno;

public class ServicoFeedback {

    private final RepositorioFeedback repositorioFeedback;
    private final RepositorioAluno repositorioAluno; // CORREÇÃO

    public ServicoFeedback(RepositorioFeedback repositorioFeedback,
                           RepositorioAluno repositorioAluno) { // CORREÇÃO
        this.repositorioFeedback = repositorioFeedback;
        this.repositorioAluno = repositorioAluno;
    }

    public Feedback enviarFeedback(Long idAluno, String mensagem) {

        repositorioAluno.buscarPorId(idAluno)
                .orElseThrow(() -> new ExcecaoRegistroNaoEncontrado(
                        "Aluno não encontrado. Impossível enviar feedback."));

        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("A mensagem do feedback não pode estar vazia.");
        }

        Feedback feedback = new Feedback(idAluno, mensagem);
        repositorioFeedback.salvar(feedback);
        return feedback;
    }
}