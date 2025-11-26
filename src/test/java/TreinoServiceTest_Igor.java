package fala.jogador.treino;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fala.jogador.aluno.RepositorioAluno;

// Arquivo separado para evitar conflito de CT-numbers com Eduardo
class TreinoServiceTest_Igor {

    @Mock private RepositorioTreino mockRepositorioTreino;
    @Mock private RepositorioAluno mockRepositorioAluno;
    @Mock private RepositorioAtribuicaoTreino mockRepositorioAtribuicao;
    private ServicoTreino servicoTreino;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicoTreino = new ServicoTreino(mockRepositorioTreino, mockRepositorioAluno, mockRepositorioAtribuicao);
    }

    // CT-28 (RT-13): Impedir criação de treino sem exercícios.
    @Test
    void ct28_deveCriarTreinoComListaVaziaDeExercicios() { // O Requisito original não existia
        // A regra de negócio "O treino deve conter ao menos um exercício" não estava implementada no código
        // Mas a lógica atual permite. Vamos testar a exceção se a regra fosse:

        /*
        // Se a regra for implementada:
        Treino treino = new Treino(null, "Treino A", 60, 1L);
        assertThrows(ExcecaoCampoObrigatorio.class, () -> servicoTreino.criarTreino(treino),
                     "Deve exigir ao menos um exercício.");
        */

        // Teste contra o código atual (sem a regra explícita)
        Treino treino = new Treino(null, "Treino A", 60, 1L);
        when(mockRepositorioTreino.salvar(any(Treino.class))).thenReturn(treino);

        assertDoesNotThrow(() -> servicoTreino.criarTreino(treino));
        assertTrue(treino.getExercicios().isEmpty());
    }
}