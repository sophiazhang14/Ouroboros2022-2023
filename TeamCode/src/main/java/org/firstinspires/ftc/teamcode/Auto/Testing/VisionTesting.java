package org.firstinspires.ftc.teamcode.Auto.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Auto.DriveTrain;
import org.firstinspires.ftc.teamcode.Auto.Vision;

@Autonomous(group = "testing", name = "CameraVision")
public class VisionTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Vision vision = new Vision(this);
        waitForStart();
        telemetry.addData("vision: ", vision.visionShriya());

    }
}