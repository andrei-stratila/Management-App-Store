package Tema;

import java.util.*;


abstract public class ItemList<T> {
	Node<Item> first;
	Node<Item> last;
	Comparator<Item> comp;
	
	public ItemList(){
		first = null;
		last = null;
		comp = null;
	}
	public ItemList(Comparator<Item> comp) {
		this();
		this.comp = comp;
	}
	
	static class Node<T> {
		Item data;
		Node<Item> next;
		Node<Item> prev;
		public Node(Item data) {
			this(data, null, null);
		}
		
		public Node(Item data, Node<Item> next, Node<Item> prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	
	class ItemIterator implements ListIterator<Item>{
		Node<Item> current;
		int index;
		public ItemIterator() {
			current = new Node<Item>(null, first, null);
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			if(current.next != null)
				return true;
			return false;
		}

		@Override
		public Item next() {
			index++;
			current = current.next;
			return current.data;
		}

		@Override
		public boolean hasPrevious() {
			if(current.prev != null)
				return true;
			return false;
		}

		@Override
		public Item previous() {
			index--;
			current = current.prev;
			return current.data;
		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public int previousIndex() {
			return index;
		}

		@Override
		public void set(Item e) {
			current.data = e;
		}

		@Override
		public void add(Item e) {
			return;
		}

		@Override
		public void remove() {
			return;
		}		
	}
	
	public boolean add(Item element) {
		Node<Item> toAdd = new Node<Item>(element);
		
		if(comp == null) {
			if(first == null) {
				first = toAdd;
				last = toAdd;
				return true;
			}
			last.next = toAdd;
			toAdd.prev = last;
			last = last.next;
			return true;
		} 
		
		
		if(first == null) {
			first = toAdd;
			last = toAdd;
			return true;
		}
		if(comp.compare(first.data, element) > 0) {
			toAdd.next = first;
			first.prev = toAdd;
			first = toAdd;
			return true;
		}
		Node<Item> tmp = first;
		while(tmp != null) {
			if(comp.compare(element, tmp.data) < 0) {
				toAdd.next = tmp;
				tmp.prev.next = toAdd;
				toAdd.prev = tmp.prev;
				tmp.prev = toAdd;
				return true;
			}
			tmp = tmp.next;
		}
		last.next = toAdd;
		toAdd.prev = last;
		last = toAdd;
		return true;
	}
	
	public boolean addAll(Collection<? extends Item> c) {
		Iterator<? extends Item> it = c.iterator();
		while(it.hasNext()) {
			add((Item)it.next());
		}
		return true;
	}	
	
	public Item getItem(int index) {
		Node<Item> tmp = first;
		int poz = 0;
		while(tmp.next != null && index != poz) {
			tmp = tmp.next;
			poz++;
		}
		return tmp.data;
	}
	
	public Node<Item> getNode(int index) {
		Node<Item> tmp = first;
		int poz = 0;
		while(tmp.next != null && index != poz) {
			tmp = tmp.next;
			poz++;
		}
		return tmp;
	}
	
	
	public int indexOf(Item item) {
		Node<Item> tmp = first;
		int poz = 0;
		while(tmp != null) {
			if(item.equals(tmp.data) == true)
				return poz;
			poz++;
			tmp = tmp.next;
		}
		return -1;
	}
	
	public int indexOf(Node<Item> node) {
		Node<Item> tmp = first;
		int poz = 0;
		while(tmp != null) {
			if(comp.compare(node.data, tmp.data) == 0)
				return poz;
			poz++;
			tmp = tmp.next;
		}
		return -1;
	}
	
	public boolean contains(Node<Item> node) {
		Node<Item> tmp = first;
		while(tmp != null) {
			if(node.data.equals(tmp.data) == true)
				return true;
			tmp = tmp.next;
		}
		return false;
	}
	
	public Item remove(int index) {
		Item removed = null;
		Node<Item> tmp = first;
		if(first == null)
			return null;
		if(index == 0) {
			removed = tmp.data;
			first = first.next;
			if(first != null)
				first.prev = null;
			return removed;
		}
		
		while(index != 0 && tmp != null) {
			tmp = tmp.next;
			index--;
		}
		
		if(tmp.data.equals(last.data) == true) {
			removed = tmp.data;
			last = last.prev;
			last.next = null;
			return removed;
		}
		removed = tmp.data;
		tmp.prev.next = tmp.next;
		tmp.next.prev = tmp.prev;
		return removed;
	}
	
	public boolean remove(Item item) {
		Node<Item> tmp = first;
		int poz = 0;
		while(tmp != null) {
			if(item.equals(tmp.data) == true) {
				remove(poz);
				return true;
			}
			poz++;
			tmp = tmp.next;
		}
		return false;
	}
	
	public boolean removeAll(Collection<? extends Item> c) {
		Iterator<? extends Item> it = c.iterator();
		while(it.hasNext()) {
			remove((Item)it.next());
		}
		return true;
	}
	
	public boolean isEmpty() {
		if(first == null)
			return true;
		return false;
	}
	
	public ListIterator<Item> listIterator(){
		return new ItemIterator();
	}
	public ListIterator<Item> listIterator(int index){
		ListIterator<Item> tmp = new ItemIterator();
		while(tmp.nextIndex() != index)
			tmp.next();
		return tmp;
	}
	
	public Double getTotalPrice() {
		double sum = 0;
		Node<Item> tmp = first;
		while(tmp != null) {
			sum += tmp.data.getPrice();
			tmp = tmp.next;
		}
		return sum;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		Iterator<Item> it = listIterator();
		while(it.hasNext()) {
			buffer.append(it.next());
			if(it.hasNext() == false)
				break;
			buffer.append(", ");
		}
		buffer.append("]");
		return buffer.toString();
	}
}

