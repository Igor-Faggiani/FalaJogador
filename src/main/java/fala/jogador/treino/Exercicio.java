package fala.jogador.treino;

public class Exercicio {

    private Long id;
    private String nome;
    private int series;
    private int repeticoes;
    private boolean concluido;

    public Exercicio(String nome, int series, int repeticoes) {
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
        this.concluido = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public int getSeries() { return series; }
    public int getRepeticoes() { return repeticoes; }
    public boolean isConcluido() { return concluido; }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
}

