package fala.jogador.servicos;

import java.time.LocalDateTime;
import fala.jogador.aluno.RepositorioAluno;

public class ServicoAgendamento {
    private final RepositorioAgendamento repositorioAgendamento;
    private final ServicoNotificacao servicoNotificacao;
    private final RepositorioAluno repositorioAluno;

    public ServicoAgendamento(RepositorioAgendamento repo, ServicoNotificacao notif, RepositorioAluno repoAluno) {
        this.repositorioAgendamento = repo;
        this.servicoNotificacao = notif;
        this.repositorioAluno = repoAluno;
    }

    public Agendamento agendarSessao(Long idAluno, Long idPersonal, LocalDateTime dataHora) {
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar em datas passadas.");
        }
        if (repositorioAgendamento.existeConflito(idPersonal, dataHora)) {
            throw new IllegalArgumentException("Horário indisponível.");
        }

        String nomeAluno = repositorioAluno.buscarPorId(idAluno).orElseThrow().getNome();

        Agendamento agendamento = new Agendamento(idAluno, idPersonal, dataHora);
        repositorioAgendamento.salvar(agendamento);
        servicoNotificacao.enviarNotificacao(idPersonal, "Novo agendamento de " + nomeAluno);

        return agendamento;
    }
}