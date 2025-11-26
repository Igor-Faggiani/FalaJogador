package fala.jogador.sincronizacao;

import java.util.Collections;
import java.util.List;

public class ServidorRemotoSimulacao implements ServidorRemoto {

    @Override
    public boolean enviar(ItemOffline item) {
        System.out.println("   [Simulação] Enviando item ID " + item.getId() + " para o servidor...");

        return true;
    }

    @Override
    public List<ItemOffline> buscarAtualizados(List<Long> idsLocais) {
        System.out.println("   [Simulação] Buscando atualizações no servidor...");
        return Collections.emptyList();
    }
}