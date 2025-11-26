package fala.jogador.autenticacao;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilCriptografiaTest_Endrik {

    private UtilCriptografia criptografia;
    private final String SENHA_PADRAO = "Endrik@123";

    @BeforeEach
    void setUp() {
        criptografia = new UtilCriptografia();
    }

    // CT-03 (RT-02): Verificar que senha não é texto puro (Hash).
    @Test
    void teste_Endrik_RT02_CT03_DeveRetornarHashParaSenhaPura() {
        // Assegurar que o valor persistido é hash[cite: 81, 170].
        String hash = criptografia.gerarHashSenha(SENHA_PADRAO);
        assertTrue(hash.startsWith("$2a$") || hash.startsWith("$2y$"));
        assertNotEquals(SENHA_PADRAO, hash);
    }

    // CT-04 (RT-02): Verificar que o hash varia com salt.
    @Test
    void teste_Endrik_RT02_CT04_DeveGerarHashesDiferentesParaMesmaSenha() {
        // Resultado Esperado: Hashes diferentes (salt aplicado)[cite: 89].
        String hash1 = criptografia.gerarHashSenha(SENHA_PADRAO);
        String hash2 = criptografia.gerarHashSenha(SENHA_PADRAO);
        assertNotEquals(hash1, hash2, "Hashes devem ser diferentes devido ao salt único.");
    }
}