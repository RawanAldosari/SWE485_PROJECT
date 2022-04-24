package RoomScheduling;

import java.util.ArrayList;
import java.util.Scanner;

public class RoomScheduling {


	static Booking[][] combinedRooms = new Booking[6][5];

	static Doctor[] allDoctors = new Doctor[30];

	static int numOfAssignedDocs = 0;

	static ArrayList<Doctor> assignedDocs = new ArrayList<Doctor>();

	Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.println("Please enter the names of the consultants");
//		String[] consultants = in.nextLine().split(",");
		String[] consultants = "doctorC1,doctorC2,doctorC3,doctorC4,doctorC5,doctorC6".split(",");
		// sample input: 
		//doctorC1,doctorC2,doctorC3,doctorC4,doctorC5,doctorC6
		
		System.out.println("Please enter the names of the senior doctors");
//		String[] seniors = in.nextLine().split(",");
		String[] seniors = "doctorS1,doctorS2,doctorS3,doctorS4,doctorS5,doctorS6,doctorS7,doctorS8,doctorS9,doctorS10,doctorS11,doctorS12,doctorS13,doctorS14,doctorS15,doctorS16,doctorS17,doctorS18".split(",");
		// sample input: 
		//doctorS1,doctorS2,doctorS3,doctorS4,doctorS5,doctorS6,doctorS7,doctorS8,doctorS9,doctorS10,doctorS11,doctorS12,doctorS13,doctorS14,doctorS15,doctorS16,doctorS17,doctorS18
		
		System.out.println("Please enter the names of junior doctors");
//		String[] juniors = in.nextLine().split(",");
		String[] juniors = "doctorj1,doctorj2,doctorj3,doctorj4,doctorj5,doctorj6".split(",");
		// sample input: 
		//doctorj1,doctorj2,doctorj3,doctorj4,doctorj5,doctorj6

		System.out.println("Who are the doctors who will do the Brain Surgery?");
//		String[] brainSurgeons = in.nextLine().split(",");
		String[] brainSurgeons = "doctorj6,doctorS3,doctorS4,doctorS16".split(",");
		// sample input: 
		//doctorC4,doctorj6,doctorS3,doctorS4,doctorS16

		System.out.println("Who are the doctors who needs a surgery room with an x-ray machine?");
//		String[] doctorsWhoNeedsXRay = in.nextLine().split(",");
		String[] doctorsWhoNeedsXRay = "doctorj2,doctorS7,doctorS9,doctorS14,doctorS17".split(",");
		// sample input: 
		//doctorj2,doctorS7,doctorS9,doctorS14,doctorS17

		System.out.println("Who are the doctors who needs a surgery room equipped with on-line streaming?");
//		String[] doctorsWhoNeedsStreaming = in.nextLine().split(",");
		String[] doctorsWhoNeedsStreaming = "doctorC2,doctorS1,doctorS2,doctorS11".split(",");
		// sample input: 
		// doctorC2,doctorj3,doctorS1,doctorS2,doctorS11
		
		in.close();

		// Filling the doctors array
		for (int i = 0; i < seniors.length; i++) {

			allDoctors[i] = new Doctor(seniors[i], "senior", "normal");
		}
		
		for (int i = 0; i < juniors.length; i++) {

			allDoctors[i + (seniors.length)] = new Doctor(juniors[i], "junior", "normal");
		}

		for (int i = 0; i < consultants.length; i++) {

			allDoctors[i + (seniors.length + juniors.length)] = new Doctor(consultants[i], "consultant", "normal");
		}


		// Labeling doctors who need brain surgery
		for (int i = 0; i < allDoctors.length; i++) {

			for (int j = 0; j < brainSurgeons.length; j++) {

				if (allDoctors[i].name.equals(brainSurgeons[j])) {
					allDoctors[i].specialNeed = "brain";
				}

			}
		}

		// Labeling doctors who need x-ray
		for (int i = 0; i < allDoctors.length; i++) {

			for (int j = 0; j < doctorsWhoNeedsXRay.length; j++) {

				if (allDoctors[i].name.equals(doctorsWhoNeedsXRay[j])) {
					allDoctors[i].specialNeed = "x-ray";
				}

			}
		}

		// Labeling doctors who need online streaming
		for (int i = 0; i < allDoctors.length; i++) {

			for (int j = 0; j < doctorsWhoNeedsStreaming.length; j++) {

				if (allDoctors[i].name.equals(doctorsWhoNeedsStreaming[j])) {
					allDoctors[i].specialNeed = "streaming";
				}

			}
		}
		
		

		setTheRooms();

		if (schedule(combinedRooms)) {
			printRoomsSchedule(1);
			printRoomsSchedule(2);
			printRoomsSchedule(3);
		} else
			System.out.println("fail");

	}

	public static boolean schedule(Booking[][] combinedRooms) {
		return backtracking(0, 0);
	}

	
	public static boolean backtracking(int row, int col) {

		// Move to the next row if we reached the last column
		if (col == 5) {
			row += 1;
			col = 0;
		}

		// Base Case
		// If all doctors have been assigned, return true
		if (numOfAssignedDocs == 30) {
			return true;
		}

		// If we reached the last row and not all the doctors have been assigned
		// return to the first row
		if (row == 6) {
			row = 0;
		}

		// Assign Doctors
		for (int i = 0; i < allDoctors.length; i++) {

			boolean assignedBefore = false;

			if (allDoctors[i].assigned)
				assignedBefore = true;

			// Check if the assignment is valid
			if (!Constraints.isValid(row, col, allDoctors[i])) {
				// if not, move to the next doctor
				continue;
			}

			// Check if the doctor has been assigned before
			// DFS Search of all valid options
			if (!assignedDocs.contains(allDoctors[i])) {
				// if not, increase the number of assigned doctors
				numOfAssignedDocs++;
				// and add the doctor to the list of assigned doctors
				assignedDocs.add(allDoctors[i]);
			}

			// Assign doctor
			allDoctors[i].assigned = true;
			combinedRooms[row][col].doctors.add(allDoctors[i]);

			// only change the room type if it was normal
			if (combinedRooms[row][col].surgeryType.equals("normal") && !combinedRooms[row][col].doctors.isEmpty()) {
				combinedRooms[row][col].surgeryType = allDoctors[i].specialNeed;
			}

			// Assign doctors to next column
			if (backtracking(row, col + 1) == true) {
				return true;

			} else {

				// if we cannot successfully assign doctors, remove last assigned doctor

				if (!assignedBefore) {
					allDoctors[i].assigned = false;
					numOfAssignedDocs--;
					assignedDocs.remove(allDoctors[i]);
				}

				combinedRooms[row][col].doctors.remove(allDoctors[i]);
				if (combinedRooms[row][col].doctors.isEmpty()) {
					combinedRooms[row][col].surgeryType = "normal";
				}
			}

		}
		return false;
	}


	public static void printRoomsSchedule(int roomNum) {

		System.out.printf("\n\n\n------------------------\n");
		System.out.printf("Room " + roomNum + " Schedule");
		System.out.printf("\n------------------------\n");

		for (int i = 0; i < combinedRooms.length; i++) {
			for (int j = 0; j < combinedRooms[i].length; j++) {
				if (combinedRooms[i][j].roomNum == roomNum) {
					System.out.printf("\nday " + combinedRooms[i][j].day + "\n");
					System.out.println("Shift " + combinedRooms[i][j].shift);
					System.out.println("Surgery type " + combinedRooms[i][j].surgeryType);

					printDoctors(combinedRooms[i][j].doctors);
				}
			}
		}

	}

	public static void printDoctors(ArrayList<Doctor> docs) {

		for (int i = 0; i < docs.size(); i++) {

			// Just for now until we complete the assignment
			if (docs.get(i) == null) {
				System.out.println("Doctor " + docs.get(i));
			} else {
				System.out.println("Doctor " + docs.get(i).name);
				System.out.println("Type " + docs.get(i).type);

			}

		}
	}


	public static void setTheRooms() {
		// Booking shift, day, room number
		combinedRooms[0][0] = new Booking(1, 1, 1);
		combinedRooms[3][0] = new Booking(2, 1, 1);
		combinedRooms[0][1] = new Booking(1, 2, 1);
		combinedRooms[3][1] = new Booking(2, 2, 1);
		combinedRooms[0][2] = new Booking(1, 3, 1);
		combinedRooms[3][2] = new Booking(2, 3, 1);
		combinedRooms[0][3] = new Booking(1, 4, 1);
		combinedRooms[3][3] = new Booking(2, 4, 1);
		combinedRooms[0][4] = new Booking(1, 5, 1);
		combinedRooms[3][4] = new Booking(2, 5, 1);

		combinedRooms[1][0] = new Booking(1, 1, 2);
		combinedRooms[4][0] = new Booking(2, 1, 2);
		combinedRooms[1][1] = new Booking(1, 2, 2);
		combinedRooms[4][1] = new Booking(2, 2, 2);
		combinedRooms[1][2] = new Booking(1, 3, 2);
		combinedRooms[4][2] = new Booking(2, 3, 2);
		combinedRooms[1][3] = new Booking(1, 4, 2);
		combinedRooms[4][3] = new Booking(2, 4, 2);
		combinedRooms[1][4] = new Booking(1, 5, 2);
		combinedRooms[4][4] = new Booking(2, 5, 2);

		combinedRooms[2][0] = new Booking(1, 1, 3);
		combinedRooms[5][0] = new Booking(2, 1, 3);
		combinedRooms[2][1] = new Booking(1, 2, 3);
		combinedRooms[5][1] = new Booking(2, 2, 3);
		combinedRooms[2][2] = new Booking(1, 3, 3);
		combinedRooms[5][2] = new Booking(2, 3, 3);
		combinedRooms[2][3] = new Booking(1, 4, 3);
		combinedRooms[5][3] = new Booking(2, 4, 3);
		combinedRooms[2][4] = new Booking(1, 5, 3);
		combinedRooms[5][4] = new Booking(2, 5, 3);

	}

} // end class
