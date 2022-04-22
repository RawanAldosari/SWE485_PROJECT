package RoomScheduling;

import java.util.ArrayList;
import java.util.Scanner;

public class RoomScheduling {

	static Booking[] Room1 = new Booking[10];
	// We may ignore the variable below
	static int room1NumOfBookedSlots = 0;
	static Booking[] Room2 = new Booking[10];
	// We may ignore the variable below
	static int room2NumOfBookedSlots = 0;
	static Booking[] Room3 = new Booking[10];
	// We may ignore the variable below
	static int room3NumOfBookedSlots = 0;

//	static ArrayList<Booking> combinedRooms = new ArrayList<Booking>(30);
	static Booking[][] combinedRooms = new Booking[6][5];

	static Booking[][] allRooms = { Room1, Room2, Room3 };

	static Doctor[] allDoctors = new Doctor[30];

	Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.println("Please enter the names of the consultants");
//		String[] consultants = in.nextLine().split(",");
		String[] consultants = "rawanC1,rawanC2,rawanC3,rawanC4,rawanC5,rawanC6".split(",");

		System.out.println("Please enter the names of the senior doctors");
//		String[] seniors = in.nextLine().split(",");
		String[] seniors = "rawanS1,rawanS2,rawanS3,rawanS4,rawanS5,rawanS6,rawanS7,rawanS8,rawanS9,rawanS10,rawanS11,rawanS12,rawanS13,rawanS14,rawanS15,rawanS16,rawanS17,rawanS18"
				.split(",");

		System.out.println("Please enter the names of junior doctors");
//		String[] juniors = in.nextLine().split(",");
		String[] juniors = "rawanj1,rawanj2,rawanj3,rawanj4,rawanj5,rawanj6".split(",");

		System.out.println("Who are the doctors who will do the Brain Surgery?");
//		String[] brainSurgeons = in.nextLine().split(",");
		String[] brainSurgeons = "rawanS3,rawanS4,rawanj4".split(",");

		System.out.println("Who are the doctors who needs a surgery room with an x-ray machine?");
//		String[] doctorsWhoNeedsXRay = in.nextLine().split(",");
		String[] doctorsWhoNeedsXRay = "rawanS7,rawanS9,rawanS14,rawanS17".split(",");

		System.out.println("Who are the doctors who needs a surgery room equipped with on-line streaming?");
//		String[] doctorsWhoNeedsStreaming = in.nextLine().split(",");
		String[] doctorsWhoNeedsStreaming = "rawanj6,rawanS1,rawanS2,rawanS11".split(",");

		in.close();

		// Filling the doctors array
		for (int i = 0; i < consultants.length; i++) {

			allDoctors[i] = new Doctor(consultants[i], "consultant", "none");
		}

		for (int i = 0; i < seniors.length; i++) {

			allDoctors[i + (consultants.length)] = new Doctor(seniors[i], "senior", "none");
		}

		for (int i = 0; i < juniors.length; i++) {

			allDoctors[i + (seniors.length + consultants.length)] = new Doctor(juniors[i], "junior", "none");
		}

		for (int i = 0; i < allDoctors.length; i++) {
			System.out.println(allDoctors[i].name);

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

		setTheRooms2();

		if (solve(combinedRooms)) {
			System.out.println("success");
			printRoomsSchedule(1);
			printRoomsSchedule(2);
			printRoomsSchedule(3);
		} else
			System.out.println("fail");

	}

	public static boolean solve(Booking[][] combinedRooms) {
		return helper(0, 0);
	}

	public static boolean helper(int row, int col) {
//		int i = 0 ; 
		if (col == 5) {
			row += 1;
			col = 0;
		}

		if (row == 6)
			return true;

		for (int i = 0; i < allDoctors.length; i++) {
//		while(!checkAssignmetOfDoctors2()) {

			if (!Constraints.isValid(row, col, allDoctors[i])) {
//				System.out.println("doctor amoved ob");
				continue;
			}
			allDoctors[i].assigned = true;
			System.out.println("doctor assigned in room" + row);
			combinedRooms[row][col].doctors.add(allDoctors[i]);
//			i++;
			if (helper(row, col + 1) == true) {
				return true;
			} else {
//				i--;
				System.out.println("doctor removed from room" + row);
				allDoctors[i].assigned = false;
				combinedRooms[row][col].doctors.remove(allDoctors[i]);
			}
//		}

		}
		return false;
	}

	public static void printRoomSchedule(Booking[] room) {

		System.out.println("Room " + room[0].roomNum + " Schedule");

		for (int i = 0; i < room.length; i++) {

			System.out.println("day " + room[i].day);
			System.out.println("Shift " + room[i].shift);
			System.out.println("Surgery type " + room[i].surgeryType);

			printDoctors(room[i].doctors);
		}

	}

	public static void printRoomsSchedule(int roomNum) {

		System.out.println("Room " + roomNum + " Schedule");
		for (int i = 0; i < combinedRooms.length; i++) {
			for (int j = 0; j < combinedRooms[i].length; j++) {
				if (combinedRooms[i][j].roomNum == roomNum) {
					System.out.println("day " + combinedRooms[i][j].day);
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

	public static boolean checkAssignmetOfDoctors() {

		boolean isAllDoctorsAssigned = true;

		for (int i = 0; i < allDoctors.length; i++) {

			isAllDoctorsAssigned = isAllDoctorsAssigned && allDoctors[i].assigned;
		}

		return isAllDoctorsAssigned;

	}

	public static boolean checkAssignmetOfSeniorDoctors() {

		for (int i = 0; i < allDoctors.length; i++) {
			if (allDoctors[i].type.equals("senior") && (allDoctors[i].assigned == false))
				return false;
		}

		return true;

	}

	public static boolean checkAssignmetOfDoctors2() {

		for (int i = 0; i < allDoctors.length; i++) {
			if (allDoctors[i].assigned == false)
				return false;
		}

		return true;

	}

	public static void setTheRooms() {
		// Booking shift, day, room number
		Room1[0] = new Booking(1, 1, 1);
		Room1[1] = new Booking(2, 1, 1);
		Room1[2] = new Booking(1, 2, 1);
		Room1[3] = new Booking(2, 2, 1);
		Room1[4] = new Booking(1, 3, 1);
		Room1[5] = new Booking(2, 3, 1);
		Room1[6] = new Booking(1, 4, 1);
		Room1[7] = new Booking(2, 4, 1);
		Room1[8] = new Booking(1, 5, 1);
		Room1[9] = new Booking(2, 5, 1);

		Room2[0] = new Booking(1, 1, 2);
		Room2[1] = new Booking(2, 1, 2);
		Room2[2] = new Booking(1, 2, 2);
		Room2[3] = new Booking(2, 2, 2);
		Room2[4] = new Booking(1, 3, 2);
		Room2[5] = new Booking(2, 3, 2);
		Room2[6] = new Booking(1, 4, 2);
		Room2[7] = new Booking(2, 4, 2);
		Room2[8] = new Booking(1, 5, 2);
		Room2[9] = new Booking(2, 5, 2);

		Room3[0] = new Booking(1, 1, 3);
		Room3[1] = new Booking(2, 1, 3);
		Room3[2] = new Booking(1, 2, 3);
		Room3[3] = new Booking(2, 2, 3);
		Room3[4] = new Booking(1, 3, 3);
		Room3[5] = new Booking(2, 3, 3);
		Room3[6] = new Booking(1, 4, 3);
		Room3[7] = new Booking(2, 4, 3);
		Room3[8] = new Booking(1, 5, 3);
		Room3[9] = new Booking(2, 5, 3);

	}

	public static void setTheRooms2() {
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
