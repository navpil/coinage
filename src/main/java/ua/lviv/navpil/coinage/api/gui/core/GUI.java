package ua.lviv.navpil.coinage.api.gui.core;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Move;

import java.util.Collection;

/**
 * UI should implement this interface, so it can be used by the GUI controller
 */
public interface GUI {

    /**
     * Initialize the UI with the game instance. Used instead of constructor
     *
     * @param game game to instantiate with
     */
    void init(GameImpl game);

    /**
     * Sets the selection listener. Selection listener listens for selected positions on board or coins to be used
     *
     * @param selectionListener selection listener
     */
    void setSelectionListener(ItemSelectionListener selectionListener);

    /**
     * Sets the move attempt listener. For example when button "Move" is clicked, this listener should be called
     *
     * @param listener move attempt listener
     */
    void setMoveAttemptListener(MoveAttemptListener listener);

    /**
     * Sets the positions on the map which are selected
     *
     * @param positions map positions, such as A1, A2 etc
     */
    void setSelectedPositions(Collection<String> positions);

    /**
     * Sets the coin which is selected for some move. Null if there is no selection
     *
     * @param coinSize selected coin identified by size
     */
    void setSelectedCoinSize(CoinSize coinSize);

    /**
     * Sets the available moves user can legally do
     *
     * @param availableMoves collection of available moves
     */
    void setAvailableMoves(Collection<Move> availableMoves);

    /**
     * Whenever end of game happens this method is called
     */
    void endOfGame();
}
