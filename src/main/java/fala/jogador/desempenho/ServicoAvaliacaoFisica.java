package fala.jogador.desempenho;

import fala.jogador.aluno.Aluno;
import fala.jogador.aluno.RepositorioAluno;
import fala.jogador.aluno.StatusDesempenho;

import java.util.List;

public class ServicoAvaliacaoFisica {

    private final RepositorioAvaliacaoFisica repositorioAvaliacao;
    private final RepositorioAluno repositorioAluno;
    private final UtilIMC utilIMC;

    public ServicoAvaliacaoFisica(RepositorioAvaliacaoFisica repositorioAvaliacao,
                                  RepositorioAluno repositorioAluno,
                                  UtilIMC utilIMC) {
        this.repositorioAvaliacao = repositorioAvaliacao;
        this.repositorioAluno = repositorioAluno;
        this.utilIMC = utilIMC;
    }

    public AvaliacaoFisica salvarAvaliacao(Long idAluno, double peso,
                                           double altura, double gordura,
                                           String observacao) {

        if (peso <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Medidas inválidas.");
        }

        Aluno aluno = repositorioAluno.buscarPorId(idAluno)
                .orElseThrow(() ->
                        new IllegalArgumentException("Aluno inexistente."));

        AvaliacaoFisica avaliacao =
                new AvaliacaoFisica(idAluno, peso, altura, gordura, observacao);
        double imc = utilIMC.calcularIMC(peso, altura);
        avaliacao.setImc(imc);

        repositorioAvaliacao.salvar(avaliacao);
        atualizarDesempenhoAluno(aluno, idAluno);
        return avaliacao;
    }

    private void atualizarDesempenhoAluno(Aluno aluno, Long idAluno) {
        List<AvaliacaoFisica> avaliacoes =
                repositorioAvaliacao.listarPorAluno(idAluno);

        if (avaliacoes.size() < 2) return;

        AvaliacaoFisica penultima = avaliacoes.get(avaliacoes.size() - 2);
        AvaliacaoFisica ultima = avaliacoes.get(avaliacoes.size() - 1);

        if (ultima.getGordura() < penultima.getGordura()) {
            aluno.setDesempenhoAtual(StatusDesempenho.MELHORANDO);
        } else if (ultima.getGordura() == penultima.getGordura()) {
            aluno.setDesempenhoAtual(StatusDesempenho.ESTAVEL);
        } else {
            aluno.setDesempenhoAtual(StatusDesempenho.PIORANDO);
        }

        repositorioAluno.salvar(aluno);
    }
}