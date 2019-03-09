package Tema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Test {
	static PrintWriter fout;
	
	private BookDepartment createBookDepartment(Scanner sc, String name, String ID) {
		BookDepartment bookDep = new BookDepartment(name, Integer.parseInt(ID));
		String tmp = sc.nextLine();
		int size = Integer.parseInt(tmp);
		for(int i = 0; i < size; i++) {
			tmp = sc.nextLine();
			String parts[] = tmp.split(";");
			Item toAdd = new Item(parts[0], Integer.parseInt(parts[1]), bookDep.getId(), Float.parseFloat(parts[2]));
			bookDep.addItem(toAdd);
		}
		return bookDep;
	}
	
	private MusicDepartment createMusicDepartment(Scanner sc, String name, String ID) {
		MusicDepartment musicDep = new MusicDepartment(name, Integer.parseInt(ID));
		String tmp = sc.nextLine();
		int size = Integer.parseInt(tmp);
		for(int i = 0; i < size; i++) {
			tmp = sc.nextLine();
			String parts[] = tmp.split(";");
			Item toAdd = new Item(parts[0], Integer.parseInt(parts[1]), musicDep.getId(), Float.parseFloat(parts[2]));
			musicDep.addItem(toAdd);
		}
		return musicDep;
	}
	
	private SoftwareDepartment createSoftwareDepartment(Scanner sc, String name, String ID) {
		SoftwareDepartment softwareDep = new SoftwareDepartment(name, Integer.parseInt(ID));
		String tmp = sc.nextLine();
		int size = Integer.parseInt(tmp);
		for(int i = 0; i < size; i++) {
			tmp = sc.nextLine();
			String parts[] = tmp.split(";");
			Item toAdd = new Item(parts[0], Integer.parseInt(parts[1]), softwareDep.getId(), Float.parseFloat(parts[2]));
			softwareDep.addItem(toAdd);
		}
		return softwareDep;
	}
	
	private VideoDepartment createVideoDepartment(Scanner sc, String name, String ID) {
		VideoDepartment videoDep = new VideoDepartment(name, Integer.parseInt(ID));
		String tmp = sc.nextLine();
		int size = Integer.parseInt(tmp);
		for(int i = 0; i < size; i++) {
			tmp = sc.nextLine();
			String parts[] = tmp.split(";");
			Item toAdd = new Item(parts[0], Integer.parseInt(parts[1]), videoDep.getId(), Float.parseFloat(parts[2]));
			videoDep.addItem(toAdd);
		}
		return videoDep;
	}
	
	
	public void createStore(String path, Store store) {
		File input = new File(path);
		Scanner sc = null;
		String tmp = null;
		try {
			sc = new Scanner(input);
			tmp = sc.nextLine();
			store.setName(tmp);
			
			while(sc.hasNextLine()) {
				tmp = sc.nextLine();
				String parts_dep[] = tmp.split(";");
				if(parts_dep[0].equals("BookDepartment") == true) 
					store.addDepartment(createBookDepartment(sc, parts_dep[0], parts_dep[1]));
				else if(parts_dep[0].equals("MusicDepartment") == true)
					store.addDepartment(createMusicDepartment(sc, parts_dep[0], parts_dep[1]));
				else if(parts_dep[0].equals("SoftwareDepartment") == true) 
					store.addDepartment(createSoftwareDepartment(sc, parts_dep[0], parts_dep[1]));
				else if(parts_dep[0].equals("VideoDepartment") == true) 
					store.addDepartment(createVideoDepartment(sc, parts_dep[0], parts_dep[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
	
	
	
	public void createCustomers(String path, Store store) {
		File input = new File(path);
		Scanner sc = null;
		String tmp = null;
		try {
			sc = new Scanner(input);
			tmp = sc.nextLine();
			int size = Integer.parseInt(tmp);
			for(int i = 0; i < size; i++) {
				tmp = sc.nextLine();
				String parts_customer[] = tmp.split(";");
				store.enter(new Customer(parts_customer[0], Float.parseFloat(parts_customer[1]), parts_customer[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
	
	
	
	private Item event_add_del_Item_getItem(int itemID) {
		Store store = Store.getInstance();
		Vector<Department> departments = store.getDepartments();
		for(Department dep:departments) {
			for(Item item:dep.getItems()) {
				if(item.getID() == itemID) {
					return item;
				}
			}
		}
		return null;
	}
	private void event_add_del_Item(String tmp, String flag) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		int pozDep = 0;
		Item toAdd = event_add_del_Item_getItem(Integer.parseInt(parts_event[1])); 
		for(int i = 0; i < store.getDepartments().size(); i++)
			if(store.getDepartments().get(i).getId() == toAdd.getID_department()) {
				pozDep = i;
				break;
			}
		
		Vector<Customer> customers = store.getCustomers();
		for(int poz = 0; poz < customers.size(); poz++) {
			if(customers.get(poz).name.equals(parts_event[3]) == true) {
				if(flag.equals("DELETE")) {
					if(parts_event[2].equals("ShoppingCart") == true) {
						customers.get(poz).cart.remove(toAdd);
					}
					else {
						customers.get(poz).wishList.remove(toAdd);
						int dep_index = getDep_position(toAdd.getID_department());
						if(checkIfStillObserverer(customers.get(poz), dep_index) == false)
							store.getDepartments().get(dep_index).removeObserver(store.getCustomers().get(poz));
					}
						
				}
				if(flag.equals("ADD")) {
					if(parts_event[2].equals("ShoppingCart") == true) {
						customers.get(poz).cart.add(new Item(toAdd));
					}
					else {
						customers.get(poz).wishList.add(new Item(toAdd));
						if(store.getDepartments().get(pozDep).getObservers().contains(customers.get(poz)) == false)
							store.getDepartments().get(pozDep).addObserver(customers.get(poz));
					}
				}
				break;
			}
		}
	}
	
	private void event_add_modify_Product(String tmp, String flag) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		Vector<Department> departments = store.getDepartments();
		
		for(int poz = 0; poz < departments.size(); poz++) {
			if(departments.get(poz).getId() == Integer.parseInt(parts_event[1])) {
				
				if(flag.equals("ADD")) {
					Item toAdd = new Item(parts_event[4],
							Integer.parseInt(parts_event[2]),
							Integer.parseInt(parts_event[1]),
							Float.parseFloat(parts_event[3]));
					
					departments.get(poz).addItem(toAdd);
					departments.get(poz).notifyAllObservers(new Notification(Notification.NotificationType.ADD, toAdd.getID_department(), toAdd.getID()));
					break;
				}
				
				Vector<Item> items = departments.get(poz).getItems();
				for(int index = 0; index < items.size(); index++) {
					if(items.get(index).getID() == Integer.parseInt(parts_event[2])) {
						if(flag.equals("MODIFY")) {
							departments.get(poz).getItems().get(index).setPrice(Float.parseFloat(parts_event[3]));
							Item tmpItem = departments.get(poz).getItems().get(index);
							departments.get(poz).notifyAllObservers(new Notification(Notification.NotificationType.MODIFY, tmpItem.getID_department(), tmpItem.getID()));
							break;
						}
					}	
				}
				break;
			}
		}
	}
	
	private void event_delete_Product(String tmp) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		Item toDelete = event_add_del_Item_getItem(Integer.parseInt(parts_event[1])); 
		Vector<Department> departments = store.getDepartments();
		for(int poz = 0; poz < departments.size(); poz++) {
			if(departments.get(poz).getId() == toDelete.getID_department()) {
				Vector<Item> items = departments.get(poz).getItems();
				for(int index = 0; index < items.size(); index++) {
					if(items.get(index).getID() == Integer.parseInt(parts_event[1])) {
						departments.get(poz).getItems().remove(index);
						departments.get(poz).notifyAllObservers(new Notification(Notification.NotificationType.REMOVE, toDelete.getID_department(), toDelete.getID()));
						return;
					}	
				}
			}
		}
	}
	
	private void event_getItem(String tmp) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		Customer c = null;
		int poz_c = 0;
		for(int i = 0; i < store.getCustomers().size(); i++) {
			if(parts_event[1].equals(store.getCustomers().get(i).name) == true) {
				c = store.getCustomers().get(i);
				poz_c = i;
				break;
			}
		}
		Item toMove = c.wishList.strat.execute(c.wishList);
		fout.println(toMove);
		store.getCustomers().get(poz_c).wishList.remove(toMove);
		int dep_index = getDep_position(toMove.getID_department());
		if(checkIfStillObserverer(store.getCustomers().get(poz_c), dep_index) == false) {
			store.getDepartments().get(dep_index).removeObserver(store.getCustomers().get(poz_c));
		}
		store.getCustomers().get(poz_c).cart.add(toMove);
	}
	
	private void event_getItems(String tmp) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		Vector<Customer> customers = store.getCustomers();
		for(Customer c:customers) {
			if(c.name.equals(parts_event[2])) {
				if(parts_event[1].equals("WishList"))
					fout.println(c.wishList);
				else
					fout.println(c.cart);
			}
		}
	}
	
	private void event_getTotal(String tmp) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		Vector<Customer> customers = store.getCustomers();
		for(Customer c:customers) {
			if(c.name.equals(parts_event[2]) == true) {
				if(parts_event[1].equals("WishList") == true) {
					fout.printf("%.2f\n", c.wishList.getTotalPrice());
				}
				else {
					fout.printf("%.2f\n", c.cart.getTotalPrice());
				}
			}
		}
	}
		
	private int getDep_position(int depID) {
		Store store = Store.getInstance();
		for(int i = 0; i < store.getDepartments().size(); i++) {
			if(store.getDepartments().get(i).getId() == depID)
				return i;
		}
		return -1;
	}
	
	private boolean checkIfStillObserverer(Customer c, int dep_index) {
		Iterator<Item> it = c.wishList.listIterator();
		Department dep = Store.getInstance().getDepartments().get(dep_index);
		int count = 0;
		while(it.hasNext()) {
			Item wishListItem = it.next();
			if(wishListItem.getID_department() == dep.getId())
				count++;
		}
		if(count != 0)
			return true;
		return false;
	}
	
	
	private void event_accept(String tmp) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		int depID = Integer.parseInt(parts_event[1]);
		int poz = getDep_position(depID);
		for(int i = 0; i < store.getCustomers().size(); i++) {
			if(store.getCustomers().get(i).name.equals(parts_event[2]) == true) {
				store.getDepartments().get(poz).accept(store.getCustomers().get(i).cart);
				break;
			}
		}
	}
	
	private void event_getObservers(String tmp) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		int depID = Integer.parseInt(parts_event[1]);
		int poz = getDep_position(depID);
		fout.println(store.getDepartments().get(poz).getObservers());

	}
	
	private void event_getNotifications(String tmp) {
		String[] parts_event = tmp.split(";");
		Store store = Store.getInstance();
		Vector<Customer> customers = store.getCustomers();
		for(Customer c:customers) {
			if(c.name.equals(parts_event[1])) {
				fout.println(c.getNotifications());		
			}
		}
	}
	
	
	
	
	public void findEvent(String tmp) {
		String[] parts_event = tmp.split(";");
		switch(parts_event[0]) {
			case "addItem":
				event_add_del_Item(tmp, "ADD");
				break;
			case "delItem":
				event_add_del_Item(tmp, "DELETE");
				break;
			case "addProduct":
				event_add_modify_Product(tmp, "ADD");
				break;
			case "modifyProduct":
				event_add_modify_Product(tmp, "MODIFY");
				break;
			case "delProduct":
				event_delete_Product(tmp);
				break;
			case "getItem":
				event_getItem(tmp);
				break;
			case "getItems":
				event_getItems(tmp);
				break;
			case "getTotal":
				event_getTotal(tmp);
				break;
			case "accept":
				event_accept(tmp);
				break;
			case "getObservers":
				event_getObservers(tmp);
				break;
			case "getNotifications":
				event_getNotifications(tmp);
				break;
			default:
				break;
		}
	}
	

	private void loadEvents(String path, Store store) {
		File input = new File(path + "events.txt");
		Scanner sc = null;
		String tmp = null;
		try {
			sc = new Scanner(input);
			tmp = sc.nextLine();
			int size = Integer.parseInt(tmp);
			for(int i = 0; i < size; i++) {
				tmp = sc.nextLine();
				findEvent(tmp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
	
	/*public static void main(String args[]) {
		Test obj = new Test();
		Store store = Store.getInstance();
		String path = "/home/andrei/Teste_TemaPOO/test09/";
		
		
		
		obj.createStore(path, store);
		obj.createCustomers(path, store);
		
		try {
			fout = new PrintWriter(new File(path + "output.txt"));
			obj.loadEvents(path, store);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println("Done test!");
	
	}*/

	
}
