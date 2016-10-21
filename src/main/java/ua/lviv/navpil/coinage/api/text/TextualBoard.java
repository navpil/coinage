package ua.lviv.navpil.coinage.api.text;

import ua.lviv.navpil.coinage.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TextualBoard {
    private final Map<String, Vertex> board;

    public TextualBoard(Map<String, Vertex> board) {
        this.board = board;
    }

    private String[] vertex(Vertex vertex) {
        List<Coin> coins = vertex.getCoins();
        List<String> coinValues = new ArrayList<String>();
        for (Coin coin : coins) {
            coinValues.add(coin.toString());
        }
        while(coinValues.size() < 4) {
            coinValues.add(" ");
        }
        String belongsTo = " ";
        if(!coins.isEmpty()) {
            Coin lastCoin = coins.get(coins.size() - 1);
            if(lastCoin.getSide() == Side.HEADS) {
                belongsTo = "H";
            } else {
                belongsTo = "t";
            }
        }

        return new String[] {
                "--------",
                "|" + vertex.getName() + "    " + belongsTo,
                "|" + vertex.getRegion() + "   " + coinValues.get(3) + "  ",
                "|    "+coinValues.get(2)+"  ",
                "|    "+coinValues.get(1)+"  ",
                "|    "+coinValues.get(0)+"  "
        };
    }



    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        fill(sb, "   ", "A1", "A2", "A3");
        fill(sb, "", "B1", "B2", "B3", "B4");
        fill(sb, "   ", "C1", "C2", "C3");
        sb.append("-----------------------------------");
        return sb.toString();
    }

    private void fill(StringBuilder sb, String str, String... vertexNames) {
        List<String[]> renders = new ArrayList<String[]>();
        for(String s : vertexNames) {
            renders.add(vertex(board.get(s)));
        }

        for(int i = 0; i < renders.get(0).length; i++) {
            sb.append(str);
            for (String[] render : renders) {
                sb.append(render[i]);
            }
            sb.append("|\n");

        }
    }
}
