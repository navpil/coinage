package ua.lviv.navpil.coinage.api.swing;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;

import java.util.*;

public class SelectedItemsController {

    private Queue<SelectedItem> selectedItems = new LinkedList<SelectedItem>();

    public void addPosition(String position) {
        SelectedItem selectedItem = new SelectedItem(position, null);

        if(selectedItems.contains(selectedItem)) {
            //deselect
            selectedItems.remove(selectedItem);
        } else {
            //add selection
            selectedItems.add(selectedItem);
            if (selectedItems.size() > 2) {
                selectedItems.poll();
            }
        }
    }

    public void addCoin(Coin coin) {
        SelectedItem selectedItem = new SelectedItem(null, coin);

        //deselect
        if(selectedItems.contains(selectedItem)) {
            selectedItems.remove(selectedItem);
            return;
        }

        //remove any coins that are in the selection
        SelectedItem c = getSelectedCoinItem();
        if(c != null) {
            selectedItems.remove(c);
        }

        //add new coin
        selectedItems.add(selectedItem);
        if (selectedItems.size() > 2) {
            selectedItems.poll();
        }
    }

    private SelectedItem getSelectedCoinItem() {
        for (SelectedItem selectedItem : selectedItems) {
            if (selectedItem.getCoin() != null) {
                return selectedItem;
            }
        }
        return null;
    }

    public CoinSize getCoinSize() {
        for (SelectedItem selectedItem : selectedItems) {
            if (selectedItem.getCoin() != null) {
                return selectedItem.getCoin().getSize();
            }
        }
        return null;
    }

    public List<String> getPositions() {
        java.util.List<String> result = new ArrayList<String>();
        for (SelectedItem selectedItem : selectedItems) {
            if (selectedItem.getPosition() != null) {
                result.add(selectedItem.getPosition());
            }
        }
        return result;
    }

    public void clear() {
        selectedItems.clear();
    }

    private static class SelectedItem {

        private final String position;
        private final Coin coin;

        private SelectedItem(String position, Coin coin) {
            this.position = position;
            this.coin = coin;
        }

        public String getPosition() {
            return position;
        }

        public Coin getCoin() {
            return coin;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SelectedItem that = (SelectedItem) o;

            if (coin != null ? !coin.equals(that.coin) : that.coin != null) return false;
            if (position != null ? !position.equals(that.position) : that.position != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = position != null ? position.hashCode() : 0;
            result = 31 * result + (coin != null ? coin.hashCode() : 0);
            return result;
        }
    }

}

