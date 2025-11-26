package fala.jogador.sincronizacao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SincronizacaoIT_Eduardo {

    @Mock private ServidorRemoto mockServidorRemoto;
    private ServicoSincronizacao servicoSincronizacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicoSincronizacao = new ServicoSincronizacao(mockServidorRemoto);
    }

    // CT-33 (RT-01): Validar se alterações feitas em modo offline são armazenadas localmente.
    @Test
    void teste_Eduardo_RT01_CT33_naoDeveSincronizarOffline() {
        ItemOffline itemPendente = new ItemOffline(1L, "Dados Alterados");
        itemPendente.setSincronizado(false);

        // Simula que o servidor remoto está inacessível (Offline)
        when(mockServidorRemoto.enviar(any(ItemOffline.class))).thenReturn(false);

        List<Long> sincronizados = servicoSincronizacao.sincronizarPendentes(List.of(itemPendente));

        // Resultado Esperado: Sem tentativa de sincronização enquanto offline[cite: 193].
        assertTrue(sincronizados.isEmpty());
    }

    // CT-34 (RT-01): Verificar sincronização automática após reconexão.
    @Test
    void teste_Eduardo_RT01_CT34_deveSincronizarPendentesOnline() {
        ItemOffline itemPendente = new ItemOffline(1L, "Dados Alterados");
        ItemOffline itemSincronizado = new ItemOffline(2L, "Dados Antigos");
        itemSincronizado.setSincronizado(true); // Não deve ser enviado

        // Simula sucesso no envio
        when(mockServidorRemoto.enviar(itemPendente)).thenReturn(true);

        List<Long> sincronizados = servicoSincronizacao.sincronizarPendentes(Arrays.asList(itemPendente, itemSincronizado));

        // Resultado Esperado: Sistema envia alterações pendentes[cite: 193].
        verify(mockServidorRemoto, times(1)).enviar(itemPendente);
        assertEquals(1, sincronizados.size());
    }
}