package Tema;

import java.text.DecimalFormat;

public class Item {
	private String name;
	private int ID;
	private int ID_department;
	private float price;
	
	public Item() {
		this("", 0, 0, 0);
	}
	
	public Item(Item toCopy) {
		this(toCopy.name, toCopy.ID, toCopy.ID_department, toCopy.price);
	}
	
	public Item(String name, int ID, int ID_department, float price) {
		this.name = name;
		this.ID = ID;
		this.ID_department = ID_department;
		this.price = price;	
	}
	
	public String getName() {
		return name;
	}
	public int getID() {
		return ID;
	}
	
	public int getID_department() {
		return ID_department;
	}
	
	public float getPrice() {
		return price;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public void getID_department(int ID_department) {
		this.ID_department = ID_department;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public String toString() {
		//String priceString = String.format("%.2f", price);
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String priceString = decimalFormat.format(price);
		return name + ";" + ID + ";" + priceString;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Item))
			return false;
		Item item = (Item) o;
		if(name.equals(item.name) == false)
			return false;
		if(ID != item.getID())
			return false;
		if(ID_department != item.getID_department())
			return false;
		if(Float.compare(price, item.getPrice()) != 0)
			return false;
		return true;
	}
}
	


