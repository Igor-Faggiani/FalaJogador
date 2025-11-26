package fala.jogador.servicos;

import java.time.LocalDateTime;

public interface RepositorioAgendamento {
    boolean existeConflito(Long idPersonal, LocalDateTime dataHora);
    void salvar(Agendamento agendamento);
}