package RoomScheduling;

public class Constraints {
	
	public boolean isValid() {
		return false;
	}
	
	// Each doctor should do at least one surgery
	public boolean doctorAssigned() {
		return false;
	}
	
	// Consultant doctors don’t work during the afternoon shift of any day (1PM - 4PM)
	public boolean validConsultantAssignment() {
		return false;
	}
	
	// Junior doctors who joined senior doctors cannot use room 3
	// Room 1 got assigned junior doctors if and only if room 1 got assigned senior doctors
	public boolean validJuniorAssignment(String doctor, Schedule schedule) {
		if (schedule.number == 3)
			return false; 
		return true;
		
		
	}
	
	// Room 2 got assigned junior doctors if and only if room 2 got assigned senior doctors
	public boolean validJuniorAssignmentRoom2(String doctor, Schedule schedule, String[] seniors) {		
		for(int i = 0; 0 <= schedule.doctors.length; ++i) {
			for (int j = 0 ; 0 <= seniors.length ; j++) {
				if (schedule.doctors[i].equals(seniors[j]))
				return true; 
			}
		}
		return false; 
	}
	
	// Room 1 got assigned junior doctors if and only if room 1 got assigned senior doctors
	public boolean validJuniorAssignmentRoom1(String doctor, Schedule schedule, String[] seniors) {		
		for(int i = 0; 0 <= schedule.doctors.length; ++i) {
			for (int j = 0 ; 0 <= seniors.length ; j++) {
				if (schedule.doctors[i].equals(seniors[j]))
				return true; 
			}
		}
		return false; 
	}
	
	// Brain surgeries cannot be done in any room at any shift on Sunday, Tuesday and Thursday
	public boolean validBrainSurgery() {
		return false;
	}
	
	// Doctors who need an x-ray cannot use room 2 and room 3
	public boolean validXRayAssignment() {
		return false;
	}
	
	// Doctors who need an online streaming cannot use room 1 and room 3
	public boolean validStreamingAssignment() {
		return false;
	}
	
	// Doctors cannot be assigned to more than one room at a certain time and day 
	public boolean DoctorAssignedOnce() {
		return false;
	}
	
	
}
