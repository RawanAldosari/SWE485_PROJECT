public class Constraints {

    //INFORMATION
    // C1: consultants, C2: senior doctors, C3: junior doctors, C4: Brain Surgery doctors, C5: Doctors who need an x-ray, C6: Doctors who need online streaming


    //We check this constraint in RoomScheduling class where we check if all the doctors are assigned which means that each doctoe does at least 1 surgery
    // Each doctor should do at least 1 surgery
    // C1 + C2 + C3 >= 30


    //Constraint#1
    // Consultants don't work during afternoon 1-4
    // (R12j != C1) and (R22j != C1) and (R32j != C1)


    //Constraint#2
    // A doctor should not be assigned to more than one room at the same time (We missed it in phase 1)


    //Constraint#3
    //Room 1 got assigned junior doctors if and only if room 1 got assigned senior doctors
    //(R1ij = C3) if and only if (R1ij = C2)

    //Constraint #4
    //Room 2 got assigned junior doctors if and only if room 2 got assigned senior doctors
    //(R2ij = C3) if and only if (R2ij = C2)

    //Constraint #5
    // Junior doctors who joined senior doctors cannot use room 3
    //R3ij != C2 + C3


    //Constraint #6
    //Brain surgeries cannot be done in any room at any shift on Sunday, Tuesday and Thursday
    //(R1i1,3,5 != C4) and (R2i1,3,5 != C4) and (R3i1,3,5 != C4)


    //Constraint #7
    //Doctors who need an x-ray cannot use room 2 and room 3
    //(R2ij != C5 ) and (R3ij != C5)

    //Constraint #8
    //Doctors who need an online streaming cannot use room 1 and room 3
    //(R1ij != C6) and (R3ij != C6)  

    // Domain of room 1 is senior or junior

    //Domain of room 1 in the morning (shift 1) is Consultants (since they only work in the morning) and the domain above (senior and junior)


    // Domain of room 2 is senior or junior

    //Domain of room 2 in the morning (shift 1) is Consultants (since they only work in the morning) and the domain above (senior and junior)


    // Domain of room 3 is consultant since juniors should join seniors and the room is too small and adding senior to this domain is just a waist of resources and knowledge that juniors need



     //This method checks one room at a time
     public static boolean checkConstraintsSatisfaction(Booking[] room){

        boolean satisfiesConstraints = false;

        //We check if it satisfies all the constraints



         //Constraint #1

         //We check all the bookings of the room
         for (int i =0; i<room.length; i++){
            
            //Check the consultants shouldn't work in the afternoon shift


            //We check all the doctors of the room
            for(j=0; j<room[i].doctors.length; j++){
                
                if(room[i].doctors[j].type.toLowerCase().equals("consultant") && room[i].shift==2){
                    satisfiesConstraints = false;
                }

            }
         }


         //Constraint #2 
         //We check it in another method since it requires checking all the rooms

         


         //Constraint #3
         //If room 1 is assigned a junior doctor it must be assigned a senior doctor as well


         //We check all the bookings of the room
         for (int i =0; i<room.length; i++){
            
            //We check all the doctors of the room
            for(j=0; j<room[i].doctors.length; j++){
                
            //We check all the doctors of the room we must have a junior+senior in the same booking
            if(room[i].doctors[j].type.toLowerCase().equals("junior")){

                //We check if there is a senior in the same booking
                 for(y=0; y<room[i].doctors.length; y++){

                    boolean thereIsASenior = false;

                         if (room[i].doctors[y].type.toLowerCase().equals("senior")){
                          thereIsASenior = true;
                          //If we found one senior we don't need to continue searching
                          break;
                         }


                    //This means that there is a junior in the booking but no senior
                    if(thereIsASenior == false){
                        satisfiesConstraints = false;
                    }

                }

            }

        }
         
    }



         //Constraint #4
         //It is the same as Constraint #3 since We will check in constraint #5 that the room should't be 3 when juniors are involved



         //Constraint #5
         //We should check each booking in the rooms if there is a junior it shouldn't be room 3

         //We check all the bookings of the room
         for (int i =0; i<room.length; i++){
            
            //We check all the doctors of the room
            for(j=0; j<room[i].doctors.length; j++){
                
            //We check all the doctors of the room we must have a junior+senior in the same booking
            if(room[i].doctors[j].type.toLowerCase().equals("junior") && room[i].roomNum==3){

                satisfiesConstraints = false;
            }

            }
        }




        //Constraint #6
        //Brain surgeries cannot be done on Sunday (1), Tuesday (3) and Thursday (4)

        //We check all the bookings of the room
        for (int i =0; i<room.length; i++){

            if (  (room[i].day==1 || room[i].day==3 || room[i].day==5) && room[i].surgeryType.toLowerCase().equals("brain")){

                satisfiesConstraints = false;
            }
        }




       //Constraint #7 
       //Surgeries that need x-ray cannot use room 2 and room 3

        //We check all the bookings of the room
        for (int i =0; i<room.length; i++){

            if(room[i].surgeryType.toLowerCase().equals("needs x-ray") &&   (room[i].roomNum == 2 || room[i].roomNum == 3)){

                satisfiesConstraints = false;

            }

        }


         //Constraint #8
         //Doctors who need an online streaming cannot use room 1 and room 3

          //We check all the bookings of the room
          for (int i =0; i<room.length; i++){

            if(room[i].surgeryType.toLowerCase().equals("needs streaming") &&   (room[i].roomNum == 1 || room[i].roomNum == 3)){

                satisfiesConstraints = false;

            }

         }








     









        return satisfiesConstraints;


    }

    
}
