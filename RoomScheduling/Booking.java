package RoomScheduling;

public class Booking {

    // We have assumed 1 for morning shift and 2 for afternoon shift
    int shift;
    
    // We have assumed 1 for Sunday and 2 for Monday 3 for Tuesday 4 for Wednesday 5 for Thursday
    int day;

    Doctor[] doctors;

    int roomNum;
    
    //SurgeryType could be "needs x-ray" or "brain" or "needs streaming" or "normal"
    String surgeryType;


    public Booking(int shift, int day, Doctor[] doctors, int roomNum, String surgeryType){

        this.shift=shift;
        this.day=day;
        this.doctors=doctors;
        this.roomNum=roomNum;
        this.surgeryType=surgeryType;
    }
    

    public Booking(int shift, int day, int roomNum){

        this.shift=shift;
        this.day=day;
        this.doctors=new Doctor[2];
        this.roomNum=roomNum;
        this.surgeryType="normal";
    }
}
