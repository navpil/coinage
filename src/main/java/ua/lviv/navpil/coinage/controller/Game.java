package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.*;

public interface Game {

    public Result slap();

    public Result pass();

    public Result pay(CoinSize coinSize);

    public Result place(CoinSize coinSize, String position);

    public Result move(String from, String to);

    public Result capture(String pos);

    public GameState getState();

}
