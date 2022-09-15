package org.firstinspires.ftc.teamcode.Hardware;

public class Teleop {
// WHAT DO OVERRIDE DOOOOO??!!!!!
    Telelib a = new Telelib();
    public void loop() {

        // can't just create an object?
        a.plunger();
        a.moveWrist();
        a.arcadeDrive();
        a.turnTurret();
        a.high_arm();
        a.low_arm();
    }
    public void stop() {a.kill();}//how? why?
}
