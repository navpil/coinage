package ua.lviv.navpil.coinage.api.gui.core;

import ua.lviv.navpil.coinage.model.Coin;
import ua.lviv.navpil.coinage.model.CoinSize;

import java.util.*;
import java.util.stream.Collectors;

class SelectedItemsController {

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
        return selectedItems.stream()
                .filter((item) -> item.getCoin() != null)
                .findFirst()
                .map((item) -> item.getCoin().getSize())
                .orElse(null);
    }

    public List<String> getPositions() {
        return selectedItems.stream()
                .filter(selectedItem -> selectedItem.getPosition() != null)
                .map(SelectedItem::getPosition)
                .collect(Collectors.toList());
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

