package fala.jogador.igor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import fala.jogador.aluno.Aluno;
import fala.jogador.aluno.RepositorioAluno;
import fala.jogador.autenticacao.ExcecaoSenhaIncorreta;
import fala.jogador.autenticacao.ExcecaoUsuarioBloqueado;
import fala.jogador.autenticacao.RepositorioUsuario;
import fala.jogador.autenticacao.ServicoAutenticacao;
import fala.jogador.autenticacao.Usuario;
import fala.jogador.autenticacao.UtilCriptografia;
import fala.jogador.servicos.Agendamento;
import fala.jogador.servicos.RepositorioAgendamento;
import fala.jogador.servicos.ServicoAgendamento;
import fala.jogador.servicos.ServicoNotificacao;

class AuthAgendaServiceTest_Igor {

    @Mock private RepositorioUsuario mockRepoUsuario;
    @Mock private UtilCriptografia mockCriptografia;
    @Mock private RepositorioAgendamento mockRepoAgendamento;
    @Mock private ServicoNotificacao mockServicoNotificacao;
    @Mock private RepositorioAluno mockRepoAluno;

    private ServicoAutenticacao servicoAuth;
    private ServicoAgendamento servicoAgenda;
    private final String SENHA = "Senha@123";
    private final String EMAIL = "aluno@teste.com";

    // ... (Setup: Servicos, Mocks) ...

    // --- Testes de Autenticação (RT-11) ---

    // CT-21 (RT-11): Autenticar com sucesso.
    @Test
    void teste_Igor_RT11_CT21_deveAutenticarComSucesso() {
        // Resultado Esperado: Retorno de token JWT/Sessão válida[cite: 220].
        // (Simulado com retorno True)
        // ... (Corpo do teste) ...
    }

    // CT-22 (RT-11): Falhar autenticação (senha incorreta).
    @Test
    void teste_Igor_RT11_CT22_deveFalharAutenticacaoComSenhaIncorreta() {
        // ... (Corpo do teste) ...
        // Resultado Esperado: Mensagem de erro "Credenciais inválidas"[cite: 220].
    }

    // CT-23 (RT-11): Bloquear conta após 5 tentativas inválidas.
    @Test
    void teste_Igor_RT11_CT23_deveBloquearApos5Tentativas() {
        // ... (Corpo do teste) ...
        // Resultado Esperado: Na 5º tentativa, conta recebe status "Bloqueada"[cite: 220].
    }

    // CT-24 (RT-11): Impedir login de conta bloqueada (mesmo com senha correta).
    @Test
    void teste_Igor_RT11_CT24_deveImpedirLoginDeContaBloqueada() {
        // ... (Corpo do teste) ...
        // Resultado Esperado: Mensagem de erro "Conta bloqueada..."[cite: 220].
    }

    // --- Testes de Agendamento (RT-12) ---

    // CT-25 (RT-12): Agendar sessão em horário disponível.
    @Test
    void teste_Igor_RT12_CT25_deveAgendarSessaoEmHorarioDisponivel() {
        // ... (Corpo do teste) ...
        // Resultado Esperado: Agendamento criado com sucesso[cite: 224].
    }

    // CT-26 (RT-12): Impedir agendamento em horário conflitante (já ocupado).
    @Test
    void teste_Igor_RT12_CT26_deveImpedirAgendamentoEmHorarioConflitante() {
        // ... (Corpo do teste) ...
        // Resultado Esperado: Mensagem de erro "Horário indisponivel"[cite: 229].
    }

    // CT-27 (RT-12): Impedir agendamento no passado.
    @Test
    void teste_Igor_RT12_CT27_deveImpedirAgendamentoNoPassado() {
        // ... (Corpo do teste) ...
        // Resultado Esperado: Mensagem de erro "Não é possível agendar em datas passadas"[cite: 229].
    }
}