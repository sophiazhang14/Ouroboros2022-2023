package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Teleop extends Telelib{
// WHAT DO OVERRIDE DOOOOO??!!!!!
    public void loop() {

        // can't just create an object?
        //plunger();
        moveWrist();
        arcadeDrive();
        turnTurret();
        high_arm();
        low_arm();
        claw();
        twist();
    }
    public void stop() {
        kill();}//how? why?
}
