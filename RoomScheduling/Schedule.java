package RoomScheduling;

public class Schedule {
	public int number; 
	public int shift; 
	public String day; 
	public String[] doctors; 
	
	public Schedule(int number, int shift, String day, String[] doctors) {
		super();
		this.number = number;
		this.shift = shift;
		this.day = day;
		this.doctors = doctors;
	}
}
