package fala.jogador.desempenho;

import java.time.LocalDate;

public class AvaliacaoFisica {

    private Long idAluno;
    private double peso;
    private double altura;
    private double gordura;
    private double imc;
    private String observacao;
    private LocalDate dataAvaliacao;

    public AvaliacaoFisica(Long idAluno, double peso, double altura,
                           double gordura, String observacao) {
        this.idAluno = idAluno;
        this.peso = peso;
        this.altura = altura;
        this.gordura = gordura;
        this.observacao = observacao;
        this.imc = 0.0;
        this.dataAvaliacao = LocalDate.now();
    }

    public Long getIdAluno() { return idAluno; }
    public double getPeso() { return peso; }
    public double getAltura() { return altura; }
    public double getGordura() { return gordura; }
    public double getImc() { return imc; }
    public String getObservacao() { return observacao; }
    public LocalDate getDataAvaliacao() { return dataAvaliacao; }

    public void setImc(double imc) { this.imc = imc; }
}