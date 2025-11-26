package fala.jogador.autenticacao;

import fala.jogador.aluno.ExcecaoCampoDuplicado;
import java.util.Optional;

public class ServicoAutenticacao {

    private final RepositorioUsuario repositorio;
    private final UtilCriptografia utilCriptografia;

    public ServicoAutenticacao(RepositorioUsuario repositorio,
                               UtilCriptografia utilCriptografia) {
        this.repositorio = repositorio;
        this.utilCriptografia = utilCriptografia;
    }

    public Usuario registrarUsuario(String email, String senhaPura) {
        if (repositorio.buscarPorEmail(email).isPresent()) {
            throw new ExcecaoCampoDuplicado("E-mail já cadastrado.");
        }

        String hash = utilCriptografia.gerarHashSenha(senhaPura);
        Usuario usuario = new Usuario(null, email, hash);
        return repositorio.salvar(usuario);
    }

    public boolean login(String email, String senhaDigitada) {
        Optional<Usuario> optUsuario = repositorio.buscarPorEmail(email);

        String hashCorreto = optUsuario.map(Usuario::getSenhaHash)
                .orElse("$2a$10$abcdefghijklmnopqrstuv.w");

        if (optUsuario.isPresent() && optUsuario.get().isBloqueado()) {
            throw new ExcecaoUsuarioBloqueado("Conta bloqueada.");
        }

        if (!utilCriptografia.verificarSenha(senhaDigitada, hashCorreto)) {
            if (optUsuario.isPresent()) {
                Usuario usuario = optUsuario.get();
                usuario.incrementarTentativas();
                if (usuario.getTentativasFalha() >= 5) {
                    usuario.bloquear();
                }
                repositorio.salvar(usuario);

                if (usuario.isBloqueado()) {
                    throw new ExcecaoUsuarioBloqueado("Conta bloqueada após 5 tentativas.");
                }
            }
            throw new ExcecaoSenhaIncorreta("Usuário ou senha inválidos.");
        }

        Usuario usuario = optUsuario.get();
        usuario.resetarTentativas();
        repositorio.salvar(usuario);
        return true;
    }
}