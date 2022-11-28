package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Manipulator;
import org.firstinspires.ftc.teamcode.Hardware.Turret;

@Autonomous(group = "blue", name = "BlueRight")
public class BlueRight extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
        //Claw claw = new Claw(this);
        //Manipulator manipulator = new Manipulator(this);
        //Turret turret = new Turret(this);
        DriveTrain dt = new DriveTrain(this);
        Vision vis = new Vision(this);

        //dt.encoderMove(.5, 3, 3, false, false);

        waitForStart();

       if(vis.senseBlue().equals("one")){
        dt.encoderMove(.5,10, 5, true, true);
        dt.encoderMove(.5, 12, 3, false, false);
            }
        else if(vis.senseBlue().equals("two")){
        dt.encoderMove(.5, 2, 3, true, false);
        dt.encoderMove(.5, 12, 3, false, false);
        }  else{
        dt.encoderMove(.5, 15, 3, true, false);
        dt.encoderMove(.5, 12, 3, false, false);
            // pos three is pos closest to wall
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