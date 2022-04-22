package RoomScheduling;

import java.util.ArrayList;

public class Booking {

    // We have assumed 1 for morning shift and 2 for afternoon shift
    int shift;
    
    // We have assumed 1 for Sunday and 2 for Monday 3 for Tuesday 4 for Wednesday 5 for Thursday
    int day;

    ArrayList<Doctor> doctors;
//    int arrayLength = 0; 

    int roomNum;
    
    //SurgeryType could be "needs x-ray" or "brain" or "needs streaming" or "normal"
    String surgeryType;


    public Booking(int shift, int day, ArrayList<Doctor> doctors, int roomNum, String surgeryType){

        this.shift=shift;
        this.day=day;
        this.doctors=doctors;
        this.roomNum=roomNum;
        this.surgeryType=surgeryType;
    }
    

    public Booking(int shift, int day, int roomNum){

        this.shift=shift;
        this.day=day;
        this.doctors=new ArrayList<Doctor>();
        this.roomNum=roomNum;
        this.surgeryType="normal";
    }
}
