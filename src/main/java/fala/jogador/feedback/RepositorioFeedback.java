package fala.jogador.feedback;

import java.util.List;

public interface RepositorioFeedback {

    Feedback salvar(Feedback feedback);
    List<Feedback> listarPorAluno(Long idAluno);
}