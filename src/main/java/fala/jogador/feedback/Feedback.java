package fala.jogador.feedback;

import java.time.LocalDateTime;

public class Feedback {

    private Long id; // CORREÇÃO: Adicionada chave primária
    private Long idAluno;
    private String mensagem;
    private LocalDateTime dataHora;

    public Feedback(Long idAluno, String mensagem) {
        this.idAluno = idAluno;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; } // CORREÇÃO
    public void setId(Long id) { this.id = id; } // CORREÇÃO

    public Long getIdAluno() { return idAluno; }
    public String getMensagem() { return mensagem; }
    public LocalDateTime getDataHora() { return dataHora; }
}