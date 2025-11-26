package fala.jogador.desempenho;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fala.jogador.aluno.Aluno;
import fala.jogador.aluno.RepositorioAluno;
import fala.jogador.aluno.RepositorioAlunoEmMemoria;
import fala.jogador.aluno.StatusDesempenho;

class AvaliacaoFisicaIT_Endrik {

    private RepositorioAvaliacaoFisicaEmMemoria repoAvaliacao;
    private RepositorioAluno repoAluno;
    private ServicoAvaliacaoFisica servicoAvaliacao;
    private Long idAluno;

    @BeforeEach
    void setUp() {
        repoAvaliacao = new RepositorioAvaliacaoFisicaEmMemoria();
        repoAluno = new RepositorioAlunoEmMemoria();
        UtilIMC utilIMC = new UtilIMC();
        servicoAvaliacao = new ServicoAvaliacaoFisica(repoAvaliacao, repoAluno, utilIMC);

        Aluno aluno = new Aluno(null, "Teste", "1", "e", "t", "e", "h");
        idAluno = repoAluno.salvar(aluno).getId();
    }

    // CT-11 (RT-06): Persistir avaliação com IMC calculado.
    @Test
    void teste_Endrik_RT06_CT11_deveSalvarAvaliacaoComIMCCorretamente() {
        servicoAvaliacao.salvarAvaliacao(idAluno, 80.0, 1.80, 15.0, "ok");
        AvaliacaoFisica avaliacao = repoAvaliacao.listarPorAluno(idAluno).get(0);

        // Resultado Esperado: registro salvo com imc = 24.69[cite: 114].
        assertEquals(24.69, avaliacao.getImc(), 0.01);
    }

    // CT-12 (RT-06): Bloquear medidas inválidas (peso=0).
    @Test
    void teste_Endrik_RT06_CT12_deveLancarExcecaoParaMedidasInvalidas() {
        // Resultado Esperado: erro de validação (não salvar)[cite: 121].
        assertThrows(IllegalArgumentException.class, () ->
                servicoAvaliacao.salvarAvaliacao(idAluno, 0.0, 1.70, 15.0, "erro"));
    }

    // CT-13 (RT-07): Atualização positiva de desempenho (gorduraAnterior=20% -> gordura=15%).
    @Test
    void teste_Endrik_RT07_CT13_deveAtualizarDesempenhoParaMelhorando() {
        servicoAvaliacao.salvarAvaliacao(idAluno, 80.0, 1.80, 20.0, "base");
        servicoAvaliacao.salvarAvaliacao(idAluno, 78.0, 1.80, 15.0, "melhora");

        Aluno alunoAtualizado = repoAluno.buscarPorId(idAluno).get();
        // Resultado Esperado: Desempenho do aluno atualizado para "Melhorando"[cite: 129].
        assertEquals(StatusDesempenho.MELHORANDO, alunoAtualizado.getDesempenhoAtual());
    }

    // CT-14 (RT-07): Desempenho mantido sem variação (gorduraAnterior=18% -> gordura=18%).
    @Test
    void teste_Endrik_RT07_CT14_deveManterDesempenhoEstavel() {
        servicoAvaliacao.salvarAvaliacao(idAluno, 80.0, 1.80, 18.0, "base");
        servicoAvaliacao.salvarAvaliacao(idAluno, 80.0, 1.80, 18.0, "mantida");

        Aluno alunoAtualizado = repoAluno.buscarPorId(idAluno).get();
        // Resultado Esperado: Desempenho do aluno permanece "Estável"[cite: 129].
        assertEquals(StatusDesempenho.ESTAVEL, alunoAtualizado.getDesempenhoAtual());
    }
}