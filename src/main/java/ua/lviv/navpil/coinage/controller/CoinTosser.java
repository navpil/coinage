package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.Side;

public interface CoinTosser {
    Side toss(Coin coin);
}
