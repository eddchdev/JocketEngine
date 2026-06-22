package jocketengine.scene.scenes;

import jocketengine.events.Event;

/**
 * Evento disparado quando o jogador coleta uma moeda. Exemplo de comunicação
 * desacoplada: a colisão dispara o evento e um listener atualiza o placar.
 *
 * @author Eddch
 */
public class CoinCollectedEvent extends Event {

    private final CoinEntity coin;

    public CoinCollectedEvent(CoinEntity coin) {
        this.coin = coin;
    }

    public CoinEntity getCoin() {
        return coin;
    }
}
