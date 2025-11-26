package fala.jogador.treino;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import fala.jogador.aluno.RepositorioAluno;

class TreinoServiceTest_Eduardo {

    @Mock private RepositorioTreino mockRepositorioTreino;
    @Mock private RepositorioAluno mockRepositorioAluno;
    @Mock private RepositorioAtribuicaoTreino mockRepositorioAtribuicao;
    private ServicoTreino servicoTreino;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicoTreino = new ServicoTreino(mockRepositorioTreino, mockRepositorioAluno, mockRepositorioAtribuicao);
    }

    // CT-27 (RT-01): Criar treino com dados válidos.
    @Test
    void teste_Eduardo_RT01_CT27_deveCriarTreinoComSucesso() {
        Treino treino = new Treino(null, "Treino A", 60, 1L);
        treino.adicionarExercicio(new Exercicio("Ex1", 3, 10));
        when(mockRepositorioTreino.salvar(any(Treino.class))).thenReturn(treino);

        // Resultado Esperado: Treino persistido[cite: 179].
        assertDoesNotThrow(() -> servicoTreino.criarTreino(treino));
        verify(mockRepositorioTreino, times(1)).salvar(treino);
    }

    // CT-28 (RT-01): Criar treino com número de séries = 0 (valor inválido).
    @Test
    void teste_Eduardo_RT01_CT28_deveLancarExcecaoParaNumeroDeSeriesZero() {
        Treino treino = new Treino(null, "Treino A", 60, 1L);
        treino.adicionarExercicio(new Exercicio("Ex1", 0, 10));

        // Resultado Esperado: Validação "Número de séries inválido"[cite: 179].
        assertThrows(ExcecaoNumeroSeriesInvalido.class, () -> servicoTreino.criarTreino(treino));
    }

    // CT-29 (RT-01): Marcar todos os exercícios e salvar (Treino Concluido).
    @Test
    void teste_Eduardo_RT01_CT29_deveMarcarTreinoComoConcluidoQuandoTodosExerciciosTerminam() {
        Treino treino = new Treino(1L, "T", 60, 1L);
        Exercicio ex1 = new Exercicio("Ex1", 3, 10); ex1.setId(10L);
        Exercicio ex2 = new Exercicio("Ex2", 3, 10); ex2.setId(20L);
        treino.adicionarExercicio(ex1);
        treino.adicionarExercicio(ex2);

        when(mockRepositorioTreino.buscarPorIdExercicio(anyLong())).thenReturn(Optional.of(treino));

        servicoTreino.marcarExercicioConcluido(10L, true);
        servicoTreino.marcarExercicioConcluido(20L, true);

        // Resultado Esperado: Estado do treino atualizado para "Concluido"[cite: 183].
        assertTrue(treino.isConcluido());
    }

    // CT-30 (RT-01): Garantir erro para exercício inexistente.
    @Test
    void teste_Eduardo_RT01_CT30_deveLancarExcecaoParaExercicioInexistente() {
        when(mockRepositorioTreino.buscarPorIdExercicio(999L)).thenReturn(Optional.empty());

        // Resultado Esperado: RegistroNaoEncontradoException[cite: 183].
        assertThrows(ExcecaoExercicioNaoEncontrado.class, () ->
                servicoTreino.marcarExercicioConcluido(999L, true));
    }
}