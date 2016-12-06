package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.*;

public interface Game {

    Result slap();

    Result pass();

    Result pay(CoinSize coinSize);

    Result place(CoinSize coinSize, String position);

    Result move(String from, String to);

    Result capture(String pos);

    GameState getState();

}
