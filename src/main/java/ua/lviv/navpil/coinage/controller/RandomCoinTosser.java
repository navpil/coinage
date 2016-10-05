package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Side;

class RandomCoinTosser implements CoinTosser {

    @Override
    public Side toss(Coin coin) {
        return Side.random();
    }
}
