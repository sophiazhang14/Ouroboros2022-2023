package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


public class BlueRight extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
       DriveTrain dt = new DriveTrain(this);
       Vision vis = new Vision(this);

       if(vis.senseBlue() == 1){
            dt.encoderMove(0.5,30, 5, false, false );
    }
        else if(vis.senseBlue() ==2 ){
        dt.encoderMove(.5, 50, 3, false, false);
    }  else{
            dt.encoderMove(.5, 70, 3, false, false);
        }
    }
}
