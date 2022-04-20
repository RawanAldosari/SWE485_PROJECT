import java.util.Scanner;

public class RoomScheduling {

    static Booking[] Room1= new Booking[10];
    //We may ignore the variable below
    static int room1NumOfBookedSlots = 0;
    static Booking[] Room2= new Booking[10];
    //We may ignore the variable below
    static int room2NumOfBookedSlots = 0;
    static Booking[] Room3= new Booking[10];
    //We may ignore the variable below
    static int room3NumOfBookedSlots = 0;


    
    
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
        for (int i=0; i<consultants.length; i++){

            allDoctors[i]=new Doctor(consultants[i], "consultant", "none");
        }

        for (int i=0; i<seniors.length; i++){

            allDoctors[i+(consultants.length-1)]=new Doctor(seniors[i], "senior", "none");
        }

        for (int i=0; i<juniors.length; i++){

            allDoctors[i+(seniors.length+consultants.length-1)]=new Doctor(juniors[i], "junior", "none");
        }
        
        
                
        for (int i=0; i<allDoctors.length; i++){
           System.out.println(allDoctors[i].name);
            
            }


         // Labeling doctors who need brain surgery
        for (int i = 0; i<allDoctors.length; i++ ){

            for (int j=0; j<brainSurgeons.length; j++){

                if (allDoctors[i].name.equals(brainSurgeons[j])){
                    allDoctors[i].specialNeed="brain";
                }
              
            }
        }


        // Labeling doctors who need x-ray
        for (int i = 0; i<allDoctors.length; i++ ){

            for (int j=0; j<doctorsWhoNeedsXRay.length; j++){

                if (allDoctors[i].name.equals(doctorsWhoNeedsXRay[j])){
                    allDoctors[i].specialNeed="x-ray";
                }
              
            }
        }


        // Labeling doctors who need online streaming
        for (int i = 0; i<allDoctors.length; i++ ){

            for (int j=0; j<doctorsWhoNeedsStreaming.length; j++){

                if (allDoctors[i].name.equals(doctorsWhoNeedsStreaming[j])){
                    allDoctors[i].specialNeed="streaming";
                }
              
            }
        }

        setTheRooms();


        


        backtrack();

        printRoomSchedule(Room1);
        printRoomSchedule(Room2);
        printRoomSchedule(Room3);


        

        
    }


    public static void backtrack(){

        //Check if all the doctors are assigned

       
        boolean isCompleteAssignment = checkAssignmetOfDoctors();
        if (isCompleteAssignment)
        {return;}


        // Select unassigned variable (doctor)
        else {
            

            //Loop until all the doctors are assigned
            //Just commented it to avoid infinite loop while the assignment is incomplete
           // while(!checkAssignmetOfDoctors()){
               
                for (int i=0; i<allDoctors.length; i++){
                if (allDoctors[i].type.toLowerCase().equals("junior")){
                    System.out.println("found a junior doctor");




                    //Room 3 only + senior must be present

                    // We search for a senior

                    for (int j=0; j<allDoctors.length; j++){
                        System.out.println("looking for a senior");


                        if (allDoctors[j].type.toLowerCase().equals("senior")){

                            System.out.println("found a senior doctor");


                            //X-ray only in room 1
                            if (allDoctors[j].specialNeed.toLowerCase().equals("x-ray")){
                                System.out.println("this senior needs x-ray");

                                 

                                //Search for an empty booking in room 1
                                for (int y=0; y<Room1.length; y++){
                                    
                                    if (Room1[y].doctors[0] == null){
                                        Doctor[] d = {allDoctors[i] , allDoctors[j]};
                                        Room1[y].doctors = d;
                                        Room1[y].roomNum = 1;
                                        Room1[y].surgeryType="needs x-ray";
                                        room1NumOfBookedSlots++;

                                        allDoctors[i].assigned=true;
                                        allDoctors[j].assigned=true;
                                        //Check if it satisfies the constraints or not to delete the last assigned value
                                        break;

                                    }
                                }
                                

                            }


                            //Streaming only in room 2
                            if (allDoctors[j].specialNeed.toLowerCase().equals("streaming")){
                                System.out.println("this senior needs online streaming");


                                 //Search for an empty booking in room 2
                                 for (int y=0; y<Room2.length; y++){
                                    
                                    if (Room2[y].doctors[0] == null){
                                        Doctor[] d = {allDoctors[i] , allDoctors[j]};
                                        Room2[y].doctors = d;
                                        Room2[y].roomNum = 1;
                                        Room2[y].surgeryType="needs streaming";
                                        room2NumOfBookedSlots++;

                                        allDoctors[i].assigned=true;
                                        allDoctors[j].assigned=true;
                                        //Check if it satisfies the constraints or not to delete the last assigned value
                                        break;

                                    }
                                }

                            }

                            //Brain only on days 2 & 4
                            if (allDoctors[j].specialNeed.toLowerCase().equals("brain")){
                                

                                boolean foundARoom = false;
                                //search for an empty day 2 or 4 in room1 then if not found search in room 2




                                //Search for an empty booking in room 1 on day 2 or 4
                                for (int y=0; y<Room1.length; y++){
                                    
                                    if (Room1[y].doctors[0] == null && (Room1[y].day == 2 || Room1[y].day == 4)){
                                        Doctor[] d = {allDoctors[i] , allDoctors[j]};
                                        Room1[y].doctors = d;
                                        Room1[y].roomNum = 1;
                                        Room1[y].surgeryType="brain";
                                        room1NumOfBookedSlots++;

                                        allDoctors[i].assigned=true;
                                        allDoctors[j].assigned=true;
                                        foundARoom=true;
                                         //Check if it satisfies the constraints or not to delete the last assigned value
                                        break;

                                    }
                                    
                                }


                                //We couldn't find an empty booking in room 1 on day 2 or 4
                                // We search in room 2
                                if(!foundARoom){

                                    for (int y=0; y<Room2.length; y++){
                                    
                                        if (Room2[y].doctors[0] == null && (Room2[y].day == 2 || Room2[y].day == 4)){
                                            Doctor[] d = {allDoctors[i] , allDoctors[j]};
                                            Room2[y].doctors = d;
                                            Room2[y].roomNum = 1;
                                            Room2[y].surgeryType="brain";
                                            room2NumOfBookedSlots++;
    
                                            allDoctors[i].assigned=true;
                                            allDoctors[j].assigned=true;
                                            foundARoom=true;
                                            //Check if it satisfies the constraints or not to delete the last assigned value
                                            break;
    
                                        }
                                        
                                    }

                                        
                                }



                                
                            }
                         
                        
                            
                             



                    }

                    
                }






            //}

          }




        }

    }




    }



    public static void printRoomSchedule(Booking[] room){
        
        System.out.println("Room "+room[0].roomNum+" Schedule");

        for (int i=0; i<room.length; i++){

            System.out.println("day "+room[i].day);
            System.out.println("Shift "+room[i].shift);
            System.out.println("Surgery type "+room[i].surgeryType);
            
            printDoctors(room[i].doctors);}








      


        }
        
        
        public static void printDoctors(Doctor[] docs){
        
        for (int i=0; i<docs.length; i++){
                
                
                //Just for now until we complete the assignment
                if (docs[i] == null){
                System.out.println("Doctor "+docs[i]);}
                else{
                System.out.println("Doctor "+docs[i].name);
                System.out.println("Type "+docs[i].type);

        }



    }}





    public static boolean checkAssignmetOfDoctors(){
      
        boolean isAllDoctorsAssigned = true;

        for (int i=0; i<allDoctors.length; i++){

            isAllDoctorsAssigned=isAllDoctorsAssigned && allDoctors[i].assigned;
        }

        return isAllDoctorsAssigned;

    }


    public static void setTheRooms(){
        //Booking shift, day, room number
        Room1[0]=new Booking(1,1,1);
        Room1[1]=new Booking(2,1,1);
        Room1[2]=new Booking(1,2,1);
        Room1[3]=new Booking(2,2,1);
        Room1[4]=new Booking(1,3,1);
        Room1[5]=new Booking(2,3,1);
        Room1[6]=new Booking(1,4,1);
        Room1[7]=new Booking(2,4,1);
        Room1[8]=new Booking(1,5,1);
        Room1[9]=new Booking(2,5,1);


        Room2[0]=new Booking(1,1,2);
        Room2[1]=new Booking(2,1,2);
        Room2[2]=new Booking(1,2,2);
        Room2[3]=new Booking(2,2,2);
        Room2[4]=new Booking(1,3,2);
        Room2[5]=new Booking(2,3,2);
        Room2[6]=new Booking(1,4,2);
        Room2[7]=new Booking(2,4,2);
        Room2[8]=new Booking(1,5,2);
        Room2[9]=new Booking(2,5,2);


        Room3[0]=new Booking(1,1,3);
        Room3[1]=new Booking(2,1,3);
        Room3[2]=new Booking(1,2,3);
        Room3[3]=new Booking(2,2,3);
        Room3[4]=new Booking(1,3,3);
        Room3[5]=new Booking(2,3,3);
        Room3[6]=new Booking(1,4,3);
        Room3[7]=new Booking(2,4,3);
        Room3[8]=new Booking(1,5,3);
        Room3[9]=new Booking(2,5,3);




    }



    
}
