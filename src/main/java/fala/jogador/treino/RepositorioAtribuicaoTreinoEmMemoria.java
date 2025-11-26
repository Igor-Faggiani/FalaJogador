package fala.jogador.treino;

import java.util.*;

public class RepositorioAtribuicaoTreinoEmMemoria implements RepositorioAtribuicaoTreino {

    private final Map<Long, List<Long>> mapa = new HashMap<>();

    @Override
    public void salvarVinculo(Long idAluno, Long idTreino) {
        mapa.computeIfAbsent(idTreino, k -> new ArrayList<>()).add(idAluno);
    }

    public List<Long> listarAlunosPorTreino(Long idTreino) {
        return mapa.getOrDefault(idTreino, Collections.emptyList());
    }
}

