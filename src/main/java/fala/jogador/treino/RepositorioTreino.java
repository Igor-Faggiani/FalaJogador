package fala.jogador.treino;

import java.util.List;
import java.util.Optional;

public interface RepositorioTreino {

    Treino salvar(Treino treino);
    Optional<Treino> buscarPorId(Long id);
    Optional<Treino> buscarPorIdExercicio(Long idExercicio);

    Optional<Treino> buscarPorExercicioId(Long idExercicio);

    List<Treino> listarTodos();
}