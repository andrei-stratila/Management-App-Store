package Tema;



public class Notification {
	String date;
	enum NotificationType {
		ADD,
		REMOVE,
		MODIFY
	}
	public NotificationType type;
	int ID_department;
	int ID_item;
	
	public Notification(NotificationType type, int ID_department, int ID_item) {
		this.type = type;
		this.ID_department = ID_department;
		this.ID_item = ID_item;
	}
	
	public NotificationType getType() {
		return type;
	}
	public int getID_item() {
		return ID_item;
	}
	public int getID_department() {
		return ID_department;
	}
	
	public String toString() {
		return type.toString() + ";" + ID_item + ";" + ID_department;
	}
}
