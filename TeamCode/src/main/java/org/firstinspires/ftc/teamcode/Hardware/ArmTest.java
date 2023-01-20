package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(group = "auto", name = "ArmTest")
public class ArmTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        while(opModeIsActive()) {
            TempMan tm = new TempMan(this);
            Manipulator arm = new Manipulator(this);

            //tm.tempHArm(10,.5);
            //tm.tempLArm(10,.5);

            //sleep(5000);

            //tm.tempHArmHold(.2);
            //tm.tempLArmHold(.2);

            //sleep(1000);
            //tm.setClaw(1);

            //tm.tempHArmHold(.7);
            arm.lowPositionPID(5, .5, 1, .0008/90, .08, .00008/90);
            arm.highPositionPID(4,.4,3,.0008/90,.08,.1/90);
            //.7/90, .02, .02/90
            //arm.moveLArm(.5);
            //arm.moveHArm(.5);
            //wait(1);
            //arm.moveHArm(.3);
        }
    }
}
