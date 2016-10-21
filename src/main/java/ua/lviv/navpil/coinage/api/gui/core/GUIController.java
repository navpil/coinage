package ua.lviv.navpil.coinage.api.gui.core;

import ua.lviv.navpil.coinage.controller.GameImpl;
import ua.lviv.navpil.coinage.controller.Result;
import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;
import ua.lviv.navpil.coinage.model.Move;

import java.util.Collections;
import java.util.List;

public class GUIController implements ItemSelectionListener, MoveAttemptListener {

    private final boolean autoPass;
    private final boolean autoSlap;
    private final GUI gui;
    private final SelectedItemsController selectedItemsController = new SelectedItemsController();
    private final GameImpl game;

    public GUIController(final GUI gui, final GameImpl game, boolean autoPass, boolean autoSlap) {
        this.gui = gui;
        this.game = game;
        this.autoPass = autoPass;
        this.autoSlap = autoSlap;
        if (autoSlap) {
            game.slap();
        }
        gui.init(game);
        gui.setSelectionListener(this);
        gui.setAvailableMoves(game.state().getAvailableMoves());
        gui.setMoveAttemptListener(this);

    }
    public GUIController(final GUI gui, final GameImpl game) {
        this(gui, game, true, true);
    }

    @Override
    public void positionSelected(String position) {
        selectedItemsController.addPosition(position);
        gui.setSelectedPositions(selectedItemsController.getPositions());
        gui.setSelectedCoinSize(selectedItemsController.getCoinSize());
    }

    @Override
    public void coinSelected(Coin coin) {
        if (coin.getSide() == game.state().getActivePlayer()) {
            selectedItemsController.addCoin(coin);
            gui.setSelectedPositions(selectedItemsController.getPositions());
            gui.setSelectedCoinSize(selectedItemsController.getCoinSize());
        }
    }

    @Override
    public void moveAttempted(Move move) {
        Result result = null;
        switch (move) {
            case PASS:
                result = game.pass();
                break;
            case SLAP:
                result = game.slap();
                break;
            case CAPTURE:
                List<String> capturePositions = selectedItemsController.getPositions();
                if (!capturePositions.isEmpty()) {
                    result = game.capture(capturePositions.get(0));
                }
                break;
            case MOVE:
                List<String> positions = selectedItemsController.getPositions();
                if (positions.size() == 2) {
                    String from = positions.get(0);
                    String to = positions.get(1);
                    if (game.getBoard().getVertex(positions.get(0)).getCoins().isEmpty()) {
                        from = positions.get(1);
                        to = positions.get(0);
                    }
                    result = game.move(from, to);
                }
                break;
            case PAY:
                CoinSize coinSize = selectedItemsController.getCoinSize();
                if (coinSize != null) {
                    result = game.pay(coinSize);
                }
                break;
            case PLACE:
                coinSize = selectedItemsController.getCoinSize();
                positions = selectedItemsController.getPositions();
                if (coinSize != null && !positions.isEmpty()) {
                    result = game.place(coinSize, positions.get(0));
                }
                break;
        }
        if (result != null) {
            if (game.state().getAvailableMoves().isEmpty() && autoPass) {
                game.pass();
            }
            if (game.state().getAvailableMoves().contains(Move.SLAP) && autoSlap) {
                game.slap();
            }
            if (result.getStatus() == Result.Status.END_OF_GAME) {
                gui.endOfGame();
            }
        }
        selectedItemsController.clear();
        gui.setAvailableMoves(game.state().getAvailableMoves());
        gui.setSelectedCoinSize(null);
        gui.setSelectedPositions(Collections.<String>emptyList());
    }
}
