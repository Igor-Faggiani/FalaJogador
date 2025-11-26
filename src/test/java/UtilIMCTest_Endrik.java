package fala.jogador.desempenho;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilIMCTest_Endrik {

    private UtilIMC utilIMC;

    @BeforeEach
    void setUp() {
        utilIMC = new UtilIMC();
    }

    // CT-07 (RT-04): IMC numérico correto (peso=75, altura=1.80 -> 23.15).
    @Test
    void teste_Endrik_RT04_CT07_DeveCalcularIMCCorretamente() {
        double peso = 75.0;
        double altura = 1.80;
        double imcCalculado = utilIMC.calcularIMC(peso, altura);

        // Resultado Esperado: IMC=23.15 (duas casas)[cite: 103].
        assertEquals(23.15, imcCalculado, 0.001);
    }

    // CT-08 (RT-04): Classificação no limite (IMC=24.9 -> "Peso normal").
    @Test
    void teste_Endrik_RT04_CT08_DeveClassificarIMCLimiteComoPesoNormal() {
        double imc = 24.9;
        String classificacao = utilIMC.classificarIMC(imc);

        // Resultado Esperado: Mensagem "Peso normal"[cite: 103].
        assertEquals("Peso normal", classificacao);
    }
}