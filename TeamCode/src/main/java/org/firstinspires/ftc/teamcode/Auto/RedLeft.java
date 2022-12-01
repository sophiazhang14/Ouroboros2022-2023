package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(group = "auto", name = "RedLeft")
public class RedLeft extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        //Claw claw = new Claw(this);
        //Manipulator manipulator = new Manipulator(this);
        //Turret turret = new Turret(this);
        DriveTrain dt = new DriveTrain(this);
        Vision vis = new Vision(this);

        //dt.encoderMove(.5, 3, 3, false, false);

        waitForStart();

       if(vis.senseBlueLeft() == 3){
           dt.encoderMove(.5,11, 5, true, false);
           dt.encoderMove(.5,11, 5, true, true);
           dt.encoderMove(-.5,2,3,false,false);
           dt.encoderMove(.5,11, 5, true, true);
           //dt.turn(true,.1);
           dt.encoderMove(.5, 12, 3, false, false);
            }
        else if(vis.senseBlueLeft() == 2){
           dt.encoderMove(.5,11, 5, true, false);
           dt.encoderMove(.5,11, 5, true, true);
           dt.encoderMove(-.5,2,3,false,false);
           dt.encoderMove(.5, .5, 3, true, false);
           dt.encoderMove(.5, 12, 3, false, false);
        }  else{
           dt.encoderMove(.5, 15, 3, true, false);
           dt.encoderMove(.5, 12, 3, false, false);
        }

        //if (1 == 1/*vis.senseBlue() == 1*/) {
    //        dt.encoderMove(.5, 20, 2, false, false);
            //dt.encoderMove(.5, 10, 5, true, false);
    //    } else if (2 == 2/*vis.senseBlue() == 2*/) {
    //        dt.encoderMove(.5, 2, 1, true, false);
    //        dt.encoderMove(.5, 20, 2, false, false);
   //     } else {
   //         dt.encoderMove(.5, 10, 3, true, true);
    //        dt.encoderMove(.5, 20, 2, false, false);
    //    }
    }
}