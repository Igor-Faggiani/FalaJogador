package fala.jogador.aluno;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import fala.jogador.autenticacao.UtilCriptografia;

class ServicoAlunoTest_Eduardo {

    @Mock private RepositorioAluno mockRepositorio;
    @Mock private UtilCriptografia mockCriptografia;
    private ServicoAluno servicoAluno;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicoAluno = new ServicoAluno(mockRepositorio, mockCriptografia);
    }

    // CT-23 (RT-01): Atualizar telefone com formato válido.
    @Test
    void teste_Eduardo_RT01_CT23_deveAtualizarTelefoneDeAlunoExistente() {
        Long id = 1L;
        Aluno aluno = new Aluno(id, "Nome", "123", "email@antigo.com", "999", "End", "hash");
        when(mockRepositorio.buscarPorId(id)).thenReturn(Optional.of(aluno));
        when(mockRepositorio.salvar(any(Aluno.class))).thenReturn(aluno);

        aluno.setTelefone("987654321");

        // Resultado Esperado: BD atualizado[cite: 166].
        assertDoesNotThrow(() -> servicoAluno.atualizarAluno(aluno));
        assertEquals("987654321", aluno.getTelefone());
    }

    // CT-24 (RT-01): Atualizar CPF para um já existente (duplicidade).
    @Test
    void teste_Eduardo_RT01_CT24_deveLancarExcecaoAoAtualizarCPFDplicado() {
        Long id2 = 2L;
        Aluno aluno2 = new Aluno(id2, "Nome2", "222", "e2@b.com", "t", "e", "h");

        when(mockRepositorio.existePorCpfEIdDiferente("111", id2)).thenReturn(true);
        when(mockRepositorio.buscarPorId(id2)).thenReturn(Optional.of(aluno2));

        aluno2.setCpf("111");

        // Resultado Esperado: DuplicateFieldException ("CPF ja cadastrado")[cite: 170].
        assertThrows(ExcecaoCampoDuplicado.class, () -> servicoAluno.atualizarAluno(aluno2));
    }
}