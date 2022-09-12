package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    public Servo claw;
    public Servo wrist;
    public LinearOpMode opMode;

    public Claw(LinearOpMode opMode) {
        this.opMode = opMode;
        opMode.hardwareMap.servo.get("claw");
        opMode.hardwareMap.servo.get("wrist");
        opMode.telemetry.addLine("claw init");
    }
    public void setClaw(boolean isOpen) {
        if (isOpen) {
            claw.setPosition(0);
        } else {
            claw.setPosition(1);
        }
    }
    public void setWrist(double pos) {
        wrist.setPosition(pos);
    }
}
