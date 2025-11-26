package fala.jogador.aluno;

public enum StatusDesempenho {
    ESTAVEL("Estável"),
    MELHORANDO("Melhorando"),
    PIORANDO("Piorando");

    private final String descricao;

    StatusDesempenho(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}