package fala.jogador.sincronizacao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicoSincronizacao {

    private final ServidorRemoto servidorRemoto;

    public ServicoSincronizacao(ServidorRemoto servidorRemoto) {
        this.servidorRemoto = servidorRemoto;
    }

    public List<Long> sincronizarPendentes(List<ItemOffline> itens) {
        List<Long> sincronizados = new ArrayList<>();
        for (ItemOffline item : itens) {
            if (item.isSincronizado()) continue;

            try {
                boolean ok = servidorRemoto.enviar(item);
                if (ok) {
                    sincronizados.add(item.getId());
                }
            } catch (Exception e) {
                System.err.println("Falha ao sincronizar item " + item.getId() + ": " + e.getMessage());
            }
        }
        return sincronizados;
    }

    public List<ItemOffline> receberAtualizacoes(List<ItemOffline> itensLocais) {
        List<Long> ids = itensLocais.stream()
                .map(ItemOffline::getId)
                .collect(Collectors.toList());

        return servidorRemoto.buscarAtualizados(ids);
    }
}