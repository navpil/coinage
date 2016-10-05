package ua.lviv.navpil.coinage.controller;

import ua.lviv.navpil.coinage.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Game {

    public Result slap();

    public Result pass();

    public Result pay(CoinSize coinSize);

    public Result place(CoinSize coinSize, String position);

    public Result move(String from, String to);

    public Result capture(String pos);

}
