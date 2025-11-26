package fala.jogador.desempenho;

import java.util.List;

public interface RepositorioAvaliacaoFisica {

    void salvar(AvaliacaoFisica avaliacao);

    List<AvaliacaoFisica> listarPorAluno(Long idAluno);
}