package fala.jogador.autenticacao;

import org.mindrot.jbcrypt.BCrypt;

public class UtilCriptografia {

    public String gerarHashSenha(String senhaPura) {
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
    }

    public boolean verificarSenha(String senhaPura, String hash) {
        if (senhaPura == null || hash == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(senhaPura, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}