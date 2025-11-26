package fala.jogador.aluno;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import fala.jogador.autenticacao.UtilCriptografia;

class ServicoAlunoTest_Endrik {

    @Mock private RepositorioAluno mockRepositorio;
    @Mock private UtilCriptografia mockCriptografia;
    private ServicoAluno servicoAluno;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicoAluno = new ServicoAluno(mockRepositorio, mockCriptografia);
    }

    // CT-01 (RT-01): Bloquear campos obrigatórios vazios.
    @Test
    void teste_Endrik_RT01_CT01_DeveBloquearCadastroComNomeVazio() {
        // Resultado Esperado: mensagem de campo obrigatório; sem persistência.
        assertThrows(ExcecaoCampoObrigatorio.class, () ->
                servicoAluno.cadastrarAluno("", "123", "a@b.com", null, null, "senha"));
    }

    // CT-02 (RT-01): Bloquear e-mail em formato inválido.
    @Test
    void teste_Endrik_RT01_CT02_DeveBloquearEmailComFormatoInvalido() {
        // Resultado Esperado: mensagem "formato de e-mail inválido".
        assertThrows(ExcecaoFormatoEmailInvalido.class, () ->
                servicoAluno.cadastrarAluno("Nome", "123", "email.invalido.com", null, null, "senha"));
    }

    // CT-05 (RT-03): Exibir diálogo de confirmação (confirmar=false).
    @Test
    void teste_Endrik_RT03_CT05_DeveLancarExcecaoSeNaoConfirmarExclusao() {
        when(mockRepositorio.buscarPorId(1L)).thenReturn(Optional.of(new Aluno(1L, "T", "C", "E", "T", "E", "S")));

        // Resultado Esperado: Sem remoção até confirmar.
        assertThrows(IllegalStateException.class, () ->
                servicoAluno.excluirAluno(1L, false));
        verify(mockRepositorio, never()).remover(any());
    }

    // CT-06 (RT-03): Excluir apenas após confirmar (confirmar=true).
    @Test
    void teste_Endrik_RT03_CT06_DeveRemoverAlunoSeConfirmarExclusao() {
        Long id = 1L;
        when(mockRepositorio.buscarPorId(id)).thenReturn(Optional.of(new Aluno(id, "T", "C", "E", "T", "E", "S")));

        // Resultado Esperado: aluno removido + mensagem de sucesso.
        assertDoesNotThrow(() -> servicoAluno.excluirAluno(id, true));
        verify(mockRepositorio, times(1)).remover(id);
    }
}