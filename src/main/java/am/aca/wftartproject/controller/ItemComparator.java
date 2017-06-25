package am.aca.wftartproject.controller;

import am.aca.wftartproject.model.Item;

import java.util.Comparator;
import java.util.List;

/**
 * Created by ASUS on 23-Jun-17
 */
public enum ItemComparator implements Comparator<Item> {

    PRICE_SORT("1") {
        public int compare(Item item1, Item item2) {
            return item1.getPrice().compareTo(item2.getPrice());
        }
    },

    TITLE_SORT("2") {
        public int compare(Item item1, Item item2) {
            return item1.getTitle().compareTo(item2.getTitle());
        }
    },

    AVAILABILITY_SORT("3") {
        public int compare(Item item1, Item item2) {
            return item1.getStatus().compareTo(item2.getStatus());
        }
    };


    private String idStr;

    ItemComparator(String idStr) {
        this.idStr = idStr;
    }


    public static Comparator<Item> descending(final Comparator<Item> itemComparator) {
        return (o1, o2) -> -1 * itemComparator.compare(o1, o2);
    }


    public static Comparator<Item> getComparator(final ItemComparator... itemComparators) {
        return (o1, o2) -> {
            for (ItemComparator itemCompElement : itemComparators) {
                int result = itemCompElement.compare(o1, o2);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        };
    }


    public static List<Item> getSortedItemList(String idStr, List<Item> itemList) {
        switch (idStr) {
            case "1":
                itemList.sort(getComparator(PRICE_SORT));
                break;
            case "2":
                itemList.sort(getComparator(TITLE_SORT));
                break;
            case "3":
                itemList.sort(getComparator(AVAILABILITY_SORT));
                break;
            case "4":
                itemList.sort(descending(AVAILABILITY_SORT));
                break;
            default:
                throw new RuntimeException("Wrong sorting argument");
        }
        return itemList;
    }
}
