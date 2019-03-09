package Tema;

import java.util.Iterator;
import java.util.Vector;

interface Observer {
	public boolean update(Notification notify);
}

public class Customer implements Observer{
	public String name;
	public ShoppingCart cart;
	public WishList wishList;
	public Vector<Notification> notifications;

	public Customer(String name, float budget, String strategy) {
		this.name = name;
		cart = new ShoppingCart(budget);
		wishList = new WishList(strategy);
		notifications = new Vector<Notification>();
	}
	
	public String toString() {
		return name;
	}

	public Vector<Notification> getNotifications() {
		return notifications;
	}
	
	
	
	private void updateShoppingCart(Notification notify, Item currentItem, String flag) {
		Iterator<Item> it = cart.listIterator();
		while(it.hasNext()) {
			Item cartItem = it.next();
			if(cartItem.getID() == notify.getID_item()) {
				if(flag.equals("MODIFY") == true) {
					cart.budget = cart.budget + cartItem.getPrice() - currentItem.getPrice();
					cartItem.setPrice(currentItem.getPrice());
					break;
				}
				if(flag.equals("REMOVE") == true) {
					cart.budget = cart.budget + cartItem.getPrice();
					cart.remove(cartItem);
					break;
				}
			}
		}
	}
	
	private void updateWishList(Notification notify, Item currentItem, String flag) {
		Iterator<Item> it = wishList.listIterator();
		while(it.hasNext()) {
			Item wishListItem = it.next();
			if(wishListItem.getID() == notify.getID_item()) {
				if(flag.equals("MODIFY") == true) {
					wishListItem.setPrice(currentItem.getPrice());
					break;
				}
				if(flag.equals("REMOVE") == true) {
					wishList.remove(wishListItem);
					break;
				}
			}
		}
	}
	
	
	@Override
	public boolean update(Notification notify) {
		notifications.addElement(notify);
		
		String flag = notify.getType().toString();
		if(flag.equals("ADD"))
			return false;
		
		Vector<Department> deps = Store.getInstance().getDepartments();
		
		int currentDep_pos = 0;
		Item currentItem = null;
		
		for(int i = 0; i < deps.size(); i++) {
			if(deps.get(i).getId() == notify.getID_department()) {
				for(Item item:deps.get(i).getItems()) {
					if(item.getID() == notify.getID_item()) {
						currentItem = item;
						break;
					}
				}
				currentDep_pos = i;
				break;
			}
		}
		
		
		updateShoppingCart(notify, currentItem, flag);
		updateWishList(notify, currentItem, flag);
		
		int count = 0;
		Iterator<Item> it = wishList.listIterator();
		while(it.hasNext()) {
			Item wishListItem = it.next();
			if(wishListItem.getID_department() == deps.get(currentDep_pos).getId()) {
				count++;
			}
		}
		if(count == 0)
			return true;
		return false;
	}
}
