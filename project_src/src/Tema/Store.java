package Tema;
import java.util.Vector;

public class Store {
	private static Store instance = null;
	private Vector<Customer> customers;
	private Vector<Department> departments;
	private String name;
	
	private Store() {
		customers = new Vector<Customer>();
		departments = new Vector<Department>();
	}
	
	public static Store getInstance() {
		if(instance == null){
			instance = new Store();
		}
		return instance;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void enter(Customer c) {
                customers.add(c);
	}
	public void exit(Customer c) {
		customers.remove(c);
	}
	public Vector<Customer> getCustomers() {
		return customers;
	}
	public Vector<Department> getDepartments() {
		return departments;
	}
	public void addDepartment(Department d) {
		departments.addElement(d);
	}
        public Department getDepartment(int ID) {
            for(Department dep:getDepartments())
                if(dep.getId() == ID)
                    return dep;
            return null;
        }
}

