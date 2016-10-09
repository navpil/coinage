package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.model.Coin;

public interface ItemSelectionListener {

    void itemSelected(String position);

    void itemSelected(Coin coin);
}
