package Tema;

import java.util.Iterator;

public interface Strategy {
	public Item execute(WishList wishList);
}

class StrategyA implements Strategy { 
	public Item execute(WishList wishList) {
		Iterator<Item> it = wishList.listIterator();
		Item cheapest = new Item(null,0,0, Float.MAX_VALUE);
		Item tmp = null;
		while(it.hasNext()) {
			tmp = it.next();
			if(tmp.getPrice() < cheapest.getPrice()) {
				cheapest = tmp;
			}
		}
		return cheapest;
	}
}

class StrategyB implements Strategy  { 
	public Item execute(WishList wishList) {
		Iterator<Item> it = wishList.listIterator();
		Item firstAlph = new Item("{", 0, 0, 0);
		Item tmp = null;
		while(it.hasNext()) {
			tmp = it.next();
			if(firstAlph.getName().compareTo(tmp.getName()) > 0)
				firstAlph = tmp;
		}
		return firstAlph;
	}
}

class StrategyC implements Strategy  { 
	public Item execute(WishList wishList) {
		Iterator<Item> it = wishList.listIterator();
		Item latest = null;
		while(it.hasNext()) {
			latest = it.next();
		}
		return latest;
	}
}
