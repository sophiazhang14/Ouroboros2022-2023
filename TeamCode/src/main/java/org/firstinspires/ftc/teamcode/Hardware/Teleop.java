package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Teleop extends Telelib{
// WHAT DO OVERRIDE DOOOOO??!!!!!
    public void loop() {

        // can't just create an object?
        //plunger();
        //turnTurret();
        high_arm();
        low_arm();
        wrist();
        claw();
        arcadeDrive();
        telemetry();
    }
    public void stop() {
        kill();}//how? why?
}
