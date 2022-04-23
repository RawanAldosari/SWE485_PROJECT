package RoomScheduling;

public class Doctor {

    String name;
    String type;
    boolean assigned;

    // specialNeed could be "brain", "x-ray","streaming" "none"
    String specialNeed;

    // Do we add the number of surgeries?

    public Doctor(String name, String type, String specialNeed){

        this.name = name;
        this.type = type;
        // Initially the doctor is not assigned to any room
        this.assigned = false;
        this.specialNeed=specialNeed;
    }
    
}
