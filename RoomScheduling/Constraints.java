package RoomScheduling;

public class Constraints {

	// INFORMATION
	// C1: consultants, C2: senior doctors, C3: junior doctors, C4: Brain Surgery
	// doctors, C5: Doctors who need an x-ray, C6: Doctors who need online streaming

	// We check this constraint in RoomScheduling class where we check if all the
	// doctors are assigned which means that each doctoe does at least 1 surgery
	// Each doctor should do at least 1 surgery
	// C1 + C2 + C3 >= 30

	// Constraint#1
	// Consultants don't work during afternoon 1-4
	// (R12j != C1) and (R22j != C1) and (R32j != C1)

	// Constraint#2
	// A doctor should not be assigned to more than one room at the same time (We
	// missed it in phase 1)

	// Constraint#3
	// Room 1 got assigned junior doctors if and only if room 1 got assigned senior
	// doctors
	// (R1ij = C3) if and only if (R1ij = C2)

	// Constraint #4
	// Room 2 got assigned junior doctors if and only if room 2 got assigned senior
	// doctors
	// (R2ij = C3) if and only if (R2ij = C2)

	// Constraint #5
	// Junior doctors who joined senior doctors cannot use room 3
	// R3ij != C2 + C3

	// Constraint #6
	// Brain surgeries cannot be done in any room at any shift on Sunday, Tuesday
	// and Thursday
	// (R1i1,3,5 != C4) and (R2i1,3,5 != C4) and (R3i1,3,5 != C4)

	// Constraint #7
	// Doctors who need an x-ray cannot use room 2 and room 3
	// (R2ij != C5 ) and (R3ij != C5)

	// Constraint #8
	// Doctors who need an online streaming cannot use room 1 and room 3
	// (R1ij != C6) and (R3ij != C6)

	// Domain of room 1 is senior or junior

	// Domain of room 1 in the morning (shift 1) is Consultants (since they only
	// work in the morning) and the domain above (senior and junior)

	// Domain of room 2 is senior or junior

	// Domain of room 2 in the morning (shift 1) is Consultants (since they only
	// work in the morning) and the domain above (senior and junior)

	// Domain of room 3 is consultant since juniors should join seniors and the room
	// is too small and adding senior to this domain is just a waist of resources
	// and knowledge that juniors need

	public static boolean isValid(int row, int col, Doctor doc) {

		Booking currentBooking = RoomScheduling.combinedRooms[row][col];

		if (!doc.specialNeed.equals("normal")) {
			// constraint 7
			if (doc.specialNeed.equals("x-ray") && currentBooking.roomNum != 1) {
				return false;
			}
			// constraint 6
			if (doc.specialNeed.equals("brain")
					&& (currentBooking.day != 1 || currentBooking.day != 3 || currentBooking.day != 5)) {
				return false;
			}
			// constraint 8
			if (doc.specialNeed.equals("streaming") && currentBooking.roomNum != 2) {
				return false;
			}
		}

		// constraint 2

		for (int i = 0; i < 6; i++) {
			if (RoomScheduling.combinedRooms[i][col].doctors.contains(doc))
				return false;

		}

		if (doc.type.equals("consultant")) {
			return isValidC(row, col, doc, currentBooking);
		}

		if (doc.type.equals("senior")) {
			return isValidS(row, col, doc, currentBooking);
		}

		else
			return isValidJ(row, col, doc, currentBooking);

	}

	public static boolean isValidC(int row, int col, Doctor doc, Booking booking) {
		// constraint 1
		if (booking.shift == 2) {
			return false;
		}

		if (doc.assigned) {
			return false;
		}
		if (!booking.doctors.isEmpty())
			return false;

		return true;
	}

	public static boolean isValidS(int row, int col, Doctor doc, Booking booking) {
		if (!booking.doctors.isEmpty())
			return false;
//		
//		if(doc.assigned) {
////			if (RoomScheduling.checkAssignmetOfSeniorDoctors())
////				return true; 
////			else
//				return false; 
//		}

		return true;
	}

	public static boolean isValidJ(int row, int col, Doctor doc, Booking booking) {
		// constraint 5
		if (booking.roomNum == 3) {
			return false;
		}
		if (doc.assigned) {
			return false;
		}
		if (booking.doctors.isEmpty()) {
			return false;
		}
		// constraint 4 and 3
		if (!booking.doctors.get(0).type.equals("senior")) {
			return false;
		}
		return true;
	}

}
