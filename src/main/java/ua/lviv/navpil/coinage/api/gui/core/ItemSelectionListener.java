package ua.lviv.navpil.coinage.api.gui.core;

import ua.lviv.navpil.coinage.model.Coin;

public interface ItemSelectionListener {

    void positionSelected(String position);

    void coinSelected(Coin coin);
}
