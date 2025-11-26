package fala.jogador.treino;

import fala.jogador.aluno.RepositorioAluno;
import fala.jogador.aluno.ExcecaoRegistroNaoEncontrado;

import java.util.List;

public class ServicoTreino {

    private final RepositorioTreino repositorioTreino;
    private final RepositorioAluno repositorioAluno;
    private final RepositorioAtribuicaoTreino repositorioAtribuicao;

    public ServicoTreino(RepositorioTreino repositorioTreino,
                         RepositorioAluno repositorioAluno,
                         RepositorioAtribuicaoTreino repositorioAtribuicao) {
        if (repositorioTreino == null || repositorioAluno == null || repositorioAtribuicao == null) {
            throw new IllegalArgumentException("Repositórios não podem ser nulos.");
        }
        this.repositorioTreino = repositorioTreino;
        this.repositorioAluno = repositorioAluno;
        this.repositorioAtribuicao = repositorioAtribuicao;
    }


    public Treino criarTreino(Treino treino) {
        treino.getExercicios().forEach(ex -> {
            if (ex.getSeries() <= 0) {
                throw new ExcecaoNumeroSeriesInvalido("Número de séries deve ser maior que zero.");
            }
        });

        return repositorioTreino.salvar(treino);
    }

    public void marcarExercicioConcluido(Long idExercicio, boolean concluido) {
        Treino treino = repositorioTreino.buscarPorIdExercicio(idExercicio)
                .orElseThrow(() ->
                        new ExcecaoExercicioNaoEncontrado("Exercício não encontrado."));

        treino.getExercicios().stream()
                .filter(e -> idExercicio.equals(e.getId()))
                .findFirst()
                .ifPresent(e -> e.setConcluido(concluido));

        treino.atualizarConclusao();
        repositorioTreino.salvar(treino);
    }

    public void atribuirTreinoParaAlunos(Long idTreino, List<Long> idsAlunos) {
        Treino treino = repositorioTreino.buscarPorId(idTreino)
                .orElseThrow(() ->
                        new ExcecaoTreinoNaoEncontrado("Treino não encontrado."));

        for (Long idAluno : idsAlunos) {
            if (repositorioAluno.buscarPorId(idAluno).isEmpty()) {
                throw new ExcecaoRegistroNaoEncontrado("Aluno não encontrado: " + idAluno);
            }
            repositorioAtribuicao.salvarVinculo(idAluno, treino.getId());
        }
    }
}