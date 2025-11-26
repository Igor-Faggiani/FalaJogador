package fala.jogador.autenticacao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthServiceTest_Eduardo {

    @Mock private RepositorioUsuario mockRepositorio;
    private UtilCriptografia criptografia;
    private ServicoAutenticacao servicoAutenticacao;
    private final String EMAIL = "usuario@teste.com";
    private final String SENHA_CORRETA = "Senha@123";
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criptografia = new UtilCriptografia();
        servicoAutenticacao = new ServicoAutenticacao(mockRepositorio, criptografia);
        String hashCorreto = criptografia.gerarHashSenha(SENHA_CORRETA);
        usuario = new Usuario(1L, EMAIL, hashCorreto);

        when(mockRepositorio.buscarPorEmail(EMAIL)).thenReturn(Optional.of(usuario));
        when(mockRepositorio.salvar(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));
    }

    // CT-21 (RT-01): Verificar que até 4 tentativas incorretas não bloqueiam a conta.
    @Test
    void teste_Eduardo_RT01_CT21_deveIncrementarTentativasEFalharAntesDoBloqueio() {
        // Executar 4 falhas (a 5ª falha o bloquearia)
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            assertThrows(ExcecaoSenhaIncorreta.class, () -> servicoAutenticacao.login(EMAIL, "senhaErrada" + finalI));
        }

        // Login correto (antes da 5ª falha)
        assertDoesNotThrow(() -> servicoAutenticacao.login(EMAIL, SENHA_CORRETA));
    }

    // CT-22 (RT-01): Verificar bloqueio após 5 tentativas incorretas.
    @Test
    void teste_Eduardo_RT01_CT22_deveBloquearContaAposCincoTentativasIncorretas() {
        // 4 Tentativas incorretas
        for (int i = 0; i < 4; i++) {
            assertThrows(ExcecaoSenhaIncorreta.class, () -> servicoAutenticacao.login(EMAIL, "senhaErrada"));
        }

        // 5ª Tentativa incorreta -> BLOQUEIO (Resultado Esperado: Exceção de bloqueio)[cite: 160].
        assertThrows(ExcecaoUsuarioBloqueado.class, () -> servicoAutenticacao.login(EMAIL, "senhaErrada"));

        // Próxima tentativa (mesmo que correta) -> Exceção de Bloqueado[cite: 160].
        assertThrows(ExcecaoUsuarioBloqueado.class, () -> servicoAutenticacao.login(EMAIL, SENHA_CORRETA));
    }

    // CT-25 (RT-01): Verificar que a senha não é armazenada em texto plano.
    @Test
    void teste_Eduardo_RT01_CT25_deveArmazenarHashValido() {
        // Resultado Esperado: Valor no campo senha não contém a string, contém hash (formato BCrypt)[cite: 170].
        String hashSalvo = usuario.getSenhaHash();
        assertTrue(hashSalvo.startsWith("$2a$") || hashSalvo.startsWith("$2y$"));
        assertFalse(hashSalvo.contains(SENHA_CORRETA));
    }

    // CT-26 (RT-01): Verificar verificação de senha (match/mismatch).
    @Test
    void teste_Eduardo_RT01_CT26_deveVerificarSenhaCorretaEIncorreta() {
        // Resultado Esperado: Com senha correta, verificação retorna true; com senha incorreta, retorna false[cite: 174].
        assertTrue(criptografia.verificarSenha(SENHA_CORRETA, usuario.getSenhaHash()));
        assertFalse(criptografia.verificarSenha("SenhaErrada", usuario.getSenhaHash()));
    }
}