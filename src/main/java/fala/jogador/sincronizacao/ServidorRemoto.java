package fala.jogador.sincronizacao;

import java.util.List;

public interface ServidorRemoto {

    boolean enviar(ItemOffline item);

    List<ItemOffline> buscarAtualizados(List<Long> idsLocais);
}
