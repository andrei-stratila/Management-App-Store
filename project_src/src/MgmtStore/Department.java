package Tema;

import java.util.Vector;

interface Subject {
	void addObserver(Customer c);
	void removeObserver(Customer c);
	void notifyAllObservers(Notification notify);
}

abstract public class Department implements Subject {
	private String name;
	private Vector<Item> itemsToSell;
	private Vector<Customer> buyers;
	private Vector<Customer> inWishList;
	private int ID;
	
	public Department(String name, int ID) {
		this.name = name;
		this.ID = ID;
		itemsToSell = new Vector<Item>();
		buyers = new Vector<Customer>();
		inWishList = new Vector<Customer>();
	}
	
	public void enter(Customer c) {
		buyers.addElement(c);
	}
	
	public void exit(Customer c) {
		buyers.remove(c);
	}
	
	public Vector<Customer> getCustomers(){
		return buyers;
	}
	public String getName() {
		return name;
	}
	
	public int getId() {
		return ID;
	}
	
	public Vector<Customer> getObservers() {
		return inWishList;
	}
	
	public void addItem(Item item) {
		itemsToSell.addElement(item);
	}
	
	public Vector<Item> getItems() {
		return itemsToSell;
	}

	public abstract void accept(ShoppingCart shopCart);
	
	public void addObserver(Customer c) {
		inWishList.addElement(c);
	}
	public void removeObserver(Customer c) {
		inWishList.remove(c);
	}
	public void notifyAllObservers(Notification notify) {
		Vector<Customer> toRemove = new Vector<Customer>();
		for(Customer customer:inWishList) 
			if(customer.update(notify) == true) {
				toRemove.addElement(customer);
			}
		for(Customer customer:toRemove) {
			removeObserver(customer);
		}
			

	}
}

class BookDepartment extends Department {

	public BookDepartment(String name, int ID) {
		super(name, ID);
	}
	
	@Override
	public void accept(ShoppingCart shopCart) {
		shopCart.visit(this);
	}
	
}

class MusicDepartment extends Department {
	
	public MusicDepartment(String name, int ID) {
		super(name, ID);
	}

	@Override
	public void accept(ShoppingCart shopCart) {
		shopCart.visit(this);
	}
}

class SoftwareDepartment extends Department {
	public SoftwareDepartment(String name, int ID) {
		super(name, ID);
	}

	@Override
	public void accept(ShoppingCart shopCart) {
		shopCart.visit(this);
	}
}

class VideoDepartment extends Department {
	public VideoDepartment(String name, int ID) {
		super(name, ID);
	}

	@Override
	public void accept(ShoppingCart shopCart) {
		shopCart.visit(this);
	}
}