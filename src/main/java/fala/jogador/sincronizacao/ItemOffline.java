package fala.jogador.sincronizacao;

public class ItemOffline {

    private Long id;
    private String conteudo;
    private boolean sincronizado;

    public ItemOffline(Long id, String conteudo) {
        this.id = id;
        this.conteudo = conteudo;
        this.sincronizado = false;
    }

    public Long getId() { return id; }
    public String getConteudo() { return conteudo; }
    public boolean isSincronizado() { return sincronizado; }

    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public void setSincronizado(boolean sincronizado) { this.sincronizado = sincronizado; }
}

