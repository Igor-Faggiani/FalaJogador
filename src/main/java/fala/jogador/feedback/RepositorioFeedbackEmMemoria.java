package fala.jogador.feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class RepositorioFeedbackEmMemoria implements RepositorioFeedback {

    private final List<Feedback> lista = new ArrayList<>();
    private final AtomicLong geradorId = new AtomicLong(1);

    @Override
    public Feedback salvar(Feedback feedback) {
        if (feedback.getId() == null) {
            feedback.setId(geradorId.getAndIncrement());
        }
        lista.add(feedback);
        return feedback;
    }

    @Override
    public List<Feedback> listarPorAluno(Long idAluno) {
        return lista.stream()
                .filter(f -> f.getIdAluno().equals(idAluno))
                .toList();
    }
}