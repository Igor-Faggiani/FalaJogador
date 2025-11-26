package fala.jogador.aluno;

import java.util.Objects;

public class Aluno {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private String senhaHash;
    private int nivel;
    private StatusDesempenho desempenhoAtual;

    public Aluno(Long id, String nome, String cpf, String email, String telefone,
                 String endereco, String senhaHash) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.senhaHash = senhaHash;
        this.nivel = 1;
        this.desempenhoAtual = StatusDesempenho.ESTAVEL;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
    public String getSenhaHash() { return senhaHash; }
    public int getNivel() { return nivel; }
    public StatusDesempenho getDesempenhoAtual() { return desempenhoAtual; }

    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void promoverNivel() { this.nivel++; }
    public void setDesempenhoAtual(StatusDesempenho desempenhoAtual) {
        this.desempenhoAtual = desempenhoAtual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno)) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(id, aluno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}