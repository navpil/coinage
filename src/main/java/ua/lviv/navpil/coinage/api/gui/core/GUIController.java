package ua.lviv.navpil.coinage.api.gui.core;

import ua.lviv.navpil.coinage.controller.Game;
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
    private final Game game;

    public GUIController(final GUI gui, final Game game, boolean autoPass, boolean autoSlap) {
        this.gui = gui;
        this.game = game;
        this.autoPass = autoPass;
        this.autoSlap = autoSlap;
        if (autoSlap) {
            game.slap();
        }
        gui.setSelectionListener(this);
        gui.setMoveAttemptListener(this);
        gui.updateState(game.getState());
    }

    public GUIController(final GUI gui, final Game game) {
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
        if (coin.getSide() == game.getState().getActivePlayer()) {
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
                    if (isEmptyVertex(from)) {
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
            if (game.getState().getAvailableMoves().isEmpty() && autoPass) {
                game.pass();
            }
            if (game.getState().getAvailableMoves().contains(Move.SLAP) && autoSlap) {
                game.slap();
            }
            if (result.getStatus() == Result.Status.END_OF_GAME) {
                gui.endOfGame();
            }
        }
        selectedItemsController.clear();
        gui.setSelectedCoinSize(null);
        gui.setSelectedPositions(Collections.<String>emptyList());
        gui.updateState(game.getState());
    }

    private boolean isEmptyVertex(String from) {
        return game.getState().getVertexes().get(from).getCoins().isEmpty();
    }
}
