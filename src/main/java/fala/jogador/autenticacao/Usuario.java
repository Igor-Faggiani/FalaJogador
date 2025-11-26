package fala.jogador.autenticacao;

public class Usuario {

    private Long id;
    private String email;
    private String senhaHash;
    private boolean bloqueado;
    private int tentativasFalha;

    public Usuario(Long id, String email, String senhaHash) {
        this.id = id;
        this.email = email;
        this.senhaHash = senhaHash;
        this.bloqueado = false;
        this.tentativasFalha = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public String getSenhaHash() { return senhaHash; }

    public boolean isBloqueado() { return bloqueado; }
    public int getTentativasFalha() { return tentativasFalha; }

    public void incrementarTentativas() { this.tentativasFalha++; }
    public void resetarTentativas() { this.tentativasFalha = 0; }

    public void bloquear() { this.bloqueado = true; }
}

