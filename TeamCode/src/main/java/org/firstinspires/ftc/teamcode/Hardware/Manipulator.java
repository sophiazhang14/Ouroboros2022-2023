package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Manipulator {
    public Servo wrist;
    public LinearOpMode opMode;
    // shriya
    public Manipulator(LinearOpMode opMode) {
        this.opMode = opMode;
        opMode.hardwareMap.servo.get("wrist");
        opMode.telemetry.addLine("claw init");
    }
    public void setWrist(double pos) {
        wrist.setPosition(pos);
    }
}
