package fala.jogador.desempenho;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepositorioAvaliacaoFisicaEmMemoria implements RepositorioAvaliacaoFisica {

    private final List<AvaliacaoFisica> lista = new ArrayList<>();

    @Override
    public void salvar(AvaliacaoFisica avaliacao) {
        lista.add(avaliacao);
    }

    @Override
    public List<AvaliacaoFisica> listarPorAluno(Long idAluno) {
        return lista.stream()
                .filter(a -> a.getIdAluno().equals(idAluno))
                .sorted(Comparator.comparing(AvaliacaoFisica::getDataAvaliacao))
                .toList();
    }
}