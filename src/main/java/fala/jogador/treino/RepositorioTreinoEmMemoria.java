package fala.jogador.treino;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class RepositorioTreinoEmMemoria implements RepositorioTreino {

    private final Map<Long, Treino> bancoTreinos = new HashMap<>();
    private final AtomicLong nextTreinoId = new AtomicLong(1);
    private final AtomicLong nextExercicioId = new AtomicLong(1);

    @Override
    public Treino salvar(Treino treino) {
        if (treino.getId() == null) {
            treino.setId(nextTreinoId.getAndIncrement());
        }
        for (Exercicio e : treino.getExercicios()) {
            if (e.getId() == null) {
                e.setId(nextExercicioId.getAndIncrement());
            }
        }
        bancoTreinos.put(treino.getId(), treino);
        return treino;
    }

    @Override
    public Optional<Treino> buscarPorId(Long id) { // CORREÇÃO
        return Optional.ofNullable(bancoTreinos.get(id));
    }

    @Override
    public Optional<Treino> buscarPorIdExercicio(Long idExercicio) {
        if (idExercicio == null) return Optional.empty();

        for (Treino treino : bancoTreinos.values()) {
            boolean achou = treino.getExercicios().stream()
                    .anyMatch(ex -> idExercicio.equals(ex.getId()));

            if (achou) {
                return Optional.of(treino);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Treino> buscarPorExercicioId(Long idExercicio) {
        return buscarPorIdExercicio(idExercicio);
    }

    @Override
    public List<Treino> listarTodos() {
        return new ArrayList<>(bancoTreinos.values());
    }
}