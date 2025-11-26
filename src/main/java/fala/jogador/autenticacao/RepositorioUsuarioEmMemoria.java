package fala.jogador.autenticacao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class RepositorioUsuarioEmMemoria implements RepositorioUsuario {

    private final Map<Long, Usuario> banco = new HashMap<>();
    private final AtomicLong geradorId = new AtomicLong(1);

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return banco.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(geradorId.getAndIncrement());
        }
        banco.put(usuario.getId(), usuario);
        return usuario;
    }
}