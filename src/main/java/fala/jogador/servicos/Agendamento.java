package fala.jogador.servicos;

import java.time.LocalDateTime;

public class Agendamento {
    public Long idAluno;
    public Long idPersonal;
    public LocalDateTime dataHora;
    public String status;

    public Agendamento(Long idAluno, Long idPersonal, LocalDateTime dataHora) {
        this.idAluno = idAluno;
        this.idPersonal = idPersonal;
        this.dataHora = dataHora;
        this.status = "Pendente";
    }
}