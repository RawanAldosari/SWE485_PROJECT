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

	static Booking[][] allRooms = { Room1, Room2, Room3 };

	static Doctor[] allDoctors = new Doctor[30];

	Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.println("Please enter the names of the consultants");
		String[] consultants = in.nextLine().split(",");

		System.out.println("Please enter the names of the senior doctors");
		String[] seniors = in.nextLine().split(",");

		System.out.println("Please enter the names of junior doctors");
		String[] juniors = in.nextLine().split(",");

		System.out.println("Who are the doctors who will do the Brain Surgery?");
		String[] brainSurgeons = in.nextLine().split(",");

		System.out.println("Who are the doctors who needs a surgery room with an x-ray machine?");
		String[] doctorsWhoNeedsXRay = in.nextLine().split(",");

		System.out.println("Who are the doctors who needs a surgery room equipped with on-line streaming?");
		String[] doctorsWhoNeedsStreaming = in.nextLine().split(",");

		in.close();

		// Filling the doctors array
		for (int i = 0; i < consultants.length; i++) {

			allDoctors[i] = new Doctor(consultants[i], "consultant", "none");
		}

		for (int i = 0; i < seniors.length; i++) {

			allDoctors[i + (consultants.length - 1)] = new Doctor(seniors[i], "senior", "none");
		}

		for (int i = 0; i < juniors.length; i++) {

			allDoctors[i + (seniors.length + consultants.length - 1)] = new Doctor(juniors[i], "junior", "none");
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

		setTheRooms();

//		backtracking(allRooms); 
//		printRoomSchedule(Room1);
//		printRoomSchedule(Room2);
//		printRoomSchedule(Room3);

//		backtrack();
		if (backtracking(allRooms)) {

			printRoomSchedule(Room1);
			printRoomSchedule(Room2);
			printRoomSchedule(Room3);
		} else {
			System.out.println("fail");
		}

	}

	////////////// backtracking method vvvvvvvvvv rawan

	public static boolean backtracking(Booking[][] rooms) {
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				for (int h = 0; h < allDoctors.length; h++) {
					if (checkRoomAvailability(rooms[i][j], allDoctors[h].type)) {
						if (checkAssignmentValidity(rooms[i], j, h)) {
//							if (!allDoctors[h].assigned) {
							allDoctors[h].assigned = true;
							rooms[i][j].doctors.add(allDoctors[h]);
							System.out.println("doctor assigned in room" + (i + 1));
							if (backtracking(rooms)) {
								return true;
							} else {
								allDoctors[h].assigned = false;
								rooms[i][j].doctors.remove(allDoctors[h]);
								System.out.println("doctor removed from room" + (i + 1));
							}
//							}
						} 
					} 
				}
				return false;
			}
		}
		return true;
	}


	public static boolean checkRoomAvailability(Booking room, String type) {

		if (room.doctors.isEmpty()) {
			return true;
		}

		if (type.equals("consultant")) {
			return false;
		}

		if (type.equals("senior")) {
			if (room.doctors.get(0).type.equals("junior") && (room.doctors.size() == 1)) {
				return true;
			}
			else return false; 
		}

		if (room.doctors.get(0).type.equals("senior") && (room.doctors.size() == 1)) {
			return true;
		}
//
//		for (int i = 0; i < room.doctors.size(); i++) {
//			if (room.doctors.get(i).type.equals("senior")) {
//				return true;
//			}
//		}

		return false;
	}

	/////////////////////// ^^^^^^^ rawan

	public static void backtrack() {

		// Check if all the doctors are assigned

		boolean isCompleteAssignment = checkAssignmetOfDoctors();
		if (isCompleteAssignment) {
			return;
		}

		// Select unassigned variable (doctor)
		else {

			// Loop until all the doctors are assigned
			// Just commented it to avoid infinite loop while the assignment is incomplete
			while (!checkAssignmetOfDoctors()) {

				for (int i = 0; i < allDoctors.length; i++) {
					if (allDoctors[i].type.toLowerCase().equals("junior")) {
						System.out.println("found a junior doctor");

						// Room 3 only + senior must be present

						// We search for a senior

						for (int j = 0; j < allDoctors.length; j++) {
							System.out.println("looking for a senior");

							if (allDoctors[j].type.toLowerCase().equals("senior")) {

								System.out.println("found a senior doctor");

								// X-ray only in room 1
								if (allDoctors[j].specialNeed.toLowerCase().equals("x-ray")) {
									System.out.println("this senior needs x-ray");

									// Search for an empty booking in room 1
									for (int y = 0; y < Room1.length; y++) {

										if (Room1[y].doctors.isEmpty()) {
											ArrayList<Doctor> d = new ArrayList<Doctor>();
											d.add(allDoctors[i]);
											d.add(allDoctors[j]);
											Room1[y].doctors = d;
											Room1[y].roomNum = 1;
											Room1[y].surgeryType = "needs x-ray";
											room1NumOfBookedSlots++;

											allDoctors[i].assigned = true;
											allDoctors[j].assigned = true;
											// Check if it satisfies the constraints or not to delete the last assigned
											// value
											break;

										}
									}

								}

								// Streaming only in room 2
								if (allDoctors[j].specialNeed.toLowerCase().equals("streaming")) {
									System.out.println("this senior needs online streaming");

									// Search for an empty booking in room 2
									for (int y = 0; y < Room2.length; y++) {

										if (Room2[y].doctors.isEmpty()) {
											ArrayList<Doctor> d = new ArrayList<Doctor>();
											d.add(allDoctors[i]);
											d.add(allDoctors[j]);
											Room2[y].doctors = d;
											Room2[y].roomNum = 1;
											Room2[y].surgeryType = "needs streaming";
											room2NumOfBookedSlots++;

											allDoctors[i].assigned = true;
											allDoctors[j].assigned = true;
											// Check if it satisfies the constraints or not to delete the last assigned
											// value
											break;

										}
									}

								}

								// Brain only on days 2 & 4
								if (allDoctors[j].specialNeed.toLowerCase().equals("brain")) {

									boolean foundARoom = false;
									// search for an empty day 2 or 4 in room1 then if not found search in room 2

									// Search for an empty booking in room 1 on day 2 or 4
									for (int y = 0; y < Room1.length; y++) {

										if (Room1[y].doctors.isEmpty() && (Room1[y].day == 2 || Room1[y].day == 4)) {
											ArrayList<Doctor> d = new ArrayList<Doctor>();
											d.add(allDoctors[i]);
											d.add(allDoctors[j]);
											Room1[y].doctors = d;
											Room1[y].roomNum = 1;
											Room1[y].surgeryType = "brain";
											room1NumOfBookedSlots++;

											allDoctors[i].assigned = true;
											allDoctors[j].assigned = true;
											foundARoom = true;
											// Check if it satisfies the constraints or not to delete the last assigned
											// value
											break;

										}

									}

									// We couldn't find an empty booking in room 1 on day 2 or 4
									// We search in room 2
									if (!foundARoom) {

										for (int y = 0; y < Room2.length; y++) {

											if (Room2[y].doctors.isEmpty()
													&& (Room2[y].day == 2 || Room2[y].day == 4)) {
												ArrayList<Doctor> d = new ArrayList<Doctor>();
												d.add(allDoctors[i]);
												d.add(allDoctors[j]);
												Room2[y].doctors = d;
												Room2[y].roomNum = 1;
												Room2[y].surgeryType = "brain";
												room2NumOfBookedSlots++;

												allDoctors[i].assigned = true;
												allDoctors[j].assigned = true;
												foundARoom = true;
												// Check if it satisfies the constraints or not to delete the last
												// assigned
												// value
												break;

											}

										}

									}

								}

							}

						}

					}

				}

			}

		}

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

	/////////////////////////////////// BY MODHI
	/////////////////////////////////// ////////////////////////////////////////////

	public static void assignment(Booking[] Room) {
		// what if the room bookings are all full and it kept looping? (use
		// checkRoomAvailability())
		// meaning there is no empty spot, but the for loop kept going for all doctors

		// we want to assign seniors at doctors[0] and juniors at doctors[1]

		assignSeniors(Room);
		assignConsultants(Room);
		assignJuniors(Room);

		// create method to fill the empty spots in a room by an already assigned doctor
		// , there will be a 6 empty senior spots
		// since seniors = 18 and consultants = 6

	} // end func

	public static void assignConsultants(Booking[] Room) {

		for (int i = 0; i < allDoctors.length; i++) {

			if (allDoctors[i].type.toLowerCase().equals("consultant")) {
				System.out.println("found a consultant doctor");

				// loop throug the room to find unfull booking and assign senior to it

				for (int j = 0; j < Room.length; j++) {

					if (!checkRoomAvailability(Room[j], allDoctors[i].type))
						return;
					if (!Room[j].doctors.isEmpty()) {// should we place the consultant at Room[j].doctors[0]?
						// كأنه بيسوي عملية لحاله ومعه جونيور
						// go to next
						System.out.println("spot is already taken");
						continue;
					}

					Room[j].doctors.add(allDoctors[i]);
					Room[j].surgeryType = allDoctors[i].specialNeed;

					allDoctors[i].assigned = true;

					boolean isValid = checkAssignmentValidity(Room, j, i);

					if (!isValid) { // change assignment, and go to other booking in Room
						Room[j].doctors.clear();
						Room[j].surgeryType = null;
						allDoctors[i].assigned = false;
					}

				}

			}
		}

	}

	public static void assignSeniors(Booking[] Room) {
		// بيكون عندي ٦ اماكن سينيورز فاضية لازم اعبيها من الاسايند
		// Seniors
		for (int i = 0; i < allDoctors.length; i++) {

			if (allDoctors[i].type.toLowerCase().equals("senior")) {
				System.out.println("found a senior doctor");

				if (allDoctors[i].assigned == false) {
					// loop throug the room to find unfull booking and assign senior to it

					for (int j = 0; j < Room.length; j++) {

						if (!checkRoomAvailability(Room[j], allDoctors[i].type))
							return;

						if (!Room[j].doctors.isEmpty()) {
							// go to next
							System.out.println("spot is already taken");
							continue;
						}

						Room[j].doctors.add(allDoctors[i]);
						Room[j].surgeryType = allDoctors[i].specialNeed;

						allDoctors[i].assigned = true;

						boolean isValid = checkAssignmentValidity(Room, j, i);

						if (!isValid) { // change assignment, and go to other booking in Room
							Room[j].doctors.clear();
							;
							Room[j].surgeryType = null;
							allDoctors[i].assigned = false;
						}

					}

				}

			}
		}

	}

	public static void assignJuniors(Booking[] Room) {

		// not all doctors have special needs, a junior doctor with a special need
		// should be assigned with a senior that doesn't have a special need
		// Juniors
		for (int i = 0; i < allDoctors.length; i++) {

			if (allDoctors[i].type.toLowerCase().equals("junior")) {
				System.out.println("found a junior doctor");

				// loop throug the room to find unfull booking and assign senior to it

				for (int j = 0; j < Room.length; j++) {

					if (!checkRoomAvailability(Room[j], allDoctors[i].type)) // room is full
						return;

					if (Room[j].doctors.get(1) != null) {
						// go to next
						System.out.println("spot is already taken");
						continue;
					}

					// junior has a special need
					if (allDoctors[i].specialNeed.equals("brain") || allDoctors[i].specialNeed.equals("x-ray")
							|| allDoctors[i].specialNeed.equals("streaming")) {
						// assign special juniors to none seniors, or to seniors with the same special
						// need
						if (Room[j].doctors.get(0).specialNeed.equals("none")
								|| Room[j].doctors.get(0).specialNeed.equals(allDoctors[i].specialNeed)) {

//	            	 Room[j].doctors[1]= allDoctors[i];
							Room[j].doctors.add(allDoctors[i]);
							Room[j].surgeryType = allDoctors[i].specialNeed;

							allDoctors[i].assigned = true;

							boolean isValid = checkAssignmentValidity(Room, j, i);

							if (!isValid) { // change assignment, and go to other booking in Room
								Room[j].doctors.remove(1);
								Room[j].surgeryType = null;
								allDoctors[i].assigned = false;
							}

						}

					}

					// junior doesn't have a special need , so it can be assigned with any senior
					// (none)
					else {
						Room[j].doctors.add(allDoctors[i]);
						// I commented the line below bc it will take the senior special need
						// Room[j].surgeryType = allDoctors[i].specialNeed;

						allDoctors[i].assigned = true;

						boolean isValid = checkAssignmentValidity(Room, j, i);

						if (!isValid) { // change assignment, and go to other booking in Room
							Room[j].doctors.remove(1);
							Room[j].surgeryType = null;
							allDoctors[i].assigned = false;
						}
					}

				}

			}
		}

	}

// check const( 6 or 7 or 8 )
// check const( 3 and 4 and 5) for juniors
// check const( 2 ) for all
// check const( 1 ) for consultant

	public static boolean checkAssignmentValidity(Booking[] room, int bookingIndex, int allDocIndex) {

//		System.out.println("here333333333333");

		int times = 0;

		// check constraints based on doc type + specialNeed

		// check special need + cons 2

		if (allDoctors[allDocIndex].type.equals("senior")) {
//			boolean validBasedOnSpecialNeed = false; // had to comment this part out
//					checkBasedOntype(room, bookingIndex, allDocIndex);

			if (allDoctors[allDocIndex].specialNeed.equals("normal")) {

				if (room[bookingIndex].roomNum == 1) {
					times = checkTimesAssigned(Room2, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 2) {
					times = checkTimesAssigned(Room1, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 3) {
					times = checkTimesAssigned(Room2, Room1, room[bookingIndex], allDoctors[allDocIndex].name);
				}

				if (times != 1) {
					System.out.println("failed11111");
					return false;
				} else
					return true;

			}
			if (validBasedOnSpecialNeed(room, bookingIndex, allDocIndex)) {

				// check const 2

				if (room[bookingIndex].roomNum == 1) {
					times = checkTimesAssigned(Room2, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 2) {
					times = checkTimesAssigned(Room1, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 3) {
					times = checkTimesAssigned(Room2, Room1, room[bookingIndex], allDoctors[allDocIndex].name);
				}

				if (times != 1) {
					System.out.println("failed22222");
					return false;
				} else
					return true;

			} else
				System.out.println("failed33333");
				return false;
		}

		// check special need
		// check const( 3 and 4 and 5) + const 2
		// ٣ و ٤ يشيك عليهم قبل ما يجي لهذي الميثود، لما ما يسوي اساينمنت للجونيور لما
		// يتأكد انه السينيور مسوي له اساينمنت
		else if (allDoctors[allDocIndex].type.equals("junior")) {

			/*
			 * boolean validBasedOnSpecialNeed =
			 * checkBasedOntype(room,bookingIndex,allDocIndex); if(!validBasedOnSpecialNeed)
			 * return false;
			 * 
			 * // const 5
			 */

			if (room[bookingIndex].roomNum == 3)
				return false;

			if (allDoctors[allDocIndex].specialNeed.equals("normal")) {

				if (room[bookingIndex].roomNum == 1) {
					times = checkTimesAssigned(Room2, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 2) {
					times = checkTimesAssigned(Room1, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 3) {
					times = checkTimesAssigned(Room2, Room1, room[bookingIndex], allDoctors[allDocIndex].name);
				}

				if (times != 1) {
					System.out.println("failed11111jjjjj");
					return false;
				} else
					return true;

			}

			if (validBasedOnSpecialNeed(room, bookingIndex, allDocIndex)) {

				// check const 2

				if (room[bookingIndex].roomNum == 1) {
					times = checkTimesAssigned(Room2, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 2) {
					times = checkTimesAssigned(Room1, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 3) {
					times = checkTimesAssigned(Room2, Room1, room[bookingIndex], allDoctors[allDocIndex].name);
				}

				if (times != 1) {
					System.out.println("failed222222jjj");
					return false;
				} else
					return true;

			} else
				System.out.println("failed3333333jjjj");
				return false;

		}

		else { // type == consultant

			if (room[bookingIndex].shift == 2) {
				System.out.println("failed11111ccccc");
				return false;
			}

			if (allDoctors[allDocIndex].specialNeed.equals("normal")) {

				if (room[bookingIndex].roomNum == 1) {
					times = checkTimesAssigned(Room2, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 2) {
					times = checkTimesAssigned(Room1, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 3) {
					times = checkTimesAssigned(Room2, Room1, room[bookingIndex], allDoctors[allDocIndex].name);
				}

				if (times != 1) {
					System.out.println("failed22222ccccc");
					return false;
				} else
					return true;

			}

			if (validBasedOnSpecialNeed(room, bookingIndex, allDocIndex)) {

				// check const 2

				if (room[bookingIndex].roomNum == 1) {
					times = checkTimesAssigned(Room2, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 2) {
					times = checkTimesAssigned(Room1, Room3, room[bookingIndex], allDoctors[allDocIndex].name);
				}
				if (room[bookingIndex].roomNum == 3) {
					times = checkTimesAssigned(Room2, Room1, room[bookingIndex], allDoctors[allDocIndex].name);
				}

				if (times != 1) {
					System.out.println("failed3333333ccccc");
					return false;
				} else
					return true;

			} else
				System.out.println("failed44444ccccc");
				return false;

		}

	}// end func

	////////////////// for const 2 vvvvvvvvvvvvvvv rawan

	public static int checkTimesAssigned(Booking[] roomX, Booking[] roomY, Booking booking, String name) {

		int times = 1;
		System.out.println("here ----------111");

		for (int i = 0; i < roomX.length; i++) {

			if ((roomX[i].day == booking.day) && (roomX[i].shift == booking.shift) && (roomX[i].arrayLength > 0)) {
				for (int j = 0; j < roomX[i].doctors.size(); j++) {
					System.out.println("here:" + roomX[i].doctors.get(j).name + " og: " + name);
					if (roomX[i].doctors.get(j).name.equals(name)) {
						System.out.println("found duplicate");
						times++;
					}
				}
			}

		}

		for (int i = 0; i < roomY.length; i++) {

			if ((roomY[i].day == booking.day) && (roomY[i].shift == booking.shift) && (roomY[i].arrayLength > 0)) {
				for (int j = 0; j < roomY[i].doctors.size(); j++) {
					System.out.println("here:" + roomY[i].doctors.get(j).name + " og: " + name);
					if (roomY[i].doctors.get(j).name.equals(name)) {
						System.out.println("found duplicate");
						times++;
					}
				}
			}

		}

		return times;

	} // --checkTimesAssigned end--//

	////////////////// ^^^^^^^^^^^^^^^^^^^^^^^^^ rawan

	public static boolean validBasedOnSpecialNeed(Booking[] room, int bookingIndex, int allDocIndex) {

		// brain surgeons work only on 2 & 4 ( const 6 )
		if (allDoctors[allDocIndex].specialNeed.equals("brain")) { // check the day

			if ((room[bookingIndex].day == 1 || room[bookingIndex].day == 3 || room[bookingIndex].day == 5)) {

				return false;
			}

		}

		// doctors who need x-ray only allowed in room 1 ( const 7 )
		if (allDoctors[allDocIndex].specialNeed.equals("x-ray")) {

			if (room[bookingIndex].roomNum == 2 || room[bookingIndex].roomNum == 3) {

				return false;
			}

		}

		// doctors who need streaming only allowed in room 2 ( const 8 )
		if (allDoctors[allDocIndex].specialNeed.equals("streaming")) {

			if (room[bookingIndex].roomNum == 1 || room[bookingIndex].roomNum == 3) {

				return false;
			}

		}

		return true; // special need none

	}// end func

} // end class
