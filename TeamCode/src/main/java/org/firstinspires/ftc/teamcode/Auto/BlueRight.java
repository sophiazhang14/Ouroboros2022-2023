package org.firstinspires.ftc.teamcode.Auto;
import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.Claw;
import org.firstinspires.ftc.teamcode.Hardware.Manipulator;
import org.firstinspires.ftc.teamcode.Hardware.Turret;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(group = "blue", name = "BlueRight")
public class BlueRight extends LinearOpMode {
        @Override
        public void runOpMode() throws InterruptedException {
            Claw claw = new Claw(this);
            Manipulator manipulator = new Manipulator(this);
            Turret turret = new Turret(this);
            Vision vision = new Vision(this);
            Pose2d pose = new Pose2d(0,0,0 );
            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
            DriveTrain dt = new DriveTrain(this);

            if (vision.senseBlue() == 1) {
                dt.encoderMove(.5, 10,1, true, false);
                dt.encoderMove(.5,20,2, false, false);
            } else if (vision.senseBlue() == 2){
                dt.encoderMove(.5,20,2, false, false);
            } else {
                dt.encoderMove(.5, 30, 3, true, true);
                dt.encoderMove(.5,20,2, false, false);
            }
        }
}
