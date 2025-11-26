package fala.jogador.autenticacao;

import java.util.Optional;

public interface RepositorioUsuario {

    Optional<Usuario> buscarPorEmail(String email);
    Usuario salvar(Usuario usuario);
}