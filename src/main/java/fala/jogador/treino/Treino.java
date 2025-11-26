package fala.jogador.treino;

import java.util.ArrayList;
import java.util.List;

public class Treino {

    private Long id;
    private String nome;
    private int duracaoMinutos;
    private Long idPersonal;
    private boolean concluido;
    private final List<Exercicio> exercicios = new ArrayList<>();

    public Treino(Long id, String nome, int duracaoMinutos, Long idPersonal) {
        this.id = id;
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.idPersonal = idPersonal;
        this.concluido = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public Long getIdPersonal() { return idPersonal; }
    public boolean isConcluido() { return concluido; }

    public List<Exercicio> getExercicios() { return exercicios; }

    public void adicionarExercicio(Exercicio exercicio) {
        this.exercicios.add(exercicio);
    }

    public void atualizarConclusao() {
        this.concluido = exercicios.stream().allMatch(Exercicio::isConcluido);
    }
}
