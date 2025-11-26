package fala.jogador.treino;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fala.jogador.aluno.Aluno;
import fala.jogador.aluno.ExcecaoRegistroNaoEncontrado;
import fala.jogador.aluno.RepositorioAlunoEmMemoria;

class TreinoAtribuicaoIT_Eduardo {

    private RepositorioTreinoEmMemoria repoTreino;
    private RepositorioAlunoEmMemoria repoAluno;
    private RepositorioAtribuicaoTreinoEmMemoria repoAtribuicao;
    private ServicoTreino servicoTreino;

    private Long idTreinoExistente;
    private List<Long> idsAlunosExistentes;

    @BeforeEach
    void setUp() {
        repoTreino = new RepositorioTreinoEmMemoria();
        repoAluno = new RepositorioAlunoEmMemoria();
        repoAtribuicao = new RepositorioAtribuicaoTreinoEmMemoria();
        servicoTreino = new ServicoTreino(repoTreino, repoAluno, repoAtribuicao);

        // Setup: Criar Treino
        Treino treino = new Treino(null, "T1", 30, 1L);
        idTreinoExistente = repoTreino.salvar(treino).getId();

        // Setup: Criar Alunos
        Aluno a1 = repoAluno.salvar(new Aluno(null, "A1", "1", "e1", "t", "e", "h"));
        Aluno a2 = repoAluno.salvar(new Aluno(null, "A2", "2", "e2", "t", "e", "h"));
        idsAlunosExistentes = List.of(a1.getId(), a2.getId());
    }

    // CT-31 (RT-01): Validar atribuição de um treino único a vários alunos simultaneamente.
    @Test
    void teste_Eduardo_RT01_CT31_deveVincularTreinoAMultiplosAlunos() {
        servicoTreino.atribuirTreinoParaAlunos(idTreinoExistente, idsAlunosExistentes);

        // Resultado Esperado: Treino vinculado a cada aluno[cite: 189].
        assertEquals(2, repoAtribuicao.listarAlunosPorTreino(idTreinoExistente).size());
    }

    // CT-32 (RT-01): Verificar comportamento ao tentar atribuir treino inexistente.
    @Test
    void teste_Eduardo_RT01_CT32_deveLancarExcecaoParaTreinoInexistente() {
        Long idTreinoInexistente = 999L;

        // Resultado Esperado: Mensagem de erro "Treino não encontrado"[cite: 189].
        assertThrows(ExcecaoTreinoNaoEncontrado.class, () ->
                servicoTreino.atribuirTreinoParaAlunos(idTreinoInexistente, idsAlunosExistentes));
    }

    // (Extensão de CT-32) Deve lançar exceção se um aluno na lista for inexistente
    @Test
    void teste_Eduardo_RT01_CT32_deveLancarExcecaoParaAlunoInexistenteNaLista() {
        List<Long> ids = List.of(idsAlunosExistentes.get(0), 999L);

        assertThrows(ExcecaoRegistroNaoEncontrado.class, () ->
                servicoTreino.atribuirTreinoParaAlunos(idTreinoExistente, ids));
    }
}