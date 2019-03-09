package Tema;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

interface Visitor {
	void visit(BookDepartment bookDepartment);
	void visit(MusicDepartment musicDepartment);
	void visit(SoftwareDepartment softwareDepartment);
	void visit(VideoDepartment videoDepartment);
}

class ItemComparator implements Comparator<Item> {
	@Override
	public int compare(Item o1, Item o2) {
		int cr = Double.compare(o1.getPrice(), o2.getPrice());
		if(cr != 0)
			return cr;
		cr = o1.getName().compareTo(o2.getName());
		if(cr != 0)
			return cr;
		return o1.getID() - o2.getID();
	}
}


public class ShoppingCart extends ItemList<Item> implements Visitor{
	float budget;
	
	public ShoppingCart(float budget) {
		super(new ItemComparator());
		this.budget = budget;
	}
	
        public float getBudget(){
            return budget;
        }
        public void setBudget(float budget){
            this.budget = budget;
        }
        
	public boolean add(Item element) {
		if((budget - element.getPrice()) >= 0) {
			budget = budget - element.getPrice();
			super.add(element);
			return true;
		}
		return false;
	}

	public boolean remove(Item element) {
		budget = budget + element.getPrice();
		Iterator<Item> it = listIterator();
		while(it.hasNext()) {
			Item tmp = it.next();
			if(tmp.getID() == element.getID()) {
				super.remove(tmp);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void visit(BookDepartment bookDepartment) {
		int ID_book = bookDepartment.getId();
		float oldPrice = 0;
		float newPrice = 0;
		Iterator<Item> it = this.listIterator();
		while(it.hasNext()) {
			Item tmp = it.next();
			
			if(ID_book == tmp.getID_department()) {
				oldPrice = tmp.getPrice();
				newPrice = oldPrice - oldPrice / 10;
				tmp.setPrice(newPrice);
			}
		}
		
	}

	@Override
	public void visit(MusicDepartment musicDepartment) {
		int ID_music = musicDepartment.getId();
		float total = 0;
		
		Iterator<Item> it = listIterator();
		while(it.hasNext()) {
			Item tmp = it.next();
			if(ID_music == tmp.getID_department()) {
				total += tmp.getPrice();
			}
		}
		budget = budget + total / 10; 
	}

	@Override
	public void visit(SoftwareDepartment softwareDepartment) {
		Vector<Item> items = softwareDepartment.getItems();
		Item cheapest = Collections.min(items, new Comparator<Item>() {	
			@Override
			public int compare(Item o1, Item o2) {
				return Double.compare(o1.getPrice(), o2.getPrice());
			}
		});
		
		if(cheapest.getPrice() < budget)
			return;
		int ID_soft = softwareDepartment.getId();
		float oldPrice = 0;
		float newPrice = 0;
		Iterator<Item> it = listIterator();
		while(it.hasNext()) {
			Item tmp = it.next();
			if(ID_soft == tmp.getID_department()) {
				oldPrice = tmp.getPrice();
				newPrice = oldPrice - oldPrice / 5;
				tmp.setPrice(newPrice);
			}
		}
	}

	@Override
	public void visit(VideoDepartment videoDepartment) {
		Vector<Item> items = videoDepartment.getItems();
		int ID_video = videoDepartment.getId();
		float total = 0;
		Item maxItem = Collections.max(items, new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				return Float.compare(o1.getPrice(), o2.getPrice());
			}
		});
		budget = budget + 5 / 100 * total;
		
		Iterator<Item> it = listIterator();
		while(it.hasNext()) {
			Item tmp = it.next();
			if(tmp.getID_department() == ID_video)
				total += tmp.getPrice();
		}
		
		if(total < maxItem.getPrice())
			return;
		
		
		float oldPrice = 0;
		float newPrice = 0;
		it = listIterator();
		while(it.hasNext()) {
			Item tmp = it.next();
			if(ID_video == tmp.getID_department()) {
				oldPrice = tmp.getPrice();
				newPrice = oldPrice - (oldPrice * 15 / 100);
				tmp.setPrice(newPrice);
			}
		}
	}
	
}
