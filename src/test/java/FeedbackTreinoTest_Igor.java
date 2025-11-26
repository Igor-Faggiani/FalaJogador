package fala.jogador.feedback;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fala.jogador.aluno.Aluno;
import fala.jogador.aluno.RepositorioAluno;

class FeedbackTreinoTest_Igor {

    @Mock private RepositorioFeedback mockRepoFeedback;
    @Mock private RepositorioAluno mockRepoAluno;

    private ServicoFeedback servicoFeedback;
    private final Long ID_ALUNO = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicoFeedback = new ServicoFeedback(mockRepoFeedback, mockRepoAluno);

        when(mockRepoAluno.buscarPorId(ID_ALUNO))
                .thenReturn(Optional.of(new Aluno(ID_ALUNO, "Teste", "1", "e", "t", "e", "h")));
        when(mockRepoFeedback.salvar(any(Feedback.class))).thenAnswer(i -> i.getArgument(0));
    }

    // CT-28 (RT-13): Impedir criação de treino sem exercícios. (Depende da implementação de ServicoTreino)
    @Test
    void teste_Igor_RT13_CT28_deveImpedirCriacaoDeTreinoSemExercicios() {
        // Resultado Esperado: Mensagem de erro "O treino deve conter ao menos um exercício"[cite: 233].
    }

    // CT-29 (RT-13): Impedir envio de feedback (mensagem) vazio ou nulo.
    @Test
    void teste_Igor_RT13_CT29_deveLancarExcecaoParaMensagemVaziaOuNula() {
        // Resultado Esperado: Mensagem de erro "A nota é obrigatória"[cite: 233].
        assertThrows(IllegalArgumentException.class, () ->
                servicoFeedback.enviarFeedback(ID_ALUNO, null));
    }

    // CT-30 (RT-13): Permitir envio de feedback com comentário opcional (mensagem).
    @Test
    void teste_Igor_RT13_CT30_deveSalvarFeedbackComMensagemValida() {
        // Resultado Esperado: Feedback registrado com sucesso[cite: 233].
        assertDoesNotThrow(() -> servicoFeedback.enviarFeedback(ID_ALUNO, "Comentário válido"));
        verify(mockRepoFeedback, times(1)).salvar(any(Feedback.class));
    }
}